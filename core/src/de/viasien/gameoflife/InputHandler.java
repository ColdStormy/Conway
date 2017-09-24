package de.viasien.gameoflife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Jannis on 10.07.2017.
 */
public class InputHandler implements InputProcessor {


    private CamManager cam;
    private Input in;
    private Field field;

    // ---------------------------------------------------
    // custom attributes

    public InputHandler() {
        this.cam = GameOfLife.cam;
        this.field = GameOfLife.field;
        in = Gdx.input;
    }

    /*
    ----------------------------------------------------
    Input events, except for continuousX methods
    ----------------------------------------------------
     */

    public void continuousInputHandling() {

        cameraInput();

    }

    @Override
    public boolean keyDown(int keycode) {

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {


        return false;
    }

    @Override
    public boolean keyTyped(char character) {

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if( button == 1 ) {
            // rechtsklick

        }

        if( button == 0 ) {
            // linksklick
            Vector2 tile = cam.tileClicked(screenX, screenY);
            int x = (int) tile.x;
            int y = (int) tile.y;
            field.setAlive( !field.isAlive(x, y), x, y);
        }

        return false;
    }

    private int lastDraggedToggleX = -1,
            lastDraggedToggleY = -1;
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        if( in.isButtonPressed(0) ) {
            Vector2 tile = cam.tileClicked(screenX, screenY);
            int x = (int) tile.x;
            int y = (int) tile.y;

            if( x == lastDraggedToggleX && y == lastDraggedToggleY )
                return false;

            lastDraggedToggleX = x;
            lastDraggedToggleY = y;
            field.setAlive( !field.isAlive(x, y), x, y);
        }

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {


        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        cam.zoom(amount);
        return false;
    }



    private void cameraInput() {


        // CAMERA MOVEMENT:
        boolean cameraKeysPressed = false;
        // with Keys WASD
        if (in.isKeyPressed(Input.Keys.W)) {
            cam.updateCamPos(0, 1);
            cameraKeysPressed = true;
        }
        if (in.isKeyPressed(Input.Keys.A)) {
            cam.updateCamPos(-1, 0);
            cameraKeysPressed = true;
        }
        if (in.isKeyPressed(Input.Keys.S)) {
            cam.updateCamPos(0, -1);
            cameraKeysPressed = true;
        }
        if(in.isKeyPressed(Input.Keys.D)) {
            cam.updateCamPos(1, 0);
            cameraKeysPressed = true;
        }

        // move when cursor is on edges and keys are not pressed
//        if( !cameraKeysPressed ) {
//            int layerWidth = 50,
//                    screenX = Gdx.input.getX(),
//                    screenY = Gdx.input.getY();
//
//            if (screenX <= layerWidth) {
//                cam.updateCamPos(-1, 0);
//            } else if (screenX >= Gdx.graphics.getWidth() - layerWidth) {
//                cam.updateCamPos(1, 0);
//            }
//
//            if (screenY <= layerWidth) {
//                cam.updateCamPos(0, 1);
//            } else if (screenY >= Gdx.graphics.getHeight() - layerWidth) {
//                cam.updateCamPos(0, -1);
//            }
//        }



    }
}
