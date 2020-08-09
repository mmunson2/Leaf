package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Static_Test_NPC {
    public static final int PLAYER_SPEED = 5;

    private float xPos;
    private float yPos;

    private Direction direction;

    private final Texture texture;

    private static final String PLAYER_DOWN_NAME = "player_down";

    public Static_Test_NPC(TextureAtlas atlas) {
        this.texture = new Texture("core\\assets\\Fairy.png");

        this.direction = Direction.DOWN;
        this.xPos = 600;
        this.yPos = 400;
    }

    public Texture getTexture() {
        return texture;
    }

    public float getXPos() {
        return this.xPos;
    }

    public float getYPos() {
        return this.yPos;
    }


}
