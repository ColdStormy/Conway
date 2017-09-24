package de.viasien.gameoflife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.kotcrab.vis.ui.widget.*;
import com.kotcrab.vis.ui.widget.spinner.IntSpinnerModel;
import com.kotcrab.vis.ui.widget.spinner.Spinner;

/**
 * Created by jannis on 23.09.17.
 */
public class InfoWindow extends VisWindow {

    private SettingsWindow settingsWindow;

    private VisImageButton settings, play, pause, resetPhase, nextPhase;
    private VisLabel currentPhase, updateLabel;

    private SpriteDrawable playSprite, pauseSprite;

    public InfoWindow(SettingsWindow settingsWindow) {
        super("Info");
        this.settingsWindow = settingsWindow;
        pad(30, 10, 10, 10);
        build();
        pack();
        setPosition( 20, 10 );
    }

    private void build() {

        VisTable table = new VisTable(true);


        settings = new VisImageButton(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("settings.png")))));
        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                settingsWindow.setVisible(true);
            }
        });


        resetPhase = new VisImageButton(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("phase.png")))));
        resetPhase.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameOfLife.field.reset();
            }
        });

        nextPhase = new VisImageButton(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("next.png")))));
        nextPhase.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameOfLife.field.phasePush();
            }
        });
        currentPhase = new VisLabel("Phase 0");

        playSprite = new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("play.png"))));
        pauseSprite = new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("pause.png"))));
        play = new VisImageButton(playSprite);
        pause = new VisImageButton(pauseSprite);
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                play.setVisible(false);
                pause.setVisible(true);
                GameOfLife.field.play();
            }
        });
        pause.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                play.setVisible(true);
                pause.setVisible(false);
                GameOfLife.field.pause();
            }
        });
        pause.setVisible(false);


        updateLabel = new VisLabel(Parameters.updateInterval+"ms");
        VisSlider updateInterval = new VisSlider(100, 1500, 100, false);
        updateInterval.setValue(Parameters.updateInterval);
        updateInterval.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Parameters.updateInterval = (long) updateInterval.getValue();
            }
        });

        table.addSeparator().colspan(2);
        table.row();
        table.add(settings);
        table.add(currentPhase).colspan(2);
        table.row();
        table.add(play);
        table.add(pause);
        table.add(nextPhase);
        table.add(resetPhase);
        table.row();
        table.add(updateLabel);
        table.add(updateInterval).colspan(table.getColumns()-1);

        add(table);
    }

    public void act() {
        currentPhase.setText("Generation "+GameOfLife.field.phaseCounter);
        updateLabel.setText(Parameters.updateInterval+"ms");
    }

}
