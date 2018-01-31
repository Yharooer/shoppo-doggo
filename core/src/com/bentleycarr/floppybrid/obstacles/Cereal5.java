package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Cereal5 extends Obstacle {

    public Cereal5() {
        this.typeId = 7;

        this.texture = new Texture(Gdx.files.internal("cereal5.png"));
        this.width = 308;
        this.height = 247;

        this.groundLevel = 6;

        this.groundedItem = true;

        collisionBoxes = new Rectangle[] {new Rectangle(10,0,291,124),
                new Rectangle(58,124,191,116)};
    }
}
