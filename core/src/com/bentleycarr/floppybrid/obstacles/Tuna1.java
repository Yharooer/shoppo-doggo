package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Tuna1 extends Obstacle {

    public Tuna1() {
        this.typeId = 0;

        this.texture = new Texture(Gdx.files.internal("tuna1.png"));
        this.width = 46;
        this.height = 38;

        this.groundLevel = 5;

        this.groundedItem = true;

        collisionBoxes = new Rectangle[] {new Rectangle(2.4f,0,40.2f,34.2f)};
    }
}
