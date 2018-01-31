package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Tuna4 extends Obstacle {

    public Tuna4() {
        this.typeId = 1;

        this.texture = new Texture(Gdx.files.internal("tuna4.png"));
        this.width = 143;
        this.height = 158;

        this.groundLevel = 5;

        this.groundedItem = true;

        collisionBoxes = new Rectangle[] {new Rectangle(2,0,42,63.6f),
                new Rectangle(52,0,38.4f,105), new Rectangle(100,0,39.6f,154.8f)};
    }
}
