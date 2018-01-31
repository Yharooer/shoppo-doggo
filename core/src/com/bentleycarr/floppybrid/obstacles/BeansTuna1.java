package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class BeansTuna1 extends Obstacle {

    public BeansTuna1() {
        this.typeId = 3;

        this.texture = new Texture(Gdx.files.internal("beanstuna1.png"));
        this.width = 47;
        this.height = 124;

        this.groundLevel = 2;

        this.groundedItem = true;

        collisionBoxes = new Rectangle[] {new Rectangle(3,0,39.6f,119.4f)};
    }
}
