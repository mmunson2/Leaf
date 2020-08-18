package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class SpeechBox {

    private float xPos;
    private float yPos;

    private float heightFactor;

    Texture standardTexture;

    private Rectangle collisionBounds;

    public SpeechBox(float xPos, float yPos) {
        this.xPos = xPos;
        this.yPos = yPos;

        this.heightFactor = heightFactor;

        standardTexture = new Texture("core\\assets\\SpeechBox.png");
    }

    public Texture getTexture() {
        return standardTexture;
    }

    public boolean overlaps(Rectangle other) {
        return other.overlaps(collisionBounds);
    }

    public Rectangle getCollisionBounds() {
        return collisionBounds;
    }

    public float getXPos() {
        return this.xPos;
    }

    public float getYPos() {
        return this.yPos;
    }

    public float getWidth() {
        return standardTexture.getWidth();
    }

    public float getHeight() {
        return standardTexture.getHeight();
    }
}
