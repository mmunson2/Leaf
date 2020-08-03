package com.mygdx.game.Old;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.Old.GameScreen;
import com.mygdx.game.Old.LearningGDX;

public class MainMenuScreen implements Screen
{
    final LearningGDX game;

    OrthographicCamera camera;

    public MainMenuScreen(final LearningGDX game)
    {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0,0,0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        game.font.draw(game.batch, "Welcome to Drop!", 100, 150);
        game.font.draw(game.batch, "Click Anywhere to Begin", 100, 100);
        game.batch.end();

        if(Gdx.input.isTouched())
        {
            game.setScreen(new GameScreen(game));
            dispose();
        }
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

    public void dispose()
    {

    }




}
