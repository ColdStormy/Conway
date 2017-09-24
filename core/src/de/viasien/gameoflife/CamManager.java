package de.viasien.gameoflife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

/**
 * Created by Jannis on 10.07.2017.
 */
public class CamManager {

    private static OrthographicCamera cam;
    private static float aspectRatio, height, width;
    // 0.35 <= camScale <= 0.9
    private static float minCamScale = 0.35f;
    private static float maxCamScale = 5f;
    private static float desiredCamScale ;


    private Vector3 camPos;
    private static final int speed = 8;

    private static Input in = Gdx.input;

    private Field field;


    public CamManager() {
        this.field = GameOfLife.field;

        width = Gdx.graphics.getWidth();
        aspectRatio = (float) Gdx.graphics.getHeight() / (float) width;
        height = width * aspectRatio;

        cam = new OrthographicCamera(width, height);
        camPos = cam.position;
        cam.zoom = maxCamScale;
        desiredCamScale = minCamScale + 0.3f;
        camPos.x = Parameters.TILESIZE * field.getWidth() / 2;
        camPos.y = Parameters.TILESIZE * field.getHeight() / 2;
        cam.update();

    }
    /**
     * alias updateAspectRatio
     * Called when window is being resized
     */
    public void updateViewports() {
        width = Gdx.graphics.getWidth();
        aspectRatio = (float)Gdx.graphics.getHeight()/(float)width;
        height = width*aspectRatio;

        cam.viewportHeight = height;
        cam.viewportWidth = width;
        updateCamPos(0,0);
    }

    /**
     * > 0 ==> in positive direction
     * < 0 ==> in negative direction
     * @param dX not delta
     * @param dY not delta
     */
    public void updateCamPos(int dX, int dY) {

        if( dX > 0 ) {
            // move cam to right
            camPos.x += speed;
        } else if( dX < 0 ) {
            // move cam to left
            camPos.x -= speed;
        }

        if( dY > 0 ) {
            // move cam up
            camPos.y += speed;
        } else if( dY < 0 ) {
            // move cam down
            camPos.y -= speed;
        }


        /**
         * Grenzen der CamPosition
         */
        int maxOverflow = Parameters.TILESIZE * 10;
        float minX = cam.zoom * cam.viewportWidth / 2 - maxOverflow;
        float maxX = field.getWidth() * Parameters.TILESIZE - cam.zoom * cam.viewportWidth / 2 + maxOverflow;
        float minY = cam.zoom * cam.viewportHeight / 2 - maxOverflow;
        float maxY = field.getHeight() * Parameters.TILESIZE - cam.zoom * cam.viewportHeight / 2 + maxOverflow;

        // if min > max, the cam can't decide which to pick so we get a flickering..
        if( minX > maxX )
            camPos.x = Parameters.TILESIZE * field.getWidth()/2;
        else
            camPos.x = MathUtils.clamp( camPos.x, minX, maxX );

        if( minY > maxY )
            camPos.y = Parameters.TILESIZE * field.getHeight()/2;
        else
            camPos.y = MathUtils.clamp(camPos.y, minY, maxY);

    }

    public void zoom(int direction) {

        if( direction > 0 && desiredCamScale < maxCamScale ) {
            desiredCamScale += 0.05f;
        } else if ( direction < 0 && desiredCamScale > minCamScale ) {
            desiredCamScale -= 0.05f;
        }

        // zoom towards the cursor



    }

    /**
     * gets called every frame
     * necessary to make changes visible
     * SpriteBatch does not have to be in drawing cycle
     * @param sr
     */
    public void updateCam(ShapeRenderer sr) {


        // create a smooth zooming animation
        float difference = desiredCamScale-cam.zoom;

        if( Math.abs(difference) > 0.001 ) {
            if (difference > 0) {
                cam.zoom += (desiredCamScale - cam.zoom) * 0.1;
            } else if (difference < 0) {
                cam.zoom -= (cam.zoom - desiredCamScale) * 0.1;
            }

            updateCamPos(0,0);

        } else {
            cam.zoom = desiredCamScale;
        }


        camPos.z = 0;
        cam.update();
        sr.setProjectionMatrix(cam.combined);
    }

    /**
     * Get the cursor position on the field <b>in pixels.</b>
     * @param x coordinate in pixel on screen
     * @param y -"-
     * @return
     */
    public Vector2 getCursorPos(int x, int y) {
        // top left in the window is 0,0
        // need to convert it to bottom left in order to match with world arrays
        y = Gdx.graphics.getHeight() - y;

        // now bring it to world pixel (!) coordinates
        // we know the camPos is always in the center of the screen
        // so we calculated the distance from the point clicked to center of the screen
        // this distance needs to be scaled to the current zoom scale
        // and then add to current cam position
        float distX = x - Gdx.graphics.getWidth()/2;
        float distY = y - Gdx.graphics.getHeight()/2;

        distX *= cam.zoom;
        distY *= cam.zoom;

        return new Vector2(
                camPos.x + distX,
                camPos.y + distY
        );
    }

    /**
     * Assuming the parameters are already translated to world pixel coordinates.
     * Translates those to grid coordinates.
     *
     * @param x
     * @param y
     * @return
     */
    public Vector2 getCursorCoord(int x, int y) {

        Vector2 v = new Vector2(
                (int) (x/Parameters.TILESIZE),
                (int) (y/Parameters.TILESIZE)
        );

        v.x = MathUtils.clamp(v.x, 0, field.getWidth()-1);
        v.y = MathUtils.clamp(v.y, 0, field.getHeight()-1);

        return v;
    }

    /**
     * combines the following two methods:
     * @see CamManager#getCursorPos(int, int)
     * @see CamManager#getCursorCoord(int, int)
     *
     * @param x
     * @param y
     * @return
     */
    public Vector2 tileClicked(int x, int y) {
        Vector2 pos = getCursorPos(x, y);
        return getCursorCoord( (int) pos.x, (int) pos.y );
    }

}
