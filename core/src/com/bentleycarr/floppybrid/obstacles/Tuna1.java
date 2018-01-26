package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Tuna1 extends Obstacle {

    public Tuna1() {
        this.typeId = 0;
        this.boundingHeight = 183;

        this.texture = new Texture(Gdx.files.internal("tuna1.png"));
        this.width = 200;
        this.height = 200;

        this.groundLevel = 12;

        this.groundedItem = true;

        collisionBoxes = new Rectangle[] {new Rectangle(3,0,56,76),
            new Rectangle(73,0,55,137), new Rectangle(138, 0, 56,195)};
    }
}
