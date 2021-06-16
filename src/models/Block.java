package models;

public class Block extends Entity
{
    public Block(int x, int y, int width, int height)
    {
        this.positionX = x;
        this.positionY = y;
        this.width = width;
        this.height = height;
    }

    // image is built into level.png
    @Override
    void loadImage()
    {
        ;
    }
}
