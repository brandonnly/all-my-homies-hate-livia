package models;

import game.Game;

import java.awt.*;

public abstract class Entity
{
    /////////////
    //VARIABLES//
    /////////////

    protected Image image;
    protected Image[] sprites;
    protected int positionX;
    protected int positionY;
    protected int directionX;
    protected int directionY;
    protected int width;
    protected int height;

    //An empty constructor.
    public Entity()
    {
    }

    //Initializes/resets the entity.
    public void init(int startX, int startY)
    {
        //Start entity's position at location
        this.positionX = startX;
        this.positionY = startY;

        //Call entity's method that loads its image.
        this.loadImage();
    }

    //Loads the entity's image into the game, and sets its length and width.
    abstract void loadImage();

    public void draw(Graphics2D g2d, Game game)
    {
        //Draws the entity's image in the program window, at its x and y location.
        g2d.drawImage(this.image, this.positionX, this.positionY, game);
    }

    public Rectangle getBounds()
    {
        // returns a rectangle bounds for the entity
        return new Rectangle(this.positionX, this.positionY, this.width, this.height);
    }

    //Getter functions.
    public int getPositionX()
    {
        return this.positionX;
    }
    public int getPositionY()
    {
        return this.positionY;
    }
    public int getDirectionX()
    {
        return this.directionX;
    }
    public int getDirectionY()
    {
        return this.directionY;
    }
    public int getWidth()
    {
        return this.width;
    }
    public int getHeight()
    {
        return this.height;
    }
    public Image getImage()
    {
        return this.image;
    }
    public Image[] getSprites()
    {
        return sprites;
    }
}
