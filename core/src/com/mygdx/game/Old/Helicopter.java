package com.mygdx.game.Old;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;


/********************************************************************************
 * Helicopter Class
 *
 * @author Matthew Munson
 * @version 0.5
 *
 * Date Created: 12/19/19
 * Last UpdateL 12/29/19
 *
 *******************************************************************************/
public class Helicopter
{
    //________________________________________________________________________________
    //Texture Variables
    //--------------------------------------------------------------------------------
    private TextureRegion engineOffLeftTexture;
    private TextureRegion engineOffRightTexture;
    private TextureRegion engineOnLeftTexture;
    private TextureRegion engineOnRightTexture;
    private TextureRegion crashRightTexture;
    private TextureRegion crashLeftTexture;

    //________________________________________________________________________________
    //Collision Variables
    //--------------------------------------------------------------------------------
    private Rectangle rotorCollisionBox;
    private Rectangle fuselageCollisionBox;
    private Rectangle wheelCollisionBoxLeft;
    private Rectangle wheelCollisionBoxRight;

    //________________________________________________________________________________
    //Sprite Size Constants
    //--------------------------------------------------------------------------------
    public static final int HELICOPTER_WIDTH = 300;
    public static final int HELICOPTER_HEIGHT = 96;

    //________________________________________________________________________________
    //Camera Constants
    //--------------------------------------------------------------------------------
    private static final double HORIZONTAL_TRANSITION_TIME = 0.3;
    private static final double VERTICAL_TRANSITION_TIME = 0.1;

    private static final double VERTICAL_TRANSITION_ALTITUDE = 2800;
    private long altitudeTransitionTime;

    //________________________________________________________________________________
    //Crash Tolerance Constant
    //--------------------------------------------------------------------------------
    private static final int MAXIMUM_IMPACT_VELOCITY = 20;

    //________________________________________________________________________________
    //Engine Control Variables
    //--------------------------------------------------------------------------------
    private boolean engineOn;
    private long engineToggleTime;

    //________________________________________________________________________________
    //Crash Flag
    //--------------------------------------------------------------------------------
    private boolean isCrashed;

    //________________________________________________________________________________
    //Facing Direction Variables
    //--------------------------------------------------------------------------------
    private boolean isFacingLeft;
    private long directionFlipTime;

    //________________________________________________________________________________
    //Physics Variables
    //--------------------------------------------------------------------------------
    private int xPos;
    private int yPos;

    //________________________________________________________________________________
    //Debug Variables
    //--------------------------------------------------------------------------------
    private String info;


    /********************************************************************************
     * Helicopter Constructor
     *******************************************************************************/
    public Helicopter(TextureAtlas atlas)
    {
        //Texture initialization
        engineOffRightTexture = atlas.findRegion("ChinookRight");

        engineOffLeftTexture = new TextureRegion(engineOffRightTexture);
        engineOffLeftTexture.flip(true, false);

        engineOnRightTexture = atlas.findRegion("ChinookOnRight");

        engineOnLeftTexture = new TextureRegion(engineOnRightTexture);
        engineOnLeftTexture.flip(true,false);

        crashRightTexture = atlas.findRegion("Crash");

        crashLeftTexture = new TextureRegion(crashRightTexture);
        crashLeftTexture.flip(true,false);

        reset();
    }

    /********************************************************************************
     * reset()
     *******************************************************************************/
    private void reset()
    {
        this.isCrashed = false;
        this.engineOn = false;
        this.isFacingLeft = false;

        this.altitudeTransitionTime = 1;
        this.directionFlipTime = 1;

        //Collision Box initialization
        wheelCollisionBoxLeft = new Rectangle();
        wheelCollisionBoxLeft.width = HELICOPTER_WIDTH / 4;
        wheelCollisionBoxLeft.height = 20;

        wheelCollisionBoxRight = new Rectangle();
        wheelCollisionBoxRight.width = HELICOPTER_WIDTH / 4;
        wheelCollisionBoxRight.height = 20;

        assert(wheelCollisionBoxLeft.height == wheelCollisionBoxRight.height);

        fuselageCollisionBox = new Rectangle();
        fuselageCollisionBox.width = 192;
        fuselageCollisionBox.height = 50 - wheelCollisionBoxLeft.height;


        rotorCollisionBox = new Rectangle();
        rotorCollisionBox.width = HELICOPTER_WIDTH;
        rotorCollisionBox.height = HELICOPTER_HEIGHT - fuselageCollisionBox.height - wheelCollisionBoxLeft.height;

        //Set CollisionBox Positions
        updateCollisionBox();

        //Starting positions
        xPos = 100;
        yPos = 300;

        info = "initialized";
    }


