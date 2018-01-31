package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Sign1 extends Obstacle {

    public Sign1() {
        this.typeId = 5;

        this.texture = new Texture(Gdx.files.internal("sign1.png"));
        this.width = 106;
        this.height = 146;

        this.groundLevel = 0;

        this.groundedItem = false;

        collisionBoxes = new Rectangle[] {new Rectangle(5,7,96,140)};
    }
}
