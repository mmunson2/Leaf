package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Dino.Dino;
import org.lwjgl.Sys;

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
    Dino dino2;

    String dialogueNPC = "";
    String dialogueNPC2 = "";
    String prevDialogue = "";

    private float NPC1_INTERACTION;
    private float NPC2_INTERACTION;
    private float TIME_SINCE_LAST_INTERACTION;


    private PlayerAnimWalk player;

    private Static_Test_NPC npc;
    private Static_Test_NPC2 npc2;
    int npcWithFocus;

    private SpeechBox npcSpeechBox;
    private boolean player_off_edge;
    private int world_east_edge;

    SpriteBatch batch;
    Texture img;
    Texture playerIdleRight;
    Texture playerIdleLeft;
    Texture playerJumpRight;
    Texture playerJumpLeft;
    TextureRegion[] playerWalkLeftFrames;
    TextureRegion[] playerWalkRightFrames;

    Animation playerWalkLeft;
    Animation playerWalkRight;

    float elapsedTime;

    private String[] traits;
    private int npc1Distance;
    private int npc2Distance;

    private Music music;


    /********************************************************************************
     * Constructor
     *******************************************************************************/
    public DemoRoom3(final Leaf_Base game) {
        music = Gdx.audio.newMusic(Gdx.files.internal("core\\assets\\DemoRoom3\\Travel Music Loop.mp3"));
        music.setLooping(true);
        music.setVolume(.02f);
        music.play();

        this.game = game;

        this.random = new Random();

        this.npc = new Static_Test_NPC(game.atlas);
        this.npc2 = new Static_Test_NPC2();


        background = new TextureRegion(new Texture("core\\assets\\DemoRoom3\\background.png"));

        world_east_edge = background.getRegionWidth() - 50;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.SCREEN_WIDTH, game.SCREEN_HEIGHT);

        game.batch = new SpriteBatch();
        game.font = new BitmapFont();

        createPlayer();

        renderer = new ShapeRenderer();
        initializeDino();
        initializeTraits();
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
    }

    private void initializeTraits() {
        traits = new String[5];
        traits[0] = "PLAYER_HEALTH";
        traits[1] = "PLAYER_DISTANCE_FROM_EDGE";
        traits[2] = "NPC1_INTERACTION";
        traits[3] = "NPC2_INTERACTION";
        traits[4] = "TIME_SINCE_LAST_INTERACTION";
    }

    private void createPlayer() {

        // creating character
        int index = 0;

        img = new Texture("core\\assets\\DemoRoom3\\playerLeft.png");
        TextureRegion[][] tmpFrames = TextureRegion.split(img, 96, 96);

        playerWalkLeftFrames = new TextureRegion[7];

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 1; j++) {
                playerWalkLeftFrames[index++] = tmpFrames[j][i];
            }
        }

        img = new Texture("core\\assets\\DemoRoom3\\playerRight.png");
        tmpFrames = TextureRegion.split(img, 96, 96);

        playerWalkRightFrames = new TextureRegion[7];

        index = 0;

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 1; j++) {
                playerWalkRightFrames[index++] = tmpFrames[j][i];
            }
        }


        playerWalkLeft = new Animation(1f / 12f, playerWalkLeftFrames);
        playerWalkRight = new Animation(1f / 12f, playerWalkRightFrames);

        playerIdleLeft = new Texture("core\\assets\\DemoRoom3\\playerIdleLeft.png");
        playerIdleRight = new Texture("core\\assets\\DemoRoom3\\playerIdleRight.png");
        playerJumpLeft = new Texture("core\\assets\\DemoRoom3\\playerJumpLeft.png");
        playerJumpRight = new Texture("core\\assets\\DemoRoom3\\playerJumpRight.png");

        this.player = new PlayerAnimWalk(playerIdleLeft, playerIdleRight, playerJumpLeft, playerJumpRight, playerWalkLeft, playerWalkRight);
    }

    private void initializeDino() {
        dino = new Dino("core\\assets\\Dialogue\\PlayerHealthDialogue.dino");
        dino2 = new Dino("core\\assets\\Dialogue\\ELDERLYDATINGSIM_NO_APOS.dino");
        dino.setStaticVariable("PLAYER_NAME", "Melvin");
        dialogueNPC = dino.getDialogue();
        dialogueNPC2 = dino2.getDialogue();
    }

    /********************************************************************************
     * render
     *******************************************************************************/
    @Override
    public void render(float delta) {
        elapsedTime += Gdx.graphics.getDeltaTime(); //gets time between frames

        float cameraX = camera.position.x - camera.viewportWidth / 2;
        float cameraY = camera.position.y - camera.viewportHeight / 2;

        Gdx.gl.glClearColor(0, 0.5f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);


        calculatePlayerHealth();

        calculateTraits();

        calcultateNPCFocus();


        //Begin Drawing
        game.batch.begin();
        game.batch.draw(background, 0, 0, 2512, 2096);

        game.batch.draw(npc.getTexture(), npc.getXPos(), npc.getYPos());
        game.font.draw(game.batch, npc2.getName(), npc.getXPos() + 10, npc.getYPos() - 4);


        game.batch.draw(npc2.getTexture(), npc2.getXPos(), npc2.getYPos());
        game.font.draw(game.batch, npc.getName(), npc2.getXPos() + 10, npc2.getYPos() - 4);
        game.font.draw(game.batch, "Affection: " + NPC2_INTERACTION, npc2.getXPos()- 7, npc2.getYPos() - 19);


        drawDialogue(delta);

        game.batch.draw(player.getTexture(elapsedTime), player.getXPos(), player.getYPos());

        drawPlayerHealth();

        game.batch.end();
        //End Drawing

        Direction playerDirection = InputHandler.getPlayerMovementDirection();
        this.player.move(playerDirection);

        if (InputHandler.detachCameraToggle()) {
            this.cameraAttachedToPlayer = !this.cameraAttachedToPlayer;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (npcWithFocus == 1) {
                if (TIME_SINCE_LAST_INTERACTION <= 96) TIME_SINCE_LAST_INTERACTION += 5;
                if (NPC1_INTERACTION< 96) NPC1_INTERACTION += 5;
                if (NPC2_INTERACTION > 50) NPC2_INTERACTION -= 3;
            }
            else if (npcWithFocus == 2 && NPC2_INTERACTION < 96) {
                NPC2_INTERACTION += 5;
                if (TIME_SINCE_LAST_INTERACTION >= 10 ) TIME_SINCE_LAST_INTERACTION -= 10;
            }
            System.out.println(TIME_SINCE_LAST_INTERACTION);

            dialogueNPC = dino.getDialogue();
            dialogueNPC2 = dino2.getDialogue();
        }

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

    private void calcultateNPCFocus() {
        calculateNPCDistance();
        if (npc1Distance < npc2Distance) {
            npcWithFocus = 1;
        } else npcWithFocus = 2;
    }


    private void calculateNPCDistance() {
        npc1Distance = (int) Math.abs(Math.sqrt((Math.abs(npc.getXPos() - player.getXPos()) *
                (Math.abs(npc.getXPos() - player.getXPos()))) + ((Math.abs(npc.getYPos() - player.getYPos()) *
                (Math.abs(npc.getYPos() - player.getYPos()))))));
        npc2Distance = (int) Math.abs(Math.sqrt((Math.abs(npc2.getXPos() - player.getXPos()) *
                (Math.abs(npc2.getXPos() - player.getXPos()))) + ((Math.abs(npc2.getYPos() - player.getYPos()) *
                (Math.abs(npc2.getYPos() - player.getYPos()))))));
        if (npc1Distance < npc2Distance) {
            npcWithFocus = 1;
        } else npcWithFocus = 2;
    }


    private void drawPlayerHealth() {
        game.font.draw(game.batch, (int) player.getPlayer_health() + "HP", player.getXPos() + 29, player.getYPos() + 105);
        game.font.draw(game.batch, "Melvin", player.getXPos() + 29, player.getYPos() - 5);

    }

    private void drawDialogue(float currentTime) {
        calculateNPCDistance();
        String tempDialogue = "";

        if (npcWithFocus == 1) {
            this.npcSpeechBox = new SpeechBox(npc.getXPos() + 70, npc.getYPos() - 10);
            tempDialogue = dialogueNPC;

        } else {
            this.npcSpeechBox = new SpeechBox(npc2.getXPos() + 70, npc2.getYPos() - 10);
            tempDialogue = dialogueNPC2;
        }

        if (npc1Distance < 230 || player.getXPos() - npc.getXPos() > 0 || npc2Distance < 230) {
            if (tempDialogue.length() > 0) {
                game.batch.draw(npcSpeechBox.getTexture(), npcSpeechBox.getXPos(), npcSpeechBox.getYPos());

                prevDialogue = tempDialogue;

                if (tempDialogue.length() > 32) {


                    double middle = Math.floor(tempDialogue.length() / 2);
                    int before = tempDialogue.lastIndexOf(' ', (int) middle);
                    int after = tempDialogue.indexOf(' ', (int) (middle + 1));

                    if (middle - before < after - middle) {
                        middle = before;
                    } else {
                        middle = after;
                    }

                    String dialogue1 = tempDialogue.substring(0, (int) middle);
                    String dialogue2 = tempDialogue.substring((int) middle + 1);

                    game.font.draw(game.batch, dialogue1, (float) (npcSpeechBox.getXPos() + npcSpeechBox.getWidth() / 2 - (dialogue1.length() * 3.1)), (npcSpeechBox.getYPos() + 36));
                    game.font.draw(game.batch, dialogue2, (float) (npcSpeechBox.getXPos() + npcSpeechBox.getWidth() / 2 - (dialogue2.length() * 3.1)), (npcSpeechBox.getYPos() + 20));
                } else {
                    game.font.draw(game.batch, tempDialogue, (float) (npcSpeechBox.getXPos() + npcSpeechBox.getWidth() / 2 - (dialogueNPC.length() * 2.8)), (npcSpeechBox.getYPos() + 28));
                }
            }
        }
    }


    private void calculateTraits() {
        // set health and distance from edge
        dino.setTraitValue(traits[1], (((player.getXPos() - npc.getXPos() - 200) / ((world_east_edge - npc.getXPos() - 200))) * 100));
        if (player_off_edge) {
            dino.setTraitValue(traits[1], 100);
        }
        dino.setTraitValue(traits[0], player.getPlayer_health());

        dino2.setTraitValue(traits[0], player.getPlayer_health());
        dino2.setTraitValue(traits[2], NPC1_INTERACTION);
        dino2.setTraitValue(traits[3], NPC2_INTERACTION);
        dino2.setTraitValue(traits[4], TIME_SINCE_LAST_INTERACTION);
    }

    private void calculatePlayerHealth() {
        if (player.getXPos() > world_east_edge) {
            if (player.getPlayer_health() > 0) {
                player.subtractPlayer_health(1);
                player_off_edge = true;
            }
        } else if (player.getXPos() < world_east_edge) {
            if (player.getPlayer_health() < 100) {
                player.addPlayer_health(.01);
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

    enum TRAITS {
        PLAYER_HEALTH, PLAYER_DISTANCE_FROM_EDGE, NPC1_INTERACTION, NPC2_INTERACTION, TIME_SINCE_LAST_INTERACTION
    }

}

