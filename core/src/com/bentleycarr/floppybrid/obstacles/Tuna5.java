package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Tuna5 extends Obstacle {

    public Tuna5() {
        this.typeId = 1;

        this.texture = new Texture(Gdx.files.internal("tuna5.png"));
        this.width = 95;
        this.height = 158;

        this.groundLevel = 5;

        this.groundedItem = true;

        collisionBoxes = new Rectangle[] {new Rectangle(3,0,89,153)};
    }
}
