package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Beans2 extends Obstacle {

    public Beans2() {
        this.typeId = 3;

        this.texture = new Texture(Gdx.files.internal("beans2.png"));
        this.width = 46;
        this.height = 137;

        this.groundLevel = 2;

        this.groundedItem = true;

        collisionBoxes = new Rectangle[] {new Rectangle(3,3.6f,37.8f,131.4f)};
    }
}
