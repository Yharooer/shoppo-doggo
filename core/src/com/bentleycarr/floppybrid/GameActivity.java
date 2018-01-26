package com.bentleycarr.floppybrid;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.golfgl.gdxgamesvcs.IGameServiceClient;
import de.golfgl.gdxgamesvcs.IGameServiceListener;
import de.golfgl.gdxgamesvcs.NoGameServiceClient;
import de.golfgl.gdxgamesvcs.gamestate.ILoadGameStateResponseListener;
import de.golfgl.gdxgamesvcs.gamestate.ISaveGameStateResponseListener;
import java.nio.ByteBuffer;

public class GameActivity extends Game implements IGameServiceListener, ApplicationListener, InputProcessor {

    // TODO Google Play Games - cannot log off and then log back in without restarting..?

	SpriteBatch batch;

    public Preferences prefs;
	public IGameServiceClient gsClient;

	final String PREFS_ID = "prefs_dev1";
	final String LEADERBOARD_ID = "CgkImcXxgKoGEAIQAA";
	private boolean fetchedBestForThisSession;

	public GameScreen gameScreen;

	public int hiScore;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		prefs = Gdx.app.getPreferences(PREFS_ID);

        if (gsClient == null) gsClient = new NoGameServiceClient();
        gsClient.setListener(this);
        gsClient.resumeSession(); // Silently signs in.

        fetchedBestForThisSession = false;

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);

        this.setScreen(new TitleScreen(this));
    }

    @Override
    public void pause() {
        super.pause();
        gsClient.pauseSession();
    }

    @Override
    public void resume() {
        super.resume();
        gsClient.resumeSession();
    }

    @Override
	public void render () {
		super.render();

        if (!fetchedBestForThisSession && gsClient.isSessionActive()) {
            fetchedBestForThisSession = true;

            gsClient.loadGameState(PREFS_ID, new ILoadGameStateResponseListener() {
                @Override
                public void gsGameStateLoaded(byte[] gameState) {
                    if (gameState == null) return;
                    ByteBuffer bb = ByteBuffer.wrap(gameState);
                    int fetchedScore = bb.getInt();
                    updateLocalScore(fetchedScore);
                }
            });
        }
	}

    public void updateScore (int score) {
	    updateLocalScore(score);
	    updateGlobalScore(score);
    }

    private void updateGlobalScore (int score) {
        gsClient.submitToLeaderboard(LEADERBOARD_ID, score, gsClient.getGameServiceId());
        byte[] data = ByteBuffer.allocate(4).putInt(hiScore).array();
        gsClient.saveGameState(PREFS_ID, data, 0,
                new ISaveGameStateResponseListener() {
                    @Override
                    public void onGameStateSaved(boolean success, String errorCode) { }
                });
    }

    private void updateLocalScore (int score) {
	    if (score > hiScore) {
	        hiScore = score;
        }
        if (score > prefs.getInteger("hiscore", 0)) {
            prefs.putInteger("hiscore", score);
            prefs.flush();
        }
    }

	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
	}

    @Override
    public void gsOnSessionActive() {

    }

    @Override
    public void gsOnSessionInactive() {

    }

    @Override
    public void gsShowErrorToUser(GsErrorType et, String msg, Throwable t) {

    }

    public boolean isPointInSquare (float px, float py, float rx, float ry, float rw, float rh) {
        boolean xOk = rx <= px && rx+rw >= px;
        boolean yOk = ry <= py && ry+rh >= py;
        return xOk && yOk;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Keys.BACK){
            gameScreen.backPressed();
        }
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
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
