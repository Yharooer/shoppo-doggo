package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Milk3 extends Obstacle {

    public Milk3() {
        this.typeId = 7;

        this.texture = new Texture(Gdx.files.internal("milk3.png"));
        this.width = 363;
        this.height = 135;

        this.groundLevel = 5;

        this.groundedItem = true;

        collisionBoxes = new Rectangle[] {new Rectangle(11,0,343, 125)};
    }
}
