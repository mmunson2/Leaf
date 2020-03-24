package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;


/********************************************************************************
 * Civilian Class
 *
 * @author Matthew Munson
 * @version 0.5
 *
 * Date Created: 12/28/19
 * Last UpdateL 12/29/19
 *******************************************************************************/

public class Civilian
{
    Random random;

    //________________________________________________________________________________
    //Texture Variables
    //--------------------------------------------------------------------------------
    private TextureRegion standingLeft;
    private TextureRegion standingRight;

    private TextureRegion runningLeft1;
    private TextureRegion runningLeft2;
    private TextureRegion runningRight1;
    private TextureRegion runningRight2;

    private TextureRegion deadLeft;
    private TextureRegion deadRight;

    //________________________________________________________________________________
    //Collision Variables
    //--------------------------------------------------------------------------------
    private Rectangle civilianCollisionBox;

    //________________________________________________________________________________
    //Sprite Size Constants
    //--------------------------------------------------------------------------------
    public static final int CIVILIAN_WIDTH = 10;
    public static final int CIVILIAN_HEIGHT = 30;

    //________________________________________________________________________________
    //Crash Tolerance Constant
    //--------------------------------------------------------------------------------
    private static final int MAXIMUM_IMPACT_VELOCITY = 20;

    //________________________________________________________________________________
    //Death Flag
    //--------------------------------------------------------------------------------
    private boolean isDead;

    //________________________________________________________________________________
    //Facing Direction Variables
    //--------------------------------------------------------------------------------
    private boolean isFacingLeft;
    private long directionFlipTime;

    //________________________________________________________________________________
    //Physics Variables
    //--------------------------------------------------------------------------------
    private int spawnXPos;
    private int spawnYPos;

    private int xPos;
    private int yPos;

    private double velocityX;
    private double velocityY;

    private double accelerationX;
    private double accelerationY;

    /********************************************************************************
     * Civilian Constructor
     *******************************************************************************/
    public Civilian(TextureAtlas atlas, int spawnXPos, int spawnYPos)
    {
        random = new Random();

        this.standingLeft = atlas.findRegion("CivilianStandingLeft");

        this.standingRight = new TextureRegion(standingLeft);
        this.standingRight.flip(true,false);

        this.runningLeft1 = atlas.findRegion("CivilianRunningLeft1");
        this.runningLeft2 = atlas.findRegion("CivilianRunningLeft2");

        this.runningRight1 = new TextureRegion(runningLeft1);
        this.runningRight1.flip(true, false);

        this.runningRight2 = new TextureRegion(runningLeft2);
        this.runningRight2.flip(true, false);

        this.deadLeft = atlas.findRegion("CivilianDeadLeft");

        this.deadRight = new TextureRegion(deadLeft);
        this.deadRight.flip(true, false);

        this.spawnXPos = spawnXPos;
        this.spawnYPos = spawnYPos;

        reset();
    }

    /********************************************************************************
     * reset
     *******************************************************************************/
    private void reset()
    {
        this.xPos = spawnXPos;
        this.yPos = spawnYPos;

        this.velocityX = 0;
        this.velocityY = 0;

        this.accelerationX = 0;
        this.accelerationY = 0;

        this.civilianCollisionBox = new Rectangle();
        civilianCollisionBox.width = CIVILIAN_WIDTH;
        civilianCollisionBox.height = CIVILIAN_HEIGHT;

        updateCollision();

        //Face a random direction on spawn
        this.isFacingLeft = random.nextBoolean();
        this.directionFlipTime = 1;
    }

    /********************************************************************************
     * updateCollision
     *******************************************************************************/
    private void updateCollision()
    {
        civilianCollisionBox.x = this.xPos;
        civilianCollisionBox.y = this.yPos;
    }

    /********************************************************************************
     * checkCollision
     *
     * Only check if the civilian is on top of a skyscraper / has smacked into one
     * This needs to be managed by the level class, maybe not a good design decision?
     *******************************************************************************/
    public void checkCollision(Rectangle rect)
    {
        if(this.civilianCollisionBox.overlaps(rect))
        {
            if(Math.abs(velocityY) > MAXIMUM_IMPACT_VELOCITY)
            {
                isDead = true;
            }
            if(this.yPos > rect.y + (rect.height / 2))
            {
                this.yPos = (int) (rect.y + rect.height);
                this.velocityY = 0;
            }
            




        }
    }

    /********************************************************************************
     * checkHelicopterCollision
     *
     * Check if the helicopter has rammed the civilian.
     *
     * This needs to be managed by the level class, maybe not a good design decision?
     *******************************************************************************/
    public void checkHelicopterCollision(Helicopter heli)
    {
        double deltaVelocityX = Math.abs(heli.getVelocityX() - this.velocityX);
        double deltaVelocityY = Math.abs(heli.getVelocityY() - this.velocityY);

        double deltaMagnitude = Math.sqrt(Math.pow(deltaVelocityX, 2) + Math.pow(deltaVelocityY, 2));

        boolean aboveDeathLimit = deltaMagnitude > MAXIMUM_IMPACT_VELOCITY;

        //Todo: Shred the Civilian if they fall into the rotors
        if(heli.getRotorCollisionBox().overlaps(this.civilianCollisionBox))
        {
            this.isDead = true;
        }
        else if(heli.overlaps(this.civilianCollisionBox) && aboveDeathLimit)
        {
            this.isDead = true;
        }
        //Todo: Make the Helicopter push civilians around if below death speed
    }

    /********************************************************************************
     * checkRescueStatus
     *
     * Check if the helicopter has landed and if we should run toward it. Check if
     * the helicopter is close enough to board. Check if there is room on the helicopter.
     * Check if we should run away from the full helicopter.
     *
     * This needs to be managed by the level class, maybe not a good design decision?
     *******************************************************************************/
    public void checkRescueStatus(Helicopter heli)
    {
        //Check if the heli is on the same y plane, if not stop method

        //Check if the heli is within some amount of pixels
            //Check Heli for space
                //Board
                //Run away
        //Check if the heli is to the left or right
            //RunLeft()
            //RunRight()

    }

    /********************************************************************************
     * update
     *******************************************************************************/
    public void update()
    {






    }




}
