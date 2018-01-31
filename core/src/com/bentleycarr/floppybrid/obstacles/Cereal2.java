package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Cereal2 extends Obstacle {

    public Cereal2() {
        this.typeId = 7;

        this.texture = new Texture(Gdx.files.internal("cereal2.png"));
        this.width = 100;
        this.height = 129;

        this.groundLevel = 4;

        this.groundedItem = true;

        collisionBoxes = new Rectangle[] {new Rectangle(10,0,84,124)};
    }
}
