package com.mygdx.game.GameObject;

import com.badlogic.gdx.math.Rectangle;

public class CollisionBox
{
    private Rectangle boundingRectangle;


    public CollisionBox(int posX, int posY, int width, int height)
    {

    }

    public void setPosX(int posX)
    {

    }

    public void setPosY(int posY)
    {

    }

    public boolean collides(CollisionBox collisionBox)
    {
        return this.boundingRectangle.overlaps(collisionBox.boundingRectangle);
    }


    /**
     *
     * Case 1: collisionBox is below this
     * Case 2: collisionBox's top line is inside this
     *
     *
     */
    public boolean collidesBottom(CollisionBox collisionBox)
    {
        float collisionBoxTop = collisionBox.boundingRectangle.y + collisionBox.boundingRectangle.height;
        float thisBoxBottom = this.boundingRectangle.y;
        float thisBoxTop = this.boundingRectangle.y + this.boundingRectangle.height;



        return false;
    }

}
