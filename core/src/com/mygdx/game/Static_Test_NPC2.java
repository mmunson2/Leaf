package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;


public class Static_Test_NPC2 {
    public static final int PLAYER_SPEED = 5;

    private float xPos;
    private float yPos;

    private Direction direction;

    private final Texture texture;

    private static final String PLAYER_DOWN_NAME = "player_down";

    public Static_Test_NPC2() {
        this.texture = new Texture("core\\assets\\DemoRoom3\\npc2.png");

        this.direction = Direction.DOWN;
        this.xPos = 1320;
        this.yPos = 1270;
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