    /********************************************************************************
     * updateCollisionBox
     *
     * Updates the position of the collision Rectangles to match the Helicopter.
     * Called in the constructor for initial position set and then once every
     * frame within update.
     *******************************************************************************/
    private void updateCollisionBox()
    {
        wheelCollisionBoxLeft.x = xPos + 75;
        wheelCollisionBoxLeft.y = yPos;

        wheelCollisionBoxRight.x = wheelCollisionBoxLeft.x + wheelCollisionBoxLeft.width;
        wheelCollisionBoxRight.y = yPos;

        fuselageCollisionBox.x = xPos + 62;
        fuselageCollisionBox.y = yPos + wheelCollisionBoxLeft.height;

        rotorCollisionBox.x = xPos;
        rotorCollisionBox.y = yPos + fuselageCollisionBox.height;
    }


    /********************************************************************************
     * Overlaps
     *
     * An "overload" of the standard Rectangle overlaps method. Checks if any of
     * the Helicopter's collision rectangles are overlapping with a provided
     * rectangle. Intended for use as a simple and efficient method of checking
     * for collision.
     *
     * Call this method every frame from the level class for accurate collision
     * tracking.
     *******************************************************************************/
    public boolean overlaps(Rectangle rect)
    {
        return fuselageCollisionBox.overlaps(rect) || rotorCollisionBox.overlaps(rect) || wheelCollisionBoxLeft.overlaps(rect) || wheelCollisionBoxRight.overlaps(rect);
    }

    /********************************************************************************
     * checkCollision
     *
     * A more complicated collision call that allows the Helicopter to land on
     * a surface if only its wheels are touching.
     *
     * First checks if the wheel collision box overlaps but the fuselage and
     * rotor don't. In this case it checks whether the helicopter is descending
     * too fast and will crash it if so. If not, position and velocity are set
     * so the helicopter will stay on top of the collision object.
     *
     * KNOWN ISSUE: At high vertical speeds, the fuselage collision box will
     * overlap the collision rectangle causing an instant crash. This is not
     * an issue at the current MAXIMUM_IMPACT_VELOCITY, but could become
     * a problem if this constant is increased.
     *
     * Call this method every frame from the level class for accurate collision
     * tracking.
     *******************************************************************************/
    public void checkCollision(Rectangle rect)
    {
        if(( wheelCollisionBoxLeft.overlaps(rect) || wheelCollisionBoxRight.overlaps(rect) ) && !fuselageCollisionBox.overlaps(rect) && !rotorCollisionBox.overlaps(rect))
        {

        }
        else if(this.overlaps(rect) && xPos + (fuselageCollisionBox.width / 2) > rect.x && xPos + (fuselageCollisionBox.width / 2) < rect.x + rect.width)
        {

        }
        else if(this.overlaps(rect))
        {

        }
    }


    /********************************************************************************
     * getTexture
     *******************************************************************************/
    public TextureRegion getTexture()
    {
        if(isCrashed)
        {
            if (isFacingLeft)
                return crashLeftTexture;
            else
                return crashRightTexture;
        }
        else if (engineOn)
        {
            if(isFacingLeft)
                return engineOnLeftTexture;
            else
                return engineOnRightTexture;
        }
        else
        {
            if(isFacingLeft)
                return engineOffLeftTexture;
            else
                return engineOffRightTexture;
        }
    }

    /********************************************************************************
     * update
     *******************************************************************************/
    public void update() {

        int originalYPos = yPos;

        handleDebugBreak();

        handleReset();

        handleHorizontalInput();

        handleVerticalInput();

        //applyEnginePower();

        //Collisions
        if(yPos < 0)
        {
            yPos = 0;
        }

        updateCollisionBox();
    }

