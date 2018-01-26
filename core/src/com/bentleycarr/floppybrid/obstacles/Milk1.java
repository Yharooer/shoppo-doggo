package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Milk1 extends Obstacle {

    public Milk1() {
        this.typeId = 7;
        this.boundingHeight = 291;

        this.texture = new Texture(Gdx.files.internal("milk1.png"));
        this.width = 143;
        this.height = 100;

        this.groundLevel = 2;

        this.groundedItem = true;

        collisionBoxes = new Rectangle[] {new Rectangle(0,0,136,98)};
    }
}
