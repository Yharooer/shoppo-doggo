package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Meat4 extends Obstacle {

    public Meat4() {
        this.typeId = 6;

        this.texture = new Texture(Gdx.files.internal("meat4.png"));
        this.width = 78;
        this.height = 144;

        this.groundLevel = 0;

        this.groundedItem = false;

        collisionBoxes = new Rectangle[] {new Rectangle(7,7,69,139)};
    }
}
