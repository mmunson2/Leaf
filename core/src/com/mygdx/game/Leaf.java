package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Random;


/********************************************************************************
 * Leaf Class
 *******************************************************************************/
public class Leaf implements Screen
{
    final Leaf_Base game;

    Random random;

    private TextureRegion ground;
    private TextureRegion sky;

    private ShapeRenderer renderer;

    private OrthographicCamera camera;
    boolean attachedToPlayer = true;
    long flipTime = TimeUtils.nanoTime();

    private Player player;

    /********************************************************************************
     * Constructor
     *******************************************************************************/
    public Leaf(final Leaf_Base game)
    {
        this.game = game;

        this.random = new Random();

        player = new Player(game.atlas);

        ground = game.atlas.findRegion("Ground");
        sky = game.atlas.findRegion("sky");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.SCREEN_WIDTH,game.SCREEN_HEIGHT);

        game.batch = new SpriteBatch();
        game.font = new BitmapFont();

        renderer = new ShapeRenderer();
    }


    /********************************************************************************
     * wrapRegion
     *******************************************************************************/
    private void wrapRegion(TextureRegion region, float x, float y)
    {
        float cameraLeftBound = camera.position.x - camera.viewportWidth / 2;
        float cameraRightBound = cameraLeftBound + camera.viewportWidth;

        float textureLeftEdge = x;
        float textureRightEdge = x + region.getRegionWidth();

        while(cameraLeftBound < textureLeftEdge)
        {
            game.batch.draw(region, textureLeftEdge - region.getRegionWidth(), y);
            textureLeftEdge -= region.getRegionWidth();
        }

        while(cameraRightBound > textureRightEdge)
        {
            game.batch.draw(region, textureRightEdge, y);
            textureRightEdge += region.getRegionWidth();
        }

        game.batch.draw(region,x, y);
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


        game.batch.begin();
        game.batch.draw(sky,cameraX,cameraY);
        //wrapRegion(mountain, cameraX - cameraX / 100, cameraY - cameraY / 100);
        //wrapRegion(mountains, cameraX - cameraX / 50, cameraY - cameraY / 50);
        //wrapRegion(trees1, cameraX - cameraX / 10, cameraY - cameraY / 10);
        //wrapRegion(trees2, 0,  0);
        wrapRegion(ground, 0,-101);

        game.batch.draw(player.getTexture(), player.getX(), player.getY());

        game.font.draw(game.batch, "Dialogue Test", cameraX + 100,cameraY + 80);


        game.batch.end();


        player.update();

        //Do Camera Updates
        if(attachedToPlayer)
            player.updateCamera(camera);
        else
            CameraController.update(camera);

        //Check if camera should detach
        if(Gdx.input.isKeyPressed(Input.Keys.C))
        {
            if(TimeUtils.nanoTime() - flipTime > 1000000000)
            {
                attachedToPlayer = !attachedToPlayer;
                flipTime = TimeUtils.nanoTime();
            }
        }

        //Demonstrating how to quickly draw shapes
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.MAGENTA);
        //renderer.rect(0,0, game.SCREEN_WIDTH, 50);
        
        renderer.end();

    }



    /********************************************************************************
     * dispose
     *******************************************************************************/
    @Override
    public void dispose()
    {
        game.batch.dispose();
        game.atlas.dispose();
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
