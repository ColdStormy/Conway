package de.viasien.gameoflife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.widget.*;
import com.kotcrab.vis.ui.widget.spinner.IntSpinnerModel;
import com.kotcrab.vis.ui.widget.spinner.Spinner;

/**
 * Created by jannis on 23.09.17.
 */
public class SettingsWindow extends VisWindow {

    public final int maxCellsX = 250,
            minCellsX = 1,
            maxCellsY = 250,
            minCellsY = 1,
            updateInterval = 1000;

    public SettingsWindow() {
        super("Settings");
        build();
        pack();
        setPosition( Gdx.graphics.getWidth()/2-getWidth()/2, Gdx.graphics.getHeight()/2-getHeight()/2 );
    }

    private void build() {

        padTop(30);
        padRight(10);
        padLeft(10);
        padBottom(10);
        setFillParent(false);

        VisTable mainTable = new VisTable(true);

        VisLabel labelSize = new VisLabel("Field size (width x height): ");

        IntSpinnerModel cellsXModel = new IntSpinnerModel(Parameters.nCellsX, minCellsX, maxCellsX, 1);
        Spinner countCellsX = new Spinner("",cellsXModel);
        IntSpinnerModel cellsYModel = new IntSpinnerModel(Parameters.nCellsY, minCellsY, maxCellsY, 1);
        Spinner countCellsY = new Spinner("", cellsYModel);


        mainTable.addSeparator().colspan(3);
        mainTable.row();
        mainTable.add(labelSize).left();
        mainTable.add(countCellsX);
        mainTable.add(new VisLabel("x"));
        mainTable.add(countCellsY);
        mainTable.row();
        mainTable.add(new VisLabel("White cells live and black cells are dead. \n Left klick to toggle. You can also click and drag. \n Use mousewheel to zoom and WASD to move cam.")).colspan(3);

        VisTable finishingTable = new VisTable(true);
        finishingTable.add( new VisTextButton("Initiate", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int nCellsX = Integer.parseInt( countCellsX.getTextField().getText() );
                int nCellsY = Integer.parseInt( countCellsY.getTextField().getText() );

                Parameters.nCellsX = nCellsX;
                Parameters.nCellsY = nCellsY;
                GameOfLife.field.reset();

                setVisible(false);

            }
        }));
        finishingTable.add( new VisTextButton("Cancel", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setVisible(false);
            }
        }));
        finishingTable.add( new VisTextButton("Exit", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        }));


        add(mainTable).row();
        add(finishingTable).padTop(20);

    }



}
