package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;


public class InputHandler
{
    public static final int MOVE_PLAYER_UP = Input.Keys.UP;
    public static final int MOVE_PLAYER_DOWN = Input.Keys.DOWN;
    public static final int MOVE_PLAYER_LEFT = Input.Keys.LEFT;
    public static final int MOVE_PLAYER_RIGHT = Input.Keys.RIGHT;

    public static final int DETACH_CAMERA = Input.Keys.C;

    public static final int TALK_TO_NPC = Input.Keys.SPACE;

    public static final int MOVE_CAMERA_UP = Input.Keys.W;
    public static final int MOVE_CAMERA_DOWN = Input.Keys.S;
    public static final int MOVE_CAMERA_LEFT = Input.Keys.A;
    public static final int MOVE_CAMERA_RIGHT = Input.Keys.D;


    public static Direction getPlayerMovementDirection()
    {
        if(Gdx.input.isKeyPressed(MOVE_PLAYER_UP) && Gdx.input.isKeyPressed(MOVE_PLAYER_LEFT))
        {
            return Direction.UP_LEFT;
        }
        if(Gdx.input.isKeyPressed(MOVE_PLAYER_UP) && Gdx.input.isKeyPressed(MOVE_PLAYER_RIGHT))
        {
            return Direction.UP_RIGHT;
        }
        if(Gdx.input.isKeyPressed(MOVE_PLAYER_DOWN) && Gdx.input.isKeyPressed(MOVE_PLAYER_LEFT))
        {
            return Direction.DOWN_LEFT;
        }
        if(Gdx.input.isKeyPressed(MOVE_PLAYER_DOWN) && Gdx.input.isKeyPressed(MOVE_PLAYER_RIGHT))
        {
            return Direction.DOWN_RIGHT;
        }

        if(Gdx.input.isKeyPressed(MOVE_PLAYER_UP))
        {
            return Direction.UP;
        }
        if(Gdx.input.isKeyPressed(MOVE_PLAYER_DOWN))
        {
            return Direction.DOWN;
        }
        if(Gdx.input.isKeyPressed(MOVE_PLAYER_LEFT))
        {
            return Direction.LEFT;
        }
        if(Gdx.input.isKeyPressed(MOVE_PLAYER_RIGHT))
        {
            return Direction.RIGHT;
        }

        return null;
    }

    public static boolean detachCameraToggle()
    {
        return Gdx.input.isKeyJustPressed(DETACH_CAMERA);
    }

    public static boolean talkToNPC()
    {
        return Gdx.input.isKeyJustPressed(TALK_TO_NPC);
    }


    public static Direction getCameraMovementDirection()
    {
        if(Gdx.input.isKeyPressed(MOVE_CAMERA_UP) && Gdx.input.isKeyPressed(MOVE_CAMERA_LEFT))
        {
            return Direction.UP_LEFT;
        }
        if(Gdx.input.isKeyPressed(MOVE_CAMERA_UP) && Gdx.input.isKeyPressed(MOVE_CAMERA_RIGHT))
        {
            return Direction.UP_RIGHT;
        }
        if(Gdx.input.isKeyPressed(MOVE_CAMERA_DOWN) && Gdx.input.isKeyPressed(MOVE_CAMERA_LEFT))
        {
            return Direction.DOWN_LEFT;
        }
        if(Gdx.input.isKeyPressed(MOVE_CAMERA_DOWN) && Gdx.input.isKeyPressed(MOVE_CAMERA_RIGHT))
        {
            return Direction.DOWN_RIGHT;
        }

        if(Gdx.input.isKeyPressed(MOVE_CAMERA_UP))
        {
            return Direction.UP;
        }
        if(Gdx.input.isKeyPressed(MOVE_CAMERA_DOWN))
        {
            return Direction.DOWN;
        }
        if(Gdx.input.isKeyPressed(MOVE_CAMERA_LEFT))
        {
            return Direction.LEFT;
        }
        if(Gdx.input.isKeyPressed(MOVE_CAMERA_RIGHT))
        {
            return Direction.RIGHT;
        }

        return null;
    }


}
