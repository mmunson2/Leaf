package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.lwjgl.Sys;

import java.util.Random;

public class NPC 
{
    public static final int NPC_SPEED = 1;

    public static final int MAX_MOVE_TIME = 20; //tenths of a second
    public static final int MIN_MOVE_TIME = 10; //tenths of a second

    public static final int MAX_SIT_TIME = 50; //tenths of a second
    public static final int MIN_SIT_TIME = 10; //tenths of a second

    private float xPos;
    private float yPos;

    private Direction direction;

    private final TextureRegion NPC_up;
    private final TextureRegion NPC_down;
    private final TextureRegion NPC_left;
    private final TextureRegion NPC_right;
    private final TextureRegion NPC_up_left;
    private final TextureRegion NPC_up_right;
    private final TextureRegion NPC_down_left;
    private final TextureRegion NPC_down_right;

    private static final String NPC_UP_NAME = "NPC_up";
    private static final String NPC_DOWN_NAME = "NPC_down";
    private static final String NPC_LEFT_NAME = "NPC_left";
    private static final String NPC_RIGHT_NAME = "NPC_right";
    private static final String NPC_UP_LEFT_NAME = "NPC_up_left";
    private static final String NPC_UP_RIGHT_NAME = "NPC_up_right";
    private static final String NPC_DOWN_LEFT_NAME = "NPC_down_left";
    private static final String NPC_DOWN_RIGHT_NAME = "NPC_down_right";

    private static final float TOP_BOUND = 400;
    private static final float BOTTOM_BOUND = 50;
    private static final float LEFT_BOUND = 0;
    private static final float RIGHT_BOUND = 1100;

    private static final float DIRECTION_MARGIN = 10;

    private boolean isMoving = false;
    private long moveTimer = 0;
    private long sitTimer = 0;

    private boolean movementLocked = false;

    private Random random = new Random();

    public NPC(TextureAtlas atlas)
    {
        this.NPC_up = atlas.findRegion(NPC_UP_NAME);
        this.NPC_down = atlas.findRegion(NPC_DOWN_NAME);
        this.NPC_left = atlas.findRegion(NPC_LEFT_NAME);
        this.NPC_right = atlas.findRegion(NPC_RIGHT_NAME);
        this.NPC_up_left = atlas.findRegion(NPC_UP_LEFT_NAME);
        this.NPC_up_right = atlas.findRegion(NPC_UP_RIGHT_NAME);
        this.NPC_down_left = atlas.findRegion(NPC_DOWN_LEFT_NAME);
        this.NPC_down_right = atlas.findRegion(NPC_DOWN_RIGHT_NAME);

        this.direction = Direction.RIGHT;
        this.xPos = 300;
        this.yPos = 300;
    }

    public TextureRegion getTexture()
    {
        switch(direction)
        {
            case UP: return NPC_up;
            case DOWN: return NPC_down;
            case LEFT: return NPC_left;
            case RIGHT: return NPC_right;
            case UP_LEFT: return NPC_up_left;
            case UP_RIGHT: return NPC_up_right;
            case DOWN_LEFT: return NPC_down_left;
            case DOWN_RIGHT: return NPC_down_right;
        }

        return null;
    }

    public void aiMove()
    {
        if(movementLocked)
        {
            return;
        }

        if(isMoving)
        {
            this.move(direction);

            if(System.nanoTime() > this.moveTimer) //Switch to sit mode
            {
                this.isMoving = false;

                int sitTime = random.nextInt(MAX_SIT_TIME);
                sitTime += MIN_SIT_TIME;

                //Multiply by 10E8 because sit time is in tenths of second
                this.sitTimer = System.nanoTime() + (sitTime * 100000000);
            }
        }
        else
        {
            if(System.nanoTime() > this.sitTimer) //Switch to move mode
            {
                this.isMoving = true;

                int newDirectionInt = random.nextInt(Direction.values().length);
                this.direction = Direction.values()[newDirectionInt];

                int moveTime = random.nextInt(MAX_MOVE_TIME);
                moveTime += MIN_MOVE_TIME;

                //Multiply by 10E8 because move time is in tenths of second
                this.moveTimer = System.nanoTime() + (moveTime * 100000000);
            }
        }
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
                this.yPos += NPC_SPEED;
                break;
            case DOWN:
                this.yPos -= NPC_SPEED;
                break;
            case LEFT:
                this.xPos -= NPC_SPEED;
                break;
            case RIGHT:
                this.xPos += NPC_SPEED;
                break;
            case UP_LEFT:
                this.yPos += NPC_SPEED;
                this.xPos -= NPC_SPEED;
                break;
            case UP_RIGHT:
                this.yPos += NPC_SPEED;
                this.xPos += NPC_SPEED;
                break;
            case DOWN_LEFT:
                this.yPos -= NPC_SPEED;
                this.xPos -= NPC_SPEED;
                break;
            case DOWN_RIGHT:
                this.yPos -= NPC_SPEED;
                this.xPos += NPC_SPEED;
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

}
