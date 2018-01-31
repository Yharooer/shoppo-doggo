package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Meat1 extends Obstacle {

    public Meat1() {
        this.typeId = 6;

        this.texture = new Texture(Gdx.files.internal("meat1.png"));
        this.width = 237;
        this.height = 210;

        this.groundLevel = 0;

        this.groundedItem = false;

        collisionBoxes = new Rectangle[] {new Rectangle(5,70,72,142),
            new Rectangle(77,0,67,210), new Rectangle(168,32,67,179)};
    }
}
