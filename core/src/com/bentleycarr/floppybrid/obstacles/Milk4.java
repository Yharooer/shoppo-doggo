package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Milk4 extends Obstacle {

    public Milk4() {
        this.typeId = 7;

        this.texture = new Texture(Gdx.files.internal("milk4.png"));
        this.width = 573;
        this.height = 135;

        this.groundLevel = 2;

        this.groundedItem = true;

        collisionBoxes = new Rectangle[] {new Rectangle(143,0,67, 54),
                new Rectangle(263,0,130, 76), new Rectangle(433,0,131, 126)};
    }
}
