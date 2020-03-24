package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen implements Screen {

    final LearningGDX game;

    public SpriteBatch batch;
    public BitmapFont font;


    Texture dropImage;
    Texture bucketImage;
    Sound dropSound;
    Music rainMusic;

    OrthographicCamera camera;
    Rectangle bucket;
    Vector3 touchPos;

    Array<Rectangle> raindrops; //Doesn't produce as much garbage as ArrayList
    long lastDropTime;

    int acceleration = 0;
    int dropsGathered = 0;




    public GameScreen(final LearningGDX game) {

        this.game = game;

        //Load images, 64x64 pixels each
        dropImage = new Texture(Gdx.files.internal("droplet.png"));
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));

        //Load sound effects
        dropSound = Gdx.audio.newSound(Gdx.files.internal("raindrop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        rainMusic.setLooping(true);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        batch = new SpriteBatch();


        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2;
        bucket.y = 20;
        bucket.width = 64;
        bucket.height = 64;


        raindrops = new Array<Rectangle>();
        spawnRaindrop();

    }

    @Override
    public void render (float delta) {

        //Render Screen with dark blue background:
        Gdx.gl.glClearColor(0,0,0.2f,1); //Color values are between 0 and 1 hence 0.2f
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //Instructs OpenGL to clear the screen

        camera.update(); //Update camera matrix, good practice to do every frame

        batch.setProjectionMatrix(camera.combined); //Use the camera's coordinate system
        batch.begin(); //OpenGL wants as many images at once as possible so we use a batch

        //game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, 480);
        batch.draw(bucketImage,bucket.x,bucket.y);
        for(Rectangle raindrop : raindrops)
        {
            batch.draw(dropImage, raindrop.x, raindrop.y);
        }

        batch.end(); //Submit all drawing requests at once


        moveBucket();

        if(TimeUtils.nanoTime() - lastDropTime > 1000000000)
        {
            spawnRaindrop();
        }


        Iterator<Rectangle> iter = raindrops.iterator();

        while(iter.hasNext())
        {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();

            if(raindrop.y + 64 < 0)
            {
                iter.remove();
            }

            if(raindrop.overlaps(bucket))
            {
                dropSound.play();
                iter.remove();
            }
        }



    }


    //For reference, but makes the game too easy :)
		/*
		if(Gdx.input.isTouched()) //Check if currently pressed
		{
			touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0); //Get current mouse/touch position
			camera.unproject(touchPos); //Transform to camera coordinate system
			bucket.x = touchPos.x - 64/2;
		}
		*/
    public void moveBucket()
    {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            acceleration -= 15;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            acceleration += 15;
        }
        else
        {
            if(acceleration > 0)
            {
                acceleration -= 10;
            }
            if(acceleration < 0)
            {
                acceleration += 10;
            }
        }

        bucket.x += acceleration * Gdx.graphics.getDeltaTime();

        if(bucket.x < 0)
        {
            bucket.x = 800 - 64;
        }

        if(bucket.x > 800 - 64)
        {
            bucket.x = 0;
        }
    }


    public void spawnRaindrop()
    {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0,800 - 64);
        raindrop.y = 480;

        raindrop.width = 64;
        raindrop.height = 64;

        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }



    @Override
    public void dispose () {
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();


        batch.dispose();
        font.dispose();

    }

    public void show()
    {
        rainMusic.play();
    }

    public void resize(int x, int y)
    {

    }

    public void pause()
    {

    }

    public void resume()
    {

    }

    public void hide()
    {

    }

}