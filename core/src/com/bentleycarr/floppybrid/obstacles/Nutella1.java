package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Nutella1 extends Obstacle {

    public Nutella1() {
        this.typeId = 7;
        this.boundingHeight = 291;

        this.texture = new Texture(Gdx.files.internal("nutella1.png"));
        this.width = 75;
        this.height = 150;

        this.groundLevel = 10;

        this.groundedItem = true;

        collisionBoxes = new Rectangle[] {new Rectangle(4,0,34,97),
            new Rectangle(38,0,30,143)};
    }
}
