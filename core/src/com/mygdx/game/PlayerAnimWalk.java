package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import javax.swing.plaf.nimbus.State;

import static com.mygdx.game.Direction.*;


public class PlayerAnimWalk {
    public static final int PLAYER_SPEED = 5;

    private float xPos;
    private float yPos;

    public Direction direction;
    public Direction prevDir;

    private final Texture playerIdleRight;
    private final Texture playerIdleLeft;
    private final Texture playerJumpLeft;
    private final Texture playerJumpRight;
    private final Animation playerWalkLeft;
    private final Animation playerWalkRight;

    private TextureRegion prevTexture;

    private static double player_health;

    public PlayerAnimWalk(Texture playerIdleLeft, Texture playerIdleRight, Texture playerJumpLeft, Texture playerJumpRight, Animation playerWalkLeft, Animation playerWalkRight) {
        this.playerIdleRight = playerIdleRight;
        this.playerIdleLeft = playerIdleLeft;
        this.playerJumpLeft = playerJumpLeft;
        this.playerJumpRight = playerJumpRight;
        this.playerWalkLeft = playerWalkLeft;
        this.playerWalkRight = playerWalkRight;

        this.xPos = 250;
        this.yPos = 200;

        player_health = 100;

        direction = RIGHT;
    }

    public void move(Direction direction) {
        if (direction == null) {
            this.direction = null;
            return;
        }

        this.direction = direction;

        switch (direction) {
            case UP:
                this.yPos += PLAYER_SPEED;
                break;
            case DOWN:
                this.yPos -= PLAYER_SPEED;
                break;
            case LEFT:
                prevDir = LEFT;
                this.xPos -= PLAYER_SPEED;
                break;
            case RIGHT:
                prevDir = RIGHT;
                this.xPos += PLAYER_SPEED;
                break;
            case UP_LEFT:
                prevDir = LEFT;
                this.yPos += PLAYER_SPEED;
                this.xPos -= PLAYER_SPEED;
                break;
            case UP_RIGHT:
                prevDir = RIGHT;
                this.yPos += PLAYER_SPEED;
                this.xPos += PLAYER_SPEED;
                break;
            case DOWN_LEFT:
                prevDir = LEFT;
                this.yPos -= PLAYER_SPEED;
                this.xPos -= PLAYER_SPEED;
                break;
            case DOWN_RIGHT:
                prevDir = RIGHT;
                this.yPos -= PLAYER_SPEED;
                this.xPos += PLAYER_SPEED;
        }
    }


    public void update() {

    }

    public float getXPos() {
        return this.xPos;
    }

    public float getYPos() {
        return this.yPos;
    }

    public double getPlayer_health() {
        return player_health;
    }

    public void setPlayer_health(double newHealth) {
        player_health = newHealth;
    }

    ;

    public void addPlayer_health(double addedHealth) {
        player_health += addedHealth;
    }

    public void subtractPlayer_health(double damage) {
        player_health -= damage;
    }

    public TextureRegion getTexture(float elapsedTime) {
        if (direction == null) {
            if (prevDir == LEFT) {
                prevTexture = new TextureRegion(playerIdleLeft);
                return new TextureRegion(playerIdleLeft);
            } else {
                prevTexture = new TextureRegion(playerIdleRight);
                return new TextureRegion(playerIdleRight);

            }
        }
        switch (direction) {
            case UP:
                if (prevDir == LEFT || prevDir == DOWN_LEFT || prevDir == UP_LEFT) {
                    prevTexture = new TextureRegion(playerIdleLeft);
                    return new TextureRegion(playerIdleLeft);
                } else {
                    prevTexture = (TextureRegion) playerWalkRight.getKeyFrame(elapsedTime, true);
                    return (TextureRegion) playerWalkRight.getKeyFrame(elapsedTime, true);
                }
            case DOWN:
                if (prevDir == LEFT) {
                    prevTexture = (TextureRegion) playerWalkLeft.getKeyFrame(elapsedTime, true);
                    return (TextureRegion) playerWalkLeft.getKeyFrame(elapsedTime, true);
                } else {
                    prevTexture = (TextureRegion) playerWalkRight.getKeyFrame(elapsedTime, true);
                    return (TextureRegion) playerWalkRight.getKeyFrame(elapsedTime, true);
                }
            case UP_RIGHT:
            case DOWN_RIGHT:
            case RIGHT: {
                prevTexture = (TextureRegion) playerWalkRight.getKeyFrame(elapsedTime, true);
                return (TextureRegion) playerWalkRight.getKeyFrame(elapsedTime, true);
            }
            case LEFT:
            case DOWN_LEFT:
            case UP_LEFT: {
                prevTexture = (TextureRegion) playerWalkLeft.getKeyFrame(elapsedTime, true);
                return (TextureRegion) playerWalkLeft.getKeyFrame(elapsedTime, true);
            }
        }

        return null;
    }

}
