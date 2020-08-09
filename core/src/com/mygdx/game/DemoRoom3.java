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
public class DemoRoom3 implements Screen {
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

    private Static_Test_NPC npc;

    private SpeechBox npcSpeechBox;
    private boolean player_off_edge;
    private int world_east_edge;

    /********************************************************************************
     * Constructor
     *******************************************************************************/
    public DemoRoom3(final Leaf_Base game) {
        this.game = game;

        this.random = new Random();

        this.player = new Player(game.atlas);

        this.npc = new Static_Test_NPC(game.atlas);

        this.npcSpeechBox = new SpeechBox(npc.getXPos() + 100, npc.getYPos() - 20);

        background = game.atlas.findRegion("battleback10");

        world_east_edge = 140 + background.getRegionWidth();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.SCREEN_WIDTH, game.SCREEN_HEIGHT);

        game.batch = new SpriteBatch();
        game.font = new BitmapFont();

        renderer = new ShapeRenderer();

        initializeDino();
    }

    private void initializeDino() {
        dino = new Dino("core\\assets\\Dialogue\\PlayerHealthDialogue.dino");
        dino.setStaticVariable("PLAYER_NAME", "Melvin Song");
    }

    /********************************************************************************
     * render
     *******************************************************************************/
    @Override
    public void render(float delta) {
        float cameraX = camera.position.x - camera.viewportWidth / 2;
        float cameraY = camera.position.y - camera.viewportHeight / 2;

        Gdx.gl.glClearColor(0, 0.5f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);


        calculatePlayerHealth();

        calculateTraits();


        //Begin Drawing
        game.batch.begin();
        game.batch.draw(background, 0, 0, 1200, 700);

        game.batch.draw(npc.getTexture(), npc.getXPos(), npc.getYPos());

        game.batch.draw(npcSpeechBox.getTexture(), npcSpeechBox.getXPos(), npcSpeechBox.getYPos());
        game.font.draw(game.batch, dialogue, (float) (npcSpeechBox.getXPos() + npcSpeechBox.getWidth() / 2 - (dialogue.length() * 2.9)), (npcSpeechBox.getYPos() + 20));


        game.batch.draw(player.getTexture(), player.getXPos(), player.getYPos());

        game.font.draw(game.batch, player.getPlayer_health() + "", player.getXPos() + 47, player.getYPos() + 135);

        game.batch.end();
        //End Drawing


        if (InputHandler.detachCameraToggle()) {
            this.cameraAttachedToPlayer = !this.cameraAttachedToPlayer;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            dialogue = dino.getDialogue();
        }

        Direction playerDirection = InputHandler.getPlayerMovementDirection();
        this.player.move(playerDirection);

        if (cameraAttachedToPlayer) {
            CameraController.centerOn(player.getXPos(), player.getYPos(), camera);
        } else {
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

    private void calculateTraits() {
        dino.setTraitValue("PLAYER_DISTANCE_FROM_EDGE", (((player.getXPos() - npc.getXPos() - 200) / ((world_east_edge - npc.getXPos() - 200))) * 100));
        if (player_off_edge) {
            dino.setTraitValue("PLAYER_DISTANCE_FROM_EDGE", 100);
        }
        dino.setTraitValue("PLAYER_HEALTH", player.getPlayer_health());

    }

    private void calculatePlayerHealth() {
        if (player.getXPos() > world_east_edge) {
            if (player.getPlayer_health() > 0) {
                player.subtractPlayer_health(1);
                player_off_edge = true;
            }
        } else if (player.getXPos() < world_east_edge) {
            if (player.getPlayer_health() < 100) {
                player.addPlayer_health(1);
                player_off_edge = false;
            }
        }
    }


    /********************************************************************************
     * dispose
     *******************************************************************************/
    @Override
    public void dispose() {
        renderer.dispose();
    }


    public void show() {

    }

    public void resize(int x, int y) {

    }

    public void pause() {

    }

    public void resume() {

    }

    public void hide() {

    }

}
