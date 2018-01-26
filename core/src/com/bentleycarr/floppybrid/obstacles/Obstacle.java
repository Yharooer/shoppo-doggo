package com.bentleycarr.floppybrid.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Obstacle {
    public int typeId;
    public int boundingHeight; // Used for algorithm
    public Texture texture; // TODO If I have more than one obstacle of same type in the pool, inefficient.
    public int width;
    public int height;
    public float x;
    public float y;
    public int groundLevel;
    public boolean groundedItem;
    public Rectangle[] collisionBoxes;

    public boolean hasScored = false;
    public boolean isActive = false;

    public boolean overlaps (Rectangle rect) {
        for (Rectangle box:collisionBoxes) {
            if (doIntersect(rect.x, rect.y, rect.width, rect.height,
                    box.x+this.x, box.y+this.y, box.width, box.height)) return true;
        }
        return false;
    }

    private boolean doIntersect(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2) {
        return x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2;
        //return new Rectangle(x1,y1,w1,h1).overlaps(new Rectangle (x2,y2,w2,h2));
        //return (Math.abs(x1 - x2) * 2 < (w1 + w2)) && (Math.abs(y1 - y2) * 2 < (h1 + h2));
    }
}
