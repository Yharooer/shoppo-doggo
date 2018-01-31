package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Tuna3 extends Obstacle {

    public Tuna3() {
        this.typeId = 1;

        this.texture = new Texture(Gdx.files.internal("tuna3.png"));
        this.width = 99;
        this.height = 65;

        this.groundLevel = 5;

        this.groundedItem = true;

        collisionBoxes = new Rectangle[] {new Rectangle(2.4f,0,92.4f,34.2f),
                new Rectangle(28.8f,34.2f,40.2f,26.4f)};
    }
}
