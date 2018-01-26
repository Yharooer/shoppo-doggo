package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Cereal1 extends Obstacle {

    public Cereal1() {
        this.typeId = 7;
        this.boundingHeight = 291;

        this.texture = new Texture(Gdx.files.internal("cereal1.png"));
        this.width = 79;
        this.height = 120;

        this.groundLevel = 4;

        this.groundedItem = true;

        collisionBoxes = new Rectangle[] {new Rectangle(0,0,75,115)};
    }
}
