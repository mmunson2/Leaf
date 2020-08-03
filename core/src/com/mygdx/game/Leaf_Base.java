package com.mygdx.game;

import com.badlogic.gdx.Game;
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
        batch = new SpriteBatch();
        font = new BitmapFont();
        atlas = new TextureAtlas("Atlas.atlas");
        this.setScreen(new Leaf(this));
    }

    /********************************************************************************
     * Render Override
     *******************************************************************************/
    @Override
    public void render()
    {
        super.render();
    }




}