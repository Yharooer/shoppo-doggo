package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Tuna2 extends Obstacle {

    public Tuna2() {
        this.typeId = 1;
        this.boundingHeight = 191;

        this.texture = new Texture(Gdx.files.internal("tuna2.png"));
        this.width = 200;
        this.height = 200;

        this.groundLevel = 12;

        this.groundedItem = true;

        collisionBoxes = new Rectangle[] {new Rectangle(36,0,128,191)};
    }
}
