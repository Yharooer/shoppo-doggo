package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Nutella2 extends Obstacle {

    public Nutella2() {
        this.typeId = 7;

        this.texture = new Texture(Gdx.files.internal("nutella2.png"));
        this.width = 131;
        this.height = 99;

        this.groundLevel = 2;

        this.groundedItem = true;

        collisionBoxes = new Rectangle[] {new Rectangle(3,2,125,94)};
    }
}
