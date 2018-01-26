package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Beans1 extends Obstacle {

    public Beans1() {
        this.typeId = 3;
        this.boundingHeight = 269;

        this.texture = new Texture(Gdx.files.internal("beans1.png"));
        this.width = 82;
        this.height = 125;

        this.groundLevel = 18;

        this.groundedItem = true;

        collisionBoxes = new Rectangle[] {new Rectangle(8,0,68,118)};
    }
}
