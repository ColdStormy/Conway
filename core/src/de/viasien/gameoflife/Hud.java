package de.viasien.gameoflife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.layout.HorizontalFlowGroup;
import com.kotcrab.vis.ui.widget.VisImageButton;
import com.kotcrab.vis.ui.widget.VisLabel;

/**
 * Created by jannis on 23.09.17.
 */
public class Hud {

    private Stage stage;

    private SettingsWindow settingsWindow;
    private InfoWindow infoWindow;

    public Hud() {
        stage = new Stage(new ScreenViewport());

        settingsWindow = new SettingsWindow();
        infoWindow = new InfoWindow(settingsWindow);

        stage.addActor(settingsWindow);
        stage.addActor(infoWindow);

    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    public void draw() {
        infoWindow.act();
        stage.draw();
    }

    public Stage getStage() { return stage; }

    public void dispose() {
        stage.dispose();
    }

}
