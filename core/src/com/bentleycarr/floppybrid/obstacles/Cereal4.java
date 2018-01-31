package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Cereal4 extends Obstacle {

    public Cereal4() {
        this.typeId = 7;

        this.texture = new Texture(Gdx.files.internal("cereal4.png"));
        this.width = 303;
        this.height = 130;

        this.groundLevel = 4;

        this.groundedItem = true;

        collisionBoxes = new Rectangle[] {new Rectangle(11,0,288,122)};
    }
}