    /********************************************************************************
     * handleDebugBreak
     *******************************************************************************/
    private void handleDebugBreak()
    {
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER))
        {
            System.out.println("______________________________________________________________________");
            System.out.println("Position X: " + xPos);
            System.out.println();
            System.out.println("Position Y: " + yPos);
            System.out.println();
            System.out.println("isCrashed: " + isCrashed);
            System.out.println("engineOn: " + engineOn);
            System.out.println("______________________________________________________________________");
            System.out.println();

        }
    }

    private void handleReset()
    {
        if(Gdx.input.isKeyPressed(Input.Keys.PERIOD))
        {
            reset();
        }
    }

    /********************************************************************************
     * handleHorizontalInput
     *******************************************************************************/
    private void handleHorizontalInput()
    {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            this.xPos += 1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            this.xPos -= 1;
        }
    }

    /********************************************************************************
     * handleVerticalInput
     *******************************************************************************/
    private void handleVerticalInput()
    {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.yPos += 1;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
        {
            this.yPos -= 1;
        }
    }

    /********************************************************************************
     * getInfo
     *******************************************************************************/
    public String getInfo()
    {
        double secondsElapsed = (TimeUtils.millis() - directionFlipTime) / 1000.0;
        double fraction = secondsElapsed / 3;

        return "";
    }

    /********************************************************************************
     * updateCamera
     *
     * Likely in need of some simplification. This method locks the camera to
     * the helicopter. When the direction of the helicopter changes, the camera
     * shifts to the opposite side. The transitionTime variable is used to modify
     * the speed that the camera shifts.
     *
     *******************************************************************************/
    public void updateCamera(OrthographicCamera camera)
    {
        double horizontalFlipSecondsElapsed = (TimeUtils.millis() - directionFlipTime) / 1000.0;
        double verticalCrossingSecondsElapsed = (TimeUtils.millis() - altitudeTransitionTime) / 1000.0;

        camera.position.x = this.xPos;
        camera.position.y = this.yPos;

        if(isFacingLeft)
        {
            if(horizontalFlipSecondsElapsed < HORIZONTAL_TRANSITION_TIME)
            {
                double fraction = horizontalFlipSecondsElapsed / HORIZONTAL_TRANSITION_TIME;

                //Original Camera Position
                camera.position.x += (camera.viewportHeight / 2);

                //New Camera Position multiplied by fraction
                camera.position.x -= camera.viewportHeight * fraction;
                camera.position.x += HELICOPTER_WIDTH * fraction;
            }
            else
            {
                camera.position.x -= (camera.viewportHeight / 2);
                camera.position.x += HELICOPTER_WIDTH;
            }
        }
        else // Facing Right
        {
            if(horizontalFlipSecondsElapsed < HORIZONTAL_TRANSITION_TIME)
            {
                double fraction = horizontalFlipSecondsElapsed / HORIZONTAL_TRANSITION_TIME;

                //Old Camera Position
                camera.position.x -= (camera.viewportHeight / 2);
                camera.position.x += HELICOPTER_WIDTH;

                //New Camera Position
                camera.position.x += (camera.viewportHeight) * fraction;
                camera.position.x -= HELICOPTER_WIDTH * fraction;
            }
            else
            {
                camera.position.x += (camera.viewportHeight / 2);
            }
        }


        //Vertical positioning
        camera.position.y += camera.viewportHeight / 2;
        camera.position.y -= 100;

        if(verticalCrossingSecondsElapsed < VERTICAL_TRANSITION_TIME)
        {
            double fraction = verticalCrossingSecondsElapsed / VERTICAL_TRANSITION_TIME;

            if(yPos < VERTICAL_TRANSITION_ALTITUDE)
            {
                camera.position.y -= 300 * fraction;
            }
            if(yPos > VERTICAL_TRANSITION_ALTITUDE)
            {
                camera.position.y -= 300;
                camera.position.y += 300 * fraction;
            }
        }
        else if(yPos < VERTICAL_TRANSITION_ALTITUDE)
        {
            camera.position.y -= 300;
        }

        if(camera.position.y <= camera.viewportHeight / 2 - 100)
            camera.position.y = camera.viewportHeight / 2 - 100;
    }


    //||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    //||||||        ||||      ||||      ||||      ||||      ||||      ||||      ||||||
    //||||||  ||||||||||  ||||||||||  ||||||||  ||||||  ||||||||  ||  ||||  ||||||||||
    //||||||  ||    ||||    ||||||||  ||||||||  ||||||    ||||||    ||||||      ||||||
    //||||||  ||||  ||||  ||||||||||  ||||||||  ||||||  ||||||||  ||  ||||||||  ||||||
    //||||||        ||||      ||||||  ||||||||  ||||||      ||||  ||  ||||      ||||||
    //||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||


    /********************************************************************************
     * getX
     *******************************************************************************/
    public int getX()
    {
        return xPos;
    }

    /********************************************************************************
     * getY
     *******************************************************************************/
    public int getY()
    {
        return yPos;
    }

    /********************************************************************************
     * getWheelCollisionBox
     *******************************************************************************/
    public Rectangle getWheelCollisionBox()
    {
        return new Rectangle(wheelCollisionBoxLeft.x, wheelCollisionBoxLeft.y,
                wheelCollisionBoxLeft.width + wheelCollisionBoxRight.width, wheelCollisionBoxLeft.height);
    }

    /********************************************************************************
     * getWheelCollisionBox
     *******************************************************************************/
    public Rectangle getWheelCollisionBoxLeft()
    {
        return wheelCollisionBoxLeft;
    }

    /********************************************************************************
     * getWheelCollisionBox
     *******************************************************************************/
    public Rectangle getWheelCollisionBoxRight()
    {
        return wheelCollisionBoxRight;
    }

    /********************************************************************************
     * getRotorCollisionBox
     *******************************************************************************/
    public Rectangle getRotorCollisionBox()
    {
        return rotorCollisionBox;
    }

    /********************************************************************************
     * getFuselageCollisionBox
     *******************************************************************************/
    public Rectangle getFuselageCollisionBox()
    {
        return fuselageCollisionBox;
    }

}
