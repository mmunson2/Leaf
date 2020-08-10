package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Static_Test_NPC {
    private float xPos;
    private float yPos;

    private Direction direction;

    private final Texture texture;

    private static final String name = "Eustace";

    public Static_Test_NPC(TextureAtlas atlas) {
        this.texture = new Texture("core\\assets\\DemoRoom3\\npc.png");

        this.direction = Direction.DOWN;
        this.xPos = 2040;
        this.yPos = 1110;
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
