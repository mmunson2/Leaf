package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.TimeUtils;

public class CameraController
{
    public static final int DEFAULT_CAMERA_SPEED = 10;

    public static void move(Direction direction, OrthographicCamera camera)
    {
        move(direction, camera, DEFAULT_CAMERA_SPEED);
    }

    public static void move(Direction direction, OrthographicCamera camera, int speed)
    {
        if(direction == null)
        {
            return;
        }

        switch(direction)
        {
            case UP:
                camera.position.y += speed;
                break;
            case DOWN:
                camera.position.y -= speed;
                break;
            case LEFT:
                camera.position.x -= speed;
                break;
            case RIGHT:
                camera.position.x += speed;
                break;
            case UP_LEFT:
                camera.position.y += speed;
                camera.position.x -= speed;
                break;
            case UP_RIGHT:
                camera.position.y += speed;
                camera.position.x += speed;
                break;
            case DOWN_LEFT:
                camera.position.y -= speed;
                camera.position.x -= speed;
                break;
            case DOWN_RIGHT:
                camera.position.y -= speed;
                camera.position.x += speed;
        }
    }

    public static void centerOn(float xPos, float yPos, OrthographicCamera camera)
    {
        camera.position.x = xPos;
        camera.position.y = yPos;
    }
}
