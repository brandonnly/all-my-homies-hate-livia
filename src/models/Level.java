package models;

import javax.swing.*;

public class Level extends Entity {
    @Override
    void loadImage() {
        //Uses the ImageIcon class to load the image file into the program.
        ImageIcon file = new ImageIcon("src/resources/level.png");

        //Assigns the image data to its image variable reference.
        this.image = file.getImage();
    }
}
