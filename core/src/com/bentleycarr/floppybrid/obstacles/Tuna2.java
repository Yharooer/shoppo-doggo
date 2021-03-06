package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Tuna2 extends Obstacle {

    public Tuna2() {
        this.typeId = 1;

        this.texture = new Texture(Gdx.files.internal("tuna2.png"));
        this.width = 45;
        this.height = 85;

        this.groundLevel = 5;

        this.groundedItem = true;

        collisionBoxes = new Rectangle[] {new Rectangle(3,0,39.6f,81.6f)};
    }
}
