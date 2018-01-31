package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Meat3 extends Obstacle {

    public Meat3() {
        this.typeId = 6;

        this.texture = new Texture(Gdx.files.internal("meat3.png"));
        this.width = 78;
        this.height = 208;

        this.groundLevel = 0;

        this.groundedItem = false;

        collisionBoxes = new Rectangle[] {new Rectangle(4,9,71,201)};
    }
}
