package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Nutella1 extends Obstacle {

    public Nutella1() {
        this.typeId = 7;

        this.texture = new Texture(Gdx.files.internal("nutella1.png"));
        this.width = 69;
        this.height = 99;

        this.groundLevel = 2;

        this.groundedItem = true;

        collisionBoxes = new Rectangle[] {new Rectangle(3,2,64,94)};
    }
}
