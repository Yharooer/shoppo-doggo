package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Meat1 extends Obstacle {

    public Meat1() {
        this.typeId = 6;
        this.boundingHeight = 291;

        this.texture = new Texture(Gdx.files.internal("meat1.png"));
        this.width = 129;
        this.height = 300;

        this.groundLevel = 0;

        this.groundedItem = false;

        collisionBoxes = new Rectangle[] {new Rectangle(70,9,38,291),
            new Rectangle(31,42,29,260)};
    }
}
