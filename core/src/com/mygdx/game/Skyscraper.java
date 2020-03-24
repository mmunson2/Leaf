package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Skyscraper
{
    private int xPos;
    private int yPos;

    private int heightFactor;

    TextureRegion standardTexture;

    private Rectangle collisionBounds;

    public Skyscraper(int xPos, int yPos, int heightFactor, TextureAtlas atlas)
    {
        this.xPos = xPos;
        this.yPos = yPos;

        this.heightFactor = heightFactor;

        standardTexture = atlas.findRegion("Skyscraper");

        collisionBounds = new Rectangle();
        collisionBounds.x = xPos;
        collisionBounds.y = yPos;
        collisionBounds.width = standardTexture.getRegionWidth();
        collisionBounds.height = standardTexture.getRegionHeight() * heightFactor;
    }

    public TextureRegion getTexture()
    {
        return standardTexture;
    }

    public boolean overlaps(Rectangle other)
    {
        return other.overlaps(collisionBounds);
    }

    public Rectangle getCollisionBounds()
    {
        return collisionBounds;
    }

    public int getXPos()
    {
        return xPos;
    }

    public int getyPos()
    {
        return yPos;
    }

    public float getHeight() { return standardTexture.getRegionHeight(); }

    public int getHeightFactor() {return heightFactor;}



}
