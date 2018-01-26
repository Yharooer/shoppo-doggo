package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class FishSign2 extends Obstacle {

    public FishSign2() {
        this.typeId = 5;
        this.boundingHeight = 269;

        this.texture = new Texture(Gdx.files.internal("fishsign2.png"));
        this.width = 134;
        this.height = 201;

        this.groundLevel = 0;

        this.groundedItem = false;

        collisionBoxes = new Rectangle[] {new Rectangle(10,14,115,187)};
    }
}
