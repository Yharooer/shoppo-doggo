package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Milo1 extends Obstacle {

    public Milo1() {
        this.typeId = 2;
        this.boundingHeight = 269;

        this.texture = new Texture(Gdx.files.internal("milo1.png"));
        this.width = 315;
        this.height = 270;

        this.groundLevel = 18;

        this.groundedItem = true;

        collisionBoxes = new Rectangle[] {new Rectangle(106,0,100,265)};
    }
}
