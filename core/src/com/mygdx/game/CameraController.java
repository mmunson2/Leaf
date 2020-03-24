package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.TimeUtils;

public class CameraController
{

    public static void update(OrthographicCamera camera)
    {
        if(Gdx.input.isKeyPressed(Input.Keys.W))
        {
            camera.position.y += 10;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A))
        {
            camera.position.x -= 10;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S))
        {
            camera.position.y -= 10;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D))
        {
            camera.position.x += 10;
        }

        if(camera.position.y < 200)
            camera.position.y = 200;
    }
}
