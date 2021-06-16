package models;

import game.Config;

import java.util.Random;
import javax.swing.*;
import java.awt.*;

public class Coin extends Entity
{
    public Coin()
    {
        super();

        // random positions
        Random random = new Random();
        this.positionX = random.nextInt(Config.windowWidth - Config.coinWidth);
        this.positionY = 200 + random.nextInt(Config.floorY - 200 + 1);

        // loads the image
        loadImage();
    }

    @Override
    void loadImage()
    {
        //Uses the ImageIcon class to load the image file into the program.
        ImageIcon file = new ImageIcon("src/resources/coin.png");

        //Assigns the image data to its image variable reference.
        this.image = file.getImage();

        //Sets the entity's width and height to be that of the image just loaded in.
        this.width = this.image.getWidth(null);
        this.height = this.image.getHeight(null);

        //Resizes the image to 50 width and 67 height.
        this.width = Config.coinWidth;
        this.height = Config.coinHeight;
        this.image = this.image.getScaledInstance(this.width, this.height, Image.SCALE_DEFAULT);
    }

    // returns bool for whether the coin is in a valid location (e.g. not in a block)
    public boolean validLocation(Block[] blocks)
    {
        // return false if collides with a block
        for (Block block : blocks)
        {
            if (getBounds().intersects(block.getBounds()))
            {
                return false;
            }
        }
        // else return true
        return true;
    }

}
