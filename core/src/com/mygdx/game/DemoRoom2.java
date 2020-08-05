package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Dino.Dino;

import java.util.Random;


/********************************************************************************
 * Leaf Class
 *******************************************************************************/
public class DemoRoom2 implements Screen
{
    final Leaf_Base game;

    Random random;

    private TextureRegion background;

    private ShapeRenderer renderer;

    private OrthographicCamera camera;

    boolean cameraAttachedToPlayer = true;
    long flipTime = TimeUtils.nanoTime();

    Dino dino;
    String dialogue = "";

    private Player player;

    /********************************************************************************
     * Constructor
     *******************************************************************************/
    public DemoRoom2(final Leaf_Base game)
    {
        this.game = game;

        this.random = new Random();

        this.player = new Player(game.atlas);

        background = game.atlas.findRegion("battleback10");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.SCREEN_WIDTH,game.SCREEN_HEIGHT);

        game.batch = new SpriteBatch();
        game.font = new BitmapFont();

        renderer = new ShapeRenderer();

        dino = new Dino("Dialogue/Go Away.dino");
    }

    /********************************************************************************
     * render
     *******************************************************************************/
    @Override
    public void render(float delta)
    {
        float cameraX = camera.position.x - camera.viewportWidth / 2;
        float cameraY = camera.position.y - camera.viewportHeight / 2;

        Gdx.gl.glClearColor(0,0.5f,0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);


        //Begin Drawing
        game.batch.begin();
        game.batch.draw(background, 0,0, 1200, 700);

        game.batch.draw(player.getTexture(), player.getXPos(), player.getYPos());

        game.font.draw(game.batch, dialogue, cameraX + 100,cameraY + 80);

        game.batch.end();
        //End Drawing


        if(InputHandler.detachCameraToggle())
        {
            this.cameraAttachedToPlayer = !this.cameraAttachedToPlayer;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            dialogue = dino.getDialogue();
        }

        Direction playerDirection = InputHandler.getPlayerMovementDirection();
        this.player.move(playerDirection);

        if(cameraAttachedToPlayer)
        {
            CameraController.centerOn(player.getXPos(), player.getYPos(), camera);
        }
        else
        {
            Direction cameraDirection = InputHandler.getCameraMovementDirection();
            CameraController.move(cameraDirection, camera);
        }


        //Demonstrating how to quickly draw shapes
        //renderer.setProjectionMatrix(camera.combined);
        //renderer.begin(ShapeRenderer.ShapeType.Filled);
        //renderer.setColor(Color.MAGENTA);
        //renderer.rect(0,0, game.SCREEN_WIDTH, 50);

        //renderer.end();

    }



    /********************************************************************************
     * dispose
     *******************************************************************************/
    @Override
    public void dispose()
    {
        renderer.dispose();
    }


    public void show()
    {

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
