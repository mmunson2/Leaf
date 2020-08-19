package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/********************************************************************************
 * HeliRescueBase Class
 *******************************************************************************/
public class Leaf_Base extends Game
{
    public final int SCREEN_WIDTH = 800;
    public final int SCREEN_HEIGHT = 600;

    public SpriteBatch batch;
    public BitmapFont font;
    public TextureAtlas atlas;

    /********************************************************************************
     * Create Override
     *******************************************************************************/
    @Override
    public void create()
    {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        batch = new SpriteBatch();
        font = new BitmapFont();
        atlas = new TextureAtlas("atlas.atlas");
        this.setScreen(new DemoRoom1(this));
    }

    /********************************************************************************
     * Render Override
     *******************************************************************************/
    @Override
    public void render()
    {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();

        batch.dispose();
        font.dispose();
        atlas.dispose();
    }
}
