package de.viasien.gameoflife;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;

public class GameOfLife extends ApplicationAdapter {
	ShapeRenderer sr;

	static Hud hud;
	static Field field;
	static CamManager cam;
	static InputHandler input;

	@Override
	public void create () {
		sr = new ShapeRenderer();

		VisUI.load();

		field = new Field();
		hud = new Hud();
		cam = new CamManager();
		input = new InputHandler();

		InputMultiplexer multiplexer = new InputMultiplexer(hud.getStage(), input);
		Gdx.input.setInputProcessor(multiplexer);
	}

	@Override
	public void resize(int width, int height) {
		hud.resize(width, height);
		cam.updateViewports();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0,0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		input.continuousInputHandling();
		cam.updateCam(sr);
		field.act();



		sr.begin(ShapeRenderer.ShapeType.Filled);

		for(int x=0; x<field.getWidth(); x++) {
			for(int y=0; y<field.getHeight(); y++) {
				if( field.isAlive(x, y) )
					sr.setColor(Color.WHITE);
				else
					sr.setColor(Color.BLACK);
				sr.rect(x*10, y*10, 10, 10);
			}
		}

		// draw edges
		sr.setColor(Color.LIGHT_GRAY);
		sr.rect(0, 0, 2, field.getHeight()*Parameters.TILESIZE );
		sr.rect(field.getWidth()*Parameters.TILESIZE, 0, 2, field.getHeight()*Parameters.TILESIZE );
		sr.rect(0, 0, field.getWidth()*Parameters.TILESIZE, 2 );
		sr.rect(0, field.getHeight()*Parameters.TILESIZE, field.getWidth()*Parameters.TILESIZE, 2 );

		sr.end();

		hud.draw();
	}
	
	@Override
	public void dispose () {
		hud.dispose();
		VisUI.dispose();
	}
}
