package models;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ImageIcon;

import game.Config;

public class Player extends Entity
{
    // score tracking
    private int score;
    private final int playerNum;

    // player num = 0 or 1 depending on which player
    // 0 = mario
    // 1 = luigi
    public Player(int playerNum) {
        super();
        this.sprites = new Image[2];
        this.directionY = Config.playerSpeedY;
        this.playerNum = playerNum;
    }

    //Loads the entity's images into the game, and sets its length and width.
    @Override
    protected void loadImage()
    {
        //Uses the ImageIcon class to load the image file into the program.
        ImageIcon[] temp = new ImageIcon[2];
        if (this.playerNum == 0)
        {
            ImageIcon file1 = new ImageIcon("src/resources/mario_right.png");
            ImageIcon file2 = new ImageIcon("src/resources/mario_left.png");
            temp = new ImageIcon[]{file1, file2};
        }
        else
        {
            System.out.println("sheesh");
            ImageIcon file1 = new ImageIcon("src/resources/luigi_right.png");
            ImageIcon file2 = new ImageIcon("src/resources/luigi_left.png");
            temp = new ImageIcon[]{file1, file2};
        }

        for (int x = 0; x < 2; x++)
        {
            //Assigns the image data to its image variable reference.
            Image image = temp[x].getImage();

            //Sets the entity's width and height to be that of the image just loaded in.
            this.width = image.getWidth(null);
            this.height = image.getHeight(null);

            //Resizes the image to 50 width and 67 height.
            this.width = Config.playerWidth;
            this.height = Config.playerHeight;

            // scale iamge
            image = image.getScaledInstance(this.width, this.height, Image.SCALE_DEFAULT);

            //adds to sprite array
            this.sprites[x] = image;

            // if first set it to default
            if (x == 0)
                this.image = image;
        }


    }

    //Changes the position coordinates of the sprite.
    //The positionX and positionY values are later used in the game.Game's paintComponent() method to draw the image of the sprite.
    public void step()
    {
        // block walking off screen
        if (this.positionX <= 0 || this.positionX >= Config.windowWidth - Config.playerWidth)
        {
            this.directionX = 0;
            // add to position to put player back in
            if (this.positionX <= 0)
            {
                this.positionX += Config.playerSpeedX;
            }
            // subtract to position to put player back in
            else if (this.positionX >= Config.windowWidth - Config.playerWidth)
            {
                this.positionX -= Config.playerSpeedX;
            }
        }

        //Changes the entity's position, based on the user inputted direction.
        this.positionX += this.directionX;
        this.positionY += this.directionY;

        this.directionY += Config.verticalAcceleration;

        // stop gravity if on the floor
        if (this.positionY > Config.floorY)
        {
            this.directionY = 0;
            this.positionY -= this.positionY - Config.floorY;
        }

    }

    //When we press a keyboard arrow key, we set its respective direction variable to a value of 2.
    //This will make the spacecraft move that direction.
    public void keyPress(int key)
    {
        if (this.playerNum == 0) {
            //All if-statements are seperate, so that multiple keys can be pressed at the same time.
            if (key == KeyEvent.VK_LEFT) {
                this.directionX = -Config.playerSpeedX;
                this.image = this.sprites[1];
            }
            if (key == KeyEvent.VK_RIGHT) {
                this.directionX = Config.playerSpeedX;
                this.image = this.sprites[0];
            }
            if (key == KeyEvent.VK_UP) {
                // prevent infinite up jumping
                if (this.directionY == 0)
                    this.directionY = -40;
            }
        }
        else
        {
            //All if-statements are seperate, so that multiple keys can be pressed at the same time.
            if (key == KeyEvent.VK_A) {
                this.directionX = -Config.playerSpeedX;
                this.image = this.sprites[1];
            }
            if (key == KeyEvent.VK_D) {
                this.directionX = Config.playerSpeedX;
                this.image = this.sprites[0];
            }
            if (key == KeyEvent.VK_W) {
                // prevent infinite up jumping
                if (this.directionY == 0)
                    this.directionY = -40;
            }
        }
    }

    //When we release a keyboard arrow key, we set its respective direction variable to zero.
    //This will stop the spacecraft from moving that direction.
    public void keyRelease(int key)
    {
        if (this.playerNum == 0) {
            //All if-statements are separate, so that multiple keys can be pressed at the same time.
            if (key == KeyEvent.VK_LEFT) {
                this.directionX = 0;
            }
            if (key == KeyEvent.VK_RIGHT) {
                this.directionX = 0;
            }
        }
        else
        {
            if (key == KeyEvent.VK_A) {
                this.directionX = 0;
            }
            if (key == KeyEvent.VK_D) {
                this.directionX = 0;
            }
        }
    }

    public void collisionCheck(ArrayList<Coin> coins, Block[] blocks) {
        // self boundaries
        Rectangle self = getBounds();

        // coin collision check
        Iterator<Coin> iter = coins.iterator();
        while (iter.hasNext())
        {
            Coin coin = iter.next();
            if (self.intersects(coin.getBounds()))
            {
                iter.remove();
                this.score++;
            }
        }

        // coin collision check
        for (Block block : blocks)
        {
            if (getBounds().intersects(block.getBounds()))
            {
                // y axis collision
                if (this.positionY > block.getPositionY() + (block.getHeight() / 2))
                    this.positionY += block.getHeight() * 2;
                else
                    this.positionY = block.positionY;
                this.directionY = 0;
            }
        }
    }

    public int getScore()
    {
        return score;
    }
}