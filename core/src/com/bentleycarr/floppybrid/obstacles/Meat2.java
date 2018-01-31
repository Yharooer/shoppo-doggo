package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Meat2 extends Obstacle {

    public Meat2() {
        this.typeId = 6;

        this.texture = new Texture(Gdx.files.internal("meat2.png"));
        this.width = 215;
        this.height = 290;

        this.groundLevel = 0;

        this.groundedItem = false;

        collisionBoxes = new Rectangle[] {new Rectangle(28,90,58,200),
            new Rectangle(106,0,64,290)};
    }
}
