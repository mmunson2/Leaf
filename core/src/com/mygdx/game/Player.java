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

    private static final float TOP_BOUND = 400;
    private static final float BOTTOM_BOUND = 50;
    private static final float LEFT_BOUND = 0;
    private static final float RIGHT_BOUND = 1100;

    private static final float DIRECTION_MARGIN = 10;

    private boolean movementLocked = false;

    private static double player_health;

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

        player_health = 100;
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
        if(direction == null || this.movementLocked)
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

        checkBounds();
    }

    public void faceTowards(float xPos, float yPos)
    {
        float xDelta = this.xPos - xPos;
        float yDelta = this.yPos - yPos;

        if(Math.abs(xDelta) < DIRECTION_MARGIN)
        {
            xDelta = 0;
        }
        if(Math.abs(yDelta) < DIRECTION_MARGIN)
        {
            yDelta = 0;
        }

        if(yDelta > 0 && xDelta == 0) //Above the target
        {
            this.direction = Direction.DOWN;
        }
        else if(yDelta < 0 && xDelta == 0) //Below the target
        {
            this.direction = Direction.UP;
        }
        else if(xDelta > 0 && yDelta == 0)  //Right of the target
        {
            this.direction = Direction.LEFT;
        }
        else if(xDelta < 0 && yDelta == 0) //Left of the target
        {
            this.direction = Direction.RIGHT;
        }

        if(yDelta > 0 && xDelta > 0) //Up and right of target
        {
            this.direction = Direction.DOWN_LEFT;
        }
        else if(yDelta > 0 && xDelta < 0) //Up and left of target
        {
            this.direction = Direction.DOWN_RIGHT;
        }
        else if(yDelta < 0 && xDelta > 0) //Down and right of target
        {
            this.direction = Direction.UP_LEFT;
        }
        else if(yDelta < 0 && xDelta < 0) //Down and left of target
        {
            this.direction = Direction.UP_RIGHT;
        }
    }

    public float getXPos()
    {
        return this.xPos;
    }

    public float getYPos()
    {
        return this.yPos;
    }

    public void setMovementLocked(boolean lock)
    {
        this.movementLocked = lock;
    }

    private void checkBounds()
    {
        if(this.yPos > TOP_BOUND)
        {
            this.yPos = TOP_BOUND;
        }
        if(this.yPos < BOTTOM_BOUND)
        {
            this.yPos = BOTTOM_BOUND;
        }
        if(this.xPos < LEFT_BOUND)
        {
            this.xPos = LEFT_BOUND;
        }
        if(this.xPos > RIGHT_BOUND)
        {
            this.xPos = RIGHT_BOUND;
        }
    }

  public double getPlayer_health() { return player_health; }

  public void setPlayer_health(double newHealth) { player_health = newHealth; };

  public void addPlayer_health(double addedHealth ) { player_health += addedHealth; }


    public void subtractPlayer_health(double damage) { player_health -= damage; }




}
