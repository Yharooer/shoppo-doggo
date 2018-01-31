package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Nutella3 extends Obstacle {

    public Nutella3() {
        this.typeId = 7;

        this.texture = new Texture(Gdx.files.internal("nutella3.png"));
        this.width = 38;
        this.height = 54;

        this.groundLevel = 2;

        this.groundedItem = true;

        collisionBoxes = new Rectangle[] {new Rectangle(3,2,32,49)};
    }
}
