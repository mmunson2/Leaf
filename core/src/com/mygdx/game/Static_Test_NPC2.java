package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.io.File;


public class Static_Test_NPC2 {
    private float xPos;
    private float yPos;

    private final Texture texture;

    private static final String PLAYER_DOWN_NAME = "player_down";
    private static final String name = "Barnaby";

    public Static_Test_NPC2() {
        this.texture = new Texture("DemoRoom3" + File.separator + "npc2.png");

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

    public String getName() { return this.name; }
}
