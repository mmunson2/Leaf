package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Player
{
    public static final int PLAYER_SPEED = 5;

    private float xPos;
    private float yPos;

    private Direction direction;

    private final TextureRegion player_up;
    private final TextureRegion player_down;
    private final TextureRegion player_left;
    private final TextureRegion player_right;
    private final TextureRegion player_up_left;
    private final TextureRegion player_up_right;
    private final TextureRegion player_down_left;
    private final TextureRegion player_down_right;

    private static final String PLAYER_UP_NAME = "player_up";
    private static final String PLAYER_DOWN_NAME = "player_down";
    private static final String PLAYER_LEFT_NAME = "player_left";
    private static final String PLAYER_RIGHT_NAME = "player_right";
    private static final String PLAYER_UP_LEFT_NAME = "player_up_left";
    private static final String PLAYER_UP_RIGHT_NAME = "player_up_right";
    private static final String PLAYER_DOWN_LEFT_NAME = "player_down_left";
    private static final String PLAYER_DOWN_RIGHT_NAME = "player_down_right";

    public Player(TextureAtlas atlas)
    {
        this.player_up = atlas.findRegion(PLAYER_UP_NAME);
        this.player_down = atlas.findRegion(PLAYER_DOWN_NAME);
        this.player_left = atlas.findRegion(PLAYER_LEFT_NAME);
        this.player_right = atlas.findRegion(PLAYER_RIGHT_NAME);
        this.player_up_left = atlas.findRegion(PLAYER_UP_LEFT_NAME);
        this.player_up_right = atlas.findRegion(PLAYER_UP_RIGHT_NAME);
        this.player_down_left = atlas.findRegion(PLAYER_DOWN_LEFT_NAME);
        this.player_down_right = atlas.findRegion(PLAYER_DOWN_RIGHT_NAME);

        this.direction = Direction.RIGHT;
        this.xPos = 250;
        this.yPos = 200;
    }

    public TextureRegion getTexture()
    {
        switch(direction)
        {
            case UP: return player_up;
            case DOWN: return player_down;
            case LEFT: return player_left;
            case RIGHT: return player_right;
            case UP_LEFT: return player_up_left;
            case UP_RIGHT: return player_up_right;
            case DOWN_LEFT: return player_down_left;
            case DOWN_RIGHT: return player_down_right;
        }

        return null;
    }

    public void move(Direction direction)
    {
        if(direction == null)
        {
            return;
        }

        this.direction = direction;

        switch(direction)
        {
            case UP:
                this.yPos += PLAYER_SPEED;
                break;
            case DOWN:
                this.yPos -= PLAYER_SPEED;
                break;
            case LEFT:
                this.xPos -= PLAYER_SPEED;
                break;
            case RIGHT:
                this.xPos += PLAYER_SPEED;
                break;
            case UP_LEFT:
                this.yPos += PLAYER_SPEED;
                this.xPos -= PLAYER_SPEED;
                break;
            case UP_RIGHT:
                this.yPos += PLAYER_SPEED;
                this.xPos += PLAYER_SPEED;
                break;
            case DOWN_LEFT:
                this.yPos -= PLAYER_SPEED;
                this.xPos -= PLAYER_SPEED;
                break;
            case DOWN_RIGHT:
                this.yPos -= PLAYER_SPEED;
                this.xPos += PLAYER_SPEED;
        }
    }


    public void update()
    {

    }

    public float getXPos()
    {
        return this.xPos;
    }

    public float getYPos()
    {
        return this.yPos;
    }






}
