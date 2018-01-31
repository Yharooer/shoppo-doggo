package com.bentleycarr.floppybrid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.math.Rectangle;
import com.bentleycarr.floppybrid.obstacles.*;
import de.golfgl.gdxgamesvcs.GameServiceException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class GameScreen implements Screen {

    // TODO Fix memory leak when stopping/starting the game many times.
    // TODO use texture packer
    // TODO make sure stuff moves left in floats.
    // TODO fix jumping sound stopping halfway through

    private boolean DISPLAY_COLLISION = false;

    private float GRAVITY = 2600;
    private float XVELOCITY = 2500;
    private int FLOOR_HEIGHT = 20;
    private float MAX_UPTHRUST = 0.15f;
    private float UPTHRUST_STRENGTH = 950;

    private float OBSTACLE_SPEED = 400;
    private float BACKGROUND_SPEED = 400*2/3;

    private int BACK_TILE = 827;

    private GameActivity activity;
    private OrthographicCamera camera;
    private Viewport viewport;

    private BitmapFont font;

    private Texture collisionTex;

    private Texture floorTex;
    private Texture ceilingTex;
    private Texture backTex;
    float backgroundX;
    float foregroundX;

    private Texture pauseBackgroundTex;
    GlyphLayout reuseableLayout;
    private Texture pauseButtonTex;
    private Texture pauseButtonPressedTex;

    private Texture doggoTex;
    private Texture dogGameOverTex;
    private Rectangle doggoBounds;
    private float doggoX;
    private float doggoY;
    private int doggoCmX = 33;
    private int doggoCmY = 35;
    private int doggoSrcDimen;

    private float yVelocity = 0;
    private float timeHeld = 0;

    ArrayList<Obstacle> obstaclePool;
    ArrayList<Obstacle> activeObstacles;
    float timeSinceLastObstacleTop;
    float timeSinceLastObstacleBottom;
    float widthTop;
    float widthBottom;
    float heightTop;
    float heightBottom;

    int score;
    boolean gameOver = false;
    float gameOverWaitTime;
    boolean isPaused;
    boolean pausePressDown;

    Texture logo;

    Texture leaderboardButton;
    Texture leaderboardButtonPressed;
    Texture signInButton;
    Texture signInButtonPressed;
    Texture signOutButton;
    Texture signOutButtonPressed;

    boolean isPlayPressed = false;
    boolean isLeaderboardPressed = false;
    boolean isLogOutPressed = false;

    private Texture gameOverBack;
    private Texture playButton;
    private Texture playButtonPressed;
    private Texture backButton;
    private Texture backButtonPressed;
    private boolean playIsPressed = false;
    private boolean backIsPressed = false;
    private boolean isNewHiScore;

    private Sound jumpSound;
    private Sound dieSound;
    private Sound gameStartSound;
    private Sound clickSound;

    private boolean isTitle = true;

    private Vector3 touchPos; // Recycled

    public GameScreen(GameActivity activity) {
        this.activity = activity;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 768, 432);
        viewport = new ExtendViewport(768,432,camera);
        viewport.apply();
        activity.gameScreen = this;

        initResources();
        initVariables();
    }

    public void backPressed() {
        if (!gameOver && !isTitle) {
            pause();
        }
        if (gameOver && gameOverWaitTime <= 0) {
            goBackScreen();
        }

        if (isTitle) {
            Gdx.app.exit();
        }
    }

    private void initVariables() {
        backgroundX = 0;
        foregroundX = 0;

        activeObstacles = new ArrayList<Obstacle>();
        timeSinceLastObstacleTop = 0;
        timeSinceLastObstacleBottom = 0;
        widthBottom = -110;
        widthTop = -110;
        heightBottom = 0;
        heightTop = 0;

        gameOver = false;
        score = 0;
        isPaused = false;
        pausePressDown = false;
        touchPos = new Vector3();
        gameOverWaitTime = 1.0f;

        OBSTACLE_SPEED = 400;
        BACKGROUND_SPEED = 400*2/3;

        doggoX = 50;
        doggoY = 20;
    }

    private void initResources() {
        font = new BitmapFont(Gdx.files.internal("hud.fnt"));
        font.getData().setScale(0.2f);

        collisionTex = new Texture(Gdx.files.internal("collisionbounds.png"));

        doggoTex = new Texture(Gdx.files.internal("doggo.png"));
        doggoTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        dogGameOverTex = new Texture(Gdx.files.internal("doggodead.png"));
        doggoBounds = new Rectangle(83, 30, 29, 44);
        doggoSrcDimen = doggoTex.getWidth();

        backTex = new Texture(Gdx.files.internal("background.png"));
        floorTex = new Texture(Gdx.files.internal("floor.png"));
        ceilingTex = new Texture(Gdx.files.internal("ceiling.png"));

        reuseableLayout = new GlyphLayout();

        jumpSound = Gdx.audio.newSound(Gdx.files.internal("jump.ogg"));
        dieSound = Gdx.audio.newSound(Gdx.files.internal("gameover.ogg"));
        gameStartSound = Gdx.audio.newSound(Gdx.files.internal("gamestart.ogg"));
        clickSound = Gdx.audio.newSound(Gdx.files.internal("click.ogg"));

        pauseBackgroundTex = new Texture(Gdx.files.internal("pausebackground.png"));
        pauseButtonTex = new Texture(Gdx.files.internal("pause.png"));
        pauseButtonPressedTex = new Texture(Gdx.files.internal("pause_pressed.png"));

        gameOverBack = new Texture(Gdx.files.internal("gameoverback.png"));
        playButton = new Texture(Gdx.files.internal("playbutton.png"));
        playButtonPressed = new Texture(Gdx.files.internal("playbutton_pressed.png"));
        backButton = new Texture(Gdx.files.internal("backbutton.png"));
        backButtonPressed = new Texture(Gdx.files.internal("backbutton_pressed.png"));

        logo = new Texture(Gdx.files.internal("logo.png"));
        leaderboardButton = new Texture(Gdx.files.internal("leaderboard.png"));
        leaderboardButtonPressed = new Texture(Gdx.files.internal("leaderboard_pressed.png"));
        signInButton = new Texture(Gdx.files.internal("logingoogleplay.png"));
        signInButtonPressed = new Texture(Gdx.files.internal("logingoogleplay_pressed.png"));
        signOutButton = new Texture(Gdx.files.internal("logoutgoogleplay.png"));
        signOutButtonPressed = new Texture(Gdx.files.internal("logoutgoogleplay_pressed.png"));

        initObstaclePool();
    }

    private void initObstaclePool() {
        obstaclePool = new ArrayList<Obstacle>();

        obstaclePool.add(new Milo1());

        obstaclePool.add(new Milk1());
        obstaclePool.add(new Milk2());
        obstaclePool.add(new Milk3());
        obstaclePool.add(new Milk4());
        obstaclePool.add(new Nutella1());
        obstaclePool.add(new Nutella2());
        obstaclePool.add(new Nutella3());
        obstaclePool.add(new Beans1());
        obstaclePool.add(new Beans2());
        obstaclePool.add(new BeansTuna1());
        obstaclePool.add(new Tuna1());
        obstaclePool.add(new Tuna2());
        obstaclePool.add(new Tuna3());
        obstaclePool.add(new Tuna4());
        obstaclePool.add(new Tuna5());
        obstaclePool.add(new Cereal1());
        obstaclePool.add(new Cereal2());
        obstaclePool.add(new Cereal3());
        obstaclePool.add(new Cereal4());
        obstaclePool.add(new Cereal5());

        obstaclePool.add(new Meat1());
        obstaclePool.add(new Meat2());
        obstaclePool.add(new Meat3());
        obstaclePool.add(new Meat4());
        obstaclePool.add(new Sign1());
        obstaclePool.add(new Sign2());
        obstaclePool.add(new Sign3());
        obstaclePool.add(new Sign4());
        obstaclePool.add(new Sign5());
        obstaclePool.add(new Sign6());
        obstaclePool.add(new Sign7());
        obstaclePool.add(new Sign8());
    }

    private void resetObstacles() {
        Iterator<Obstacle> iterator = obstaclePool.iterator();
        while (iterator.hasNext()) {
            Obstacle obstacle = iterator.next();
            obstacle.isActive = false;
        }

        Iterator<Obstacle> activeIterator = activeObstacles.iterator();
        while (activeIterator.hasNext()) {
            activeIterator.next();
            activeIterator.remove();
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        pausedHandler();
        pauseButtonHandler();
        doGameLogic(delta);
        drawGraphics();
    }

    private void pausedHandler() {
        if (!isPaused) return;

        if (Gdx.input.isTouched()) pausePressDown = true;

        if (!Gdx.input.isTouched() && pausePressDown) {
            isPaused = false;
            pausePressDown = false;
            clickSound.play();
            timeHeld = MAX_UPTHRUST;
        }
    }

    private void pauseButtonHandler () {
        if (isPaused || isTitle || gameOverWaitTime <= 0) return;

        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (activity.isPointInSquare(touchPos.x, touchPos.y, viewport.getWorldWidth()/2 + 768/2 - 80, (viewport.getWorldHeight()-432)/2 + 352, 60, 60)) {
                pausePressDown = true;
            }
        }
        if (!Gdx.input.isTouched() && pausePressDown) {
            pausePressDown = false;
            if (!gameOver) {
                clickSound.play();
                isPaused = true;
            }
        }
    }

    private void drawGraphics() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        activity.batch.setProjectionMatrix(camera.combined);
        activity.batch.begin();

        // Draw background
        activity.batch.draw(backTex, backgroundX, 0, 827, 432);
        activity.batch.draw(backTex, backgroundX + 827, 0, 827, 432);
        activity.batch.draw(backTex, backgroundX - 827, 0, 827, 432);

        // Draw floor
        activity.batch.draw(floorTex, foregroundX, -181, 480, 218);
        activity.batch.draw(floorTex, foregroundX+480, -181, 480, 218);
        activity.batch.draw(floorTex, foregroundX+960, -181, 480, 218);
        activity.batch.draw(floorTex, foregroundX-480, -181, 480, 218);

        // Draw obstacles
        for (Obstacle obstacle : activeObstacles) {
            activity.batch.draw(obstacle.texture, obstacle.x, obstacle.y, obstacle.width, obstacle.height);
        }

        // Draw doggo
        Texture drawDogTex = gameOver ? dogGameOverTex : doggoTex;
        activity.batch.draw(drawDogTex, doggoX-13, doggoY-4, doggoCmX, doggoCmY, 110, 110, 1, 1,
                getDoggoRotation(), 0, 0 , doggoSrcDimen, doggoSrcDimen, false, false);

        // Draw collision boxes
        if (DISPLAY_COLLISION) {
            activity.batch.draw(collisionTex, doggoBounds.x, doggoBounds.y, doggoBounds.width, doggoBounds.height);
            for (Obstacle ob : activeObstacles) {
                for (Rectangle rect : ob.collisionBoxes) {
                    activity.batch.draw(collisionTex, rect.x + ob.x, rect.y + ob.y, rect.width, rect.height);
                }
            }
        }

        // Draw ceiling
        activity.batch.draw(ceilingTex, foregroundX, 418, 480, 180);
        activity.batch.draw(ceilingTex, foregroundX+480, 418, 480, 180);
        activity.batch.draw(ceilingTex, foregroundX+960, 418, 480, 180);
        activity.batch.draw(ceilingTex, foregroundX-480, 418, 480, 180);

        // Draw HUD
        if (!isTitle) {
            font.draw(activity.batch, "" + score, (768 - viewport.getWorldWidth()) / 2 + 20, (viewport.getWorldHeight() - 432) / 2 + 412);
        }
        //font.draw(activity.batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), (768-viewport.getWorldWidth())/2 + 20,(viewport.getWorldHeight()-432)/2 + 412 - 60);


        // Draw Paused Button
        if (!isPaused && !isTitle) {
            activity.batch.draw(pausePressDown ? pauseButtonPressedTex : pauseButtonTex, viewport.getWorldWidth()/2 + 768/2 - 80, (viewport.getWorldHeight()-432)/2 + 352, 60, 60);
        }

        // Draw Paused
        if (isPaused) {
            activity.batch.draw(pauseBackgroundTex, (768 - viewport.getWorldWidth()) / 2, (432 - viewport.getWorldHeight()) / 2, viewport.getWorldWidth(), viewport.getWorldHeight());
            reuseableLayout.setText(font, "PAUSED");
            font.draw(activity.batch, reuseableLayout, 384 - reuseableLayout.width/2, 216 - reuseableLayout.height/2 + 70);

            reuseableLayout.setText(font, "Tap to resume");
            font.draw(activity.batch, reuseableLayout, 384 - reuseableLayout.width/2, 216 - reuseableLayout.height/2);
        }

        if (gameOver && gameOverWaitTime <= 0) {
            drawGameOverScreen();
        }

        if(isTitle) {
            titleScreenRender();
        }

        activity.batch.end();
    }

    private float getDoggoRotation () {
        XVELOCITY = (float) getObstacleSpeed()*2500/400;
        return doggoY > FLOOR_HEIGHT ? (float) (Math.atan(yVelocity / XVELOCITY) * 180 / Math.PI) : 0;
    }

    private void doGameLogic (float delta) {
        if (isPaused) return;
        if (isTitle) {
            titleScreenLogic();
            moveBackground(delta);
        }

        if(!gameOver && !isTitle) {
            detectObstacle();
            moveObstacles(delta);
            moveDoggo(delta);
            generateObstacles(delta);
            moveBackground(delta);
        }

        if (gameOverWaitTime <= 0) {
            handleGameOverScreen();
        }

        if (gameOver) {
            gameOverWaitTime -= delta;
        }

        doggoBounds.x = doggoX+25;
        doggoBounds.y = doggoY+21;
    }

    private void handleGameOverScreen() {
        if (gameOver && isPaused) isPaused = false;

        boolean isTouchingBack = false;
        boolean isTouchingPlay = false;
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            // TODO take out constants
            isTouchingPlay = activity.isPointInSquare(touchPos.x, touchPos.y, 391.5f, 35, 150, 150);
            isTouchingBack = activity.isPointInSquare(touchPos.x, touchPos.y, 226.5f, 35, 150, 150);
        }

        if (Gdx.input.justTouched()) {
            if (isTouchingBack) backIsPressed = true;
            if (isTouchingPlay) playIsPressed = true;
        }
        else if (Gdx.input.isTouched()) {
            if (!isTouchingBack && backIsPressed) backIsPressed = false;
            if (!isTouchingPlay && playIsPressed) playIsPressed = false;
        }
        else if (!Gdx.input.isTouched()) {
            if (backIsPressed) goBackScreen();
            if (playIsPressed) startGame(true);
            backIsPressed = false;
            playIsPressed = false;
        }

    }

    private void titleScreenLogic() {
        float buttonSideLength = 150;
        float playButtonX = 768/2 - buttonSideLength*1.1f;
        float leaderboardButtonX = 768/2 + buttonSideLength*0.1f;
        float buttonsY = 20;

        if (isPaused && isTitle) isPaused = false;

        // TODO Yucky
        boolean isTouchingPlay = false;
        boolean isTouchingLeader = false;
        boolean isTouchingLogOff = false;
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            isTouchingPlay = activity.isPointInSquare(touchPos.x, touchPos.y, playButtonX, buttonsY, buttonSideLength, buttonSideLength);
            isTouchingLeader = activity.isPointInSquare(touchPos.x, touchPos.y, leaderboardButtonX, buttonsY, buttonSideLength, buttonSideLength);
            isTouchingLogOff = activity.isPointInSquare(touchPos.x, touchPos.y, (viewport.getWorldWidth() + 768)/2 - 80, 20, 60, 60);
        }

        if(Gdx.input.justTouched()) {
            if (isTouchingPlay) isPlayPressed = true;
            if (isTouchingLeader) isLeaderboardPressed = true;
            if (isTouchingLogOff) isLogOutPressed = true;
        }
        else if (Gdx.input.isTouched()) {
            if (!isTouchingPlay && isPlayPressed) isPlayPressed = false;
            if (!isTouchingLeader && isLeaderboardPressed) isLeaderboardPressed = false;
            if (!isTouchingLogOff && isLogOutPressed) isLogOutPressed = false;
        }
        else if (!Gdx.input.isTouched()) {
            if (isPlayPressed) startGame(false);
            if (isLeaderboardPressed) {
                clickSound.stop();
                clickSound.play();
                if (activity.gsClient.isSessionActive()) openPlayGamesLeaderboard();
                else activity.gsClient.logIn();
            }
            if (isLogOutPressed && activity.gsClient.isSessionActive()) {
                clickSound.stop();
                clickSound.play();
                activity.gsClient.logOff();
            }
            isPlayPressed = false;
            isLeaderboardPressed = false;
            isLogOutPressed = false;
        }
    }

    private void titleScreenRender() {
        float buttonSideLength = 150;
        float playButtonX = 768/2 - buttonSideLength*1.1f;
        float leaderboardButtonX = 768/2 + buttonSideLength*0.1f;
        float buttonsY = 20;

        activity.batch.draw(logo, 768/2-400/2, 432/2 + 5, 400, 200);

        Texture leaderboardTexture = activity.gsClient.isSessionActive() ? (isLeaderboardPressed ? leaderboardButtonPressed : leaderboardButton) :
                (isLeaderboardPressed ? signInButtonPressed : signInButton);

        activity.batch.draw(isPlayPressed ? playButtonPressed : playButton, playButtonX, buttonsY, buttonSideLength, buttonSideLength);
        activity.batch.draw(leaderboardTexture, leaderboardButtonX, buttonsY, buttonSideLength, buttonSideLength);
        if (activity.gsClient.isSessionActive()) {
            activity.batch.draw(isLogOutPressed ? signOutButtonPressed : signOutButton, (viewport.getWorldWidth() + 768) / 2 - 80, 20, 60, 60);
        }

        activity.hiScore = activity.prefs.getInteger("hiscore", 0); // TODO can i move this so not every frame?
        reuseableLayout.setText(font, "HIGH SCORE: "+ activity.hiScore);
        final float fontX = 384 - reuseableLayout.width/2;
        final float fontY =  216 - reuseableLayout.height/2 + 15;
        font.draw(activity.batch, reuseableLayout, fontX, fontY);
    }

    private void startGame (boolean resetVars) {
        clickSound.play();
        gameStartSound.play();
        isTitle = false;
        resetObstacles();
        if (resetVars) initVariables();
    }

    private void openPlayGamesLeaderboard () {
        try {
            activity.gsClient.showLeaderboards(activity.LEADERBOARD_ID);
        } catch (GameServiceException e) {
            System.err.println("Unable to connect to Google Play Services Leaderboards...");
            e.printStackTrace();
        }
    }

    private void goBackScreen () {
        clickSound.play();
        initVariables();
        resetObstacles();
        isTitle = true;
    }

    private void drawGameOverScreen() {
        activity.batch.draw(pauseBackgroundTex, (768 - viewport.getWorldWidth()) / 2, (432 - viewport.getWorldHeight()) / 2, viewport.getWorldWidth(), viewport.getWorldHeight());
        activity.batch.draw(gameOverBack, 188, 20, 392, 392);

        activity.batch.draw(backIsPressed ? backButtonPressed : backButton, 226.5f, 35, 150, 150);
        activity.batch.draw(playIsPressed ? playButtonPressed : playButton, 391.5f, 35, 150, 150);

        reuseableLayout.setText(font, "GAME OVER");
        font.draw(activity.batch, reuseableLayout, 384 - reuseableLayout.width/2, 392);

        reuseableLayout.setText(font, "SCORE: " + score);
        font.draw(activity.batch, reuseableLayout, 384 - reuseableLayout.width/2, 302);

        String bottomText = isNewHiScore ? "NEW HIGH SCORE!" : "HIGH SCORE: " + activity.hiScore;
        reuseableLayout.setText(font, bottomText);
        font.draw(activity.batch, reuseableLayout, 384 - reuseableLayout.width/2, 242);
    }

    private void moveBackground (float delta) {
        OBSTACLE_SPEED = (float) getObstacleSpeed();
        BACKGROUND_SPEED = OBSTACLE_SPEED*2/3;

        foregroundX -= delta * OBSTACLE_SPEED;
        backgroundX -= delta * BACKGROUND_SPEED;
        if (foregroundX < -480) foregroundX +=480;
        if (backgroundX < -1*BACK_TILE) backgroundX += BACK_TILE;
    }

    private void detectObstacle () {
        for (Obstacle obstacle : activeObstacles) {
            if (obstacle.overlaps(doggoBounds)) {
                gameOver();
            }
        }
    }

    private void gameOver() {
        if (gameOver) return; //So that this only executes once.
        dieSound.play();
        isNewHiScore = score > activity.hiScore;
        gameOver = true;
        activity.updateScore(score);
    }

    private void generateObstacles (float delta) {
        widthBottom -= delta*getObstacleSpeed();
        widthTop -= delta*getObstacleSpeed();

        Collections.shuffle(obstaclePool);

        if (widthTop < -110) heightTop = 0;
        if (widthBottom < -110) heightBottom = 0;

        if (Math.random() < 0.5f) {
            generateTop();
            generateBottom();
        }
        else {
            generateBottom();
            generateTop();
        }

    }

    private void generateTop () {
        if (widthBottom <= -110) {
            for (Obstacle obstacle : obstaclePool) {
                if (!obstacle.isActive && obstacle.groundedItem && obstacle.getHeight() + heightTop + getNarrowness() < 407) {
                    obstacle.x = (int) viewport.getWorldWidth();
                    obstacle.y = obstacle.groundedItem ?
                            FLOOR_HEIGHT - obstacle.groundLevel : 432 - obstacle.height;
                    obstacle.isActive = true;
                    obstacle.hasScored = false;
                    if ((Math.random() < getNearFrequency() && widthTop > 0) || (widthTop < 0)) activeObstacles.add(obstacle);
                    widthBottom = obstacle.width + generatorExtend();
                    heightBottom = obstacle.getHeight();
                    break;
                }
            }
        }
    }

    private void generateBottom () {
        if (widthTop <= -110) {
            for (Obstacle obstacle : obstaclePool) {
                if (!obstacle.isActive && !obstacle.groundedItem && obstacle.getHeight() + heightBottom + getNarrowness() < 407) {
                    obstacle.x = (int) viewport.getWorldWidth();
                    obstacle.y = obstacle.groundedItem ?
                            FLOOR_HEIGHT - obstacle.groundLevel : 432 - obstacle.height;
                    obstacle.isActive = true;
                    obstacle.hasScored = false;
                    if ((Math.random() < getNearFrequency() && widthBottom > 0) || (widthBottom < 0)) activeObstacles.add(obstacle);
                    widthTop = obstacle.width + generatorExtend();
                    heightTop = obstacle.getHeight();
                    break;
                }
            }
        }
    }

    private float getNearFrequency() {
        if (score < 10) {
            return 0;
        }
        if (score < 20) {
            return 0.5f;
        }
        if (score < 50) {
            return 0.6f;
        }
        return 0.75f;
    }

    private float getNarrowness () {
        if (score < 20) {
            return 200;
        }
        if (score < 50) {
            return 175;
        }
        if (score < 80) {
            return 165;
        }
        if (score < 130) {
            return 140;
        }
        return 130;
    }

    /*private void generateObstacles (float delta) {
        timeSinceLastObstacleTop += delta;
        timeSinceLastObstacleBottom += delta;
        Collections.shuffle(obstaclePool);
        if (timeSinceLastObstacleTop > 0.15f) {
            for (Obstacle obstacle : obstaclePool) {
                if (!obstacle.isActive) {
                    obstacle.x = (int) viewport.getWorldWidth();
                    obstacle.y = obstacle.groundedItem ?
                            FLOOR_HEIGHT - obstacle.groundLevel : (int) (432 - (obstacle.height*(0.5 + Math.random()/2)));
                    obstacle.isActive = true;
                    obstacle.hasScored = false;
                    activeObstacles.add(obstacle);
                    float timeSinceLastExtend = (float) generatorTimeExtend();
                    timeSinceLastObstacleTop = -1*obstacle.width/((float) getObstacleSpeed()) - timeSinceLastExtend;
                    break;
                }
            }
        }
    }*/

    private float generatorExtend () {
        if (score < 20) {
            return (float) Math.random()*200;
        }
        if (score < 50) {
            return (float) Math.random()*120;
        }
        if (score < 80) {
            return (float) Math.random()*100;
        }
        if (score < 120) {
            return (float) Math.random()*50;
        }
        if (score < 150) {
            return (float) Math.random()*30;
        }
        return (float) Math.random()*20;
    }

    private void moveObstacles (float delta) {
        Iterator<Obstacle> iterator = activeObstacles.iterator();
        while (iterator.hasNext()) {
            Obstacle obstacle = iterator.next();
            obstacle.x -= (float) getObstacleSpeed() * delta;
            if (obstacle.x + obstacle.width < doggoX && !obstacle.hasScored && !gameOver) {
                score++;
                obstacle.hasScored = true;
            }
            if (obstacle.x + obstacle.width < -1*(viewport.getWorldWidth() - 432)/2) {
                obstacle.isActive = false;
                iterator.remove();
            }
        }
    }

    private double getObstacleSpeed () {
        return getObstacleSpeed(this.score);
    }

    private double getObstacleSpeed (float score) {
        if (score < 50) {
            return 400 + score;
        }
        if (score < 100) {
            return 450 + 1.5*(score-50);
        }
        return 525 + 2.25*(score-100);
    }

    private void moveDoggo(float delta) {
        yVelocity -= GRAVITY*delta;

        if (Gdx.input.justTouched() && !pausePressDown && !gameOver) {
            jumpSound.play();
        }

        if (Gdx.input.isTouched() && !pausePressDown && !gameOver) {
            timeHeld += delta;
            if (timeHeld < MAX_UPTHRUST) {
                yVelocity = UPTHRUST_STRENGTH*timeHeld/MAX_UPTHRUST;
            }
        }
        else {
            timeHeld = 0;
        }

        doggoY += yVelocity*delta;
        if (doggoY <= FLOOR_HEIGHT) {
            doggoY = FLOOR_HEIGHT;
        }
        if (doggoY > 428 - 50) {
            doggoY = 428 - 50;
            yVelocity = -0.3f* yVelocity;
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
        if (!gameOver && !isTitle) isPaused = true;
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        collisionTex.dispose();
        floorTex.dispose();
        ceilingTex.dispose();
        backTex.dispose();
        doggoTex.dispose();
        dogGameOverTex.dispose();
        gameOverBack.dispose();
        playButton.dispose();
        playButtonPressed.dispose();
        backButton.dispose();
        backButtonPressed.dispose();
        logo.dispose();
        leaderboardButton.dispose();
        leaderboardButtonPressed.dispose();
        signOutButton.dispose();
        signOutButtonPressed.dispose();
        signInButtonPressed.dispose();
        signInButton.dispose();
    }
}
