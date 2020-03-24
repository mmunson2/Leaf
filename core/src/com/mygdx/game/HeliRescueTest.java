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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Random;


/********************************************************************************
 * HeliRescueTest Class
 *******************************************************************************/
public class HeliRescueTest implements Screen
{
    final HeliRescueBase game;

    Random random;

    private TextureRegion ground;
    private TextureRegion sky;
    private TextureRegion mountains;
    private TextureRegion mountain;
    private TextureRegion trees1;
    private TextureRegion trees2;


    private ShapeRenderer renderer;

    private OrthographicCamera camera;
    boolean attachedToHeli = true;
    long flipTime = TimeUtils.nanoTime();

    private Helicopter heli;

    private Array<Skyscraper> skyscrapers;

    public GLProfiler profiler;

    boolean debugFlag = false;

    /********************************************************************************
     * HeliRescueTest Constructor
     *******************************************************************************/
    public HeliRescueTest(final HeliRescueBase game)
    {
        this.game = game;

        this.random = new Random();

        heli = new Helicopter(game.atlas);

        ground = game.atlas.findRegion("Ground");
        sky = game.atlas.findRegion("sky");
        mountain = game.atlas.findRegion("mountain");
        mountains = game.atlas.findRegion("mountains");
        trees1 = game.atlas.findRegion("trees1");
        trees2 = game.atlas.findRegion("trees2");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.SCREEN_WIDTH,game.SCREEN_HEIGHT);

        game.batch = new SpriteBatch();

        game.font = new BitmapFont();

        profiler = new GLProfiler(Gdx.graphics);
        profiler.enable();


        skyscrapers = new Array<Skyscraper>();
        for(int i = 0; i < 100; i++)
        {
            int height = random.nextInt(3) + 1;

            skyscrapers.insert(i, new Skyscraper(i * 1000 + 1000, 0, height, game.atlas));
        }

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
        wrapRegion(mountain, cameraX - cameraX / 100, cameraY - cameraY / 100);
        wrapRegion(mountains, cameraX - cameraX / 50, cameraY - cameraY / 50);
        wrapRegion(trees1, cameraX - cameraX / 10, cameraY - cameraY / 10);
        wrapRegion(trees2, 0,  0);
        wrapRegion(ground, 0,-101);

        game.batch.draw(heli.getTexture(),heli.getX(),heli.getY());

        drawBuildings();
        game.font.draw(game.batch, heli.getInfo(), cameraX + 100, cameraY + 100);

        game.batch.end();

        heli.update();

        if(attachedToHeli)
            heli.updateCamera(camera);
        else
            CameraController.update(camera);


        if(Gdx.input.isKeyPressed(Input.Keys.C))
        {
            if(TimeUtils.nanoTime() - flipTime > 1000000000)
            {
                attachedToHeli = !attachedToHeli;
                flipTime = TimeUtils.nanoTime();
            }
        }

        checkBuildingCollisions();


        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.MAGENTA);
        //renderer.rect(heli.getFuselageCollisionBox().x, heli.getFuselageCollisionBox().y, heli.getFuselageCollisionBox().width, heli.getFuselageCollisionBox().height);
        //renderer.rect(heli.getRotorCollisionBox().x, heli.getRotorCollisionBox().y, heli.getRotorCollisionBox().width, heli.getRotorCollisionBox().height);
        //renderer.rect(heli.getWheelCollisionBox().x, heli.getWheelCollisionBox().y, heli.getWheelCollisionBox().width, heli.getWheelCollisionBox().height);

        //renderer.setColor(Color.RED);
        //renderer.rect(heli.getWheelCollisionBoxLeft().x, heli.getWheelCollisionBoxLeft().y, heli.getWheelCollisionBoxLeft().width, heli.getWheelCollisionBoxLeft().height);
        //renderer.setColor(Color.BLUE);
        //renderer.rect(heli.getWheelCollisionBoxRight().x, heli.getWheelCollisionBoxRight().y, heli.getWheelCollisionBoxRight().width, heli.getWheelCollisionBoxRight().height);
        renderer.end();


        profiler.reset();
    }

    public void drawBuildings()
    {
        int heightFactor;
        int yPos;

        for(Skyscraper skyscraper : skyscrapers)
        {
            heightFactor = skyscraper.getHeightFactor();
            yPos = skyscraper.getyPos();

            for(int i = 0; i < heightFactor; i++)
            {
                game.batch.draw(skyscraper.getTexture(), skyscraper.getXPos(), yPos);
                yPos += skyscraper.getHeight();
            }

        }
    }

    public void checkBuildingCollisions()
    {
        for(Skyscraper skyscraper : skyscrapers)
        {
            if(Math.abs(skyscraper.getXPos() - heli.getX()) < 1000)
            {
                heli.checkCollision(skyscraper.getCollisionBounds());
            }
        }
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
