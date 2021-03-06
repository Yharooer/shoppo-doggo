package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Beans1 extends Obstacle {

    public Beans1() {
        this.typeId = 3;

        this.texture = new Texture(Gdx.files.internal("beans1.png"));
        this.width = 46;
        this.height = 77;

        this.groundLevel = 2;

        this.groundedItem = true;

        collisionBoxes = new Rectangle[] {new Rectangle(2.4f,2.4f,39,69)};
    }
}
