package com.bentleycarr.floppybrid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.golfgl.gdxgamesvcs.GameServiceException;

// TODO tidy this up this is a mess

public class TitleScreen implements Screen {

    GameActivity activity;

    /*
    OrthographicCamera camera;
    Viewport viewport;

    BitmapFont font;
    GlyphLayout reuseableLayout;*/

    public TitleScreen(GameActivity activity) {
        this.activity = activity;
        /*
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 768, 432);
        viewport = new ExtendViewport(768,432,camera);
        viewport.apply();*/
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        activity.setScreen(new GameScreen(activity));
        dispose();
    }

    @Override
    public void resize(int width, int height) {
        //viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}
