package game;

import models.Block;
import models.Coin;
import models.Level;
import models.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.SwingUtilities;

public class Game extends JPanel implements ActionListener
{
    // game entity objects
    Player player1;
    Player player2;
    Level level;
    ArrayList<Coin> coins = new ArrayList<Coin>();
    Block[] blocks = new Block[8];

    //Initializes the game.
    public void init()
    {
        // Initializes the level
        level = new Level();
        level.init(0, 0);

        //Initializes the player.
        player1 = new Player(0);
        player2 = new Player(1);
        player1.init(Config.player1StartX, Config.player1StartY);
        player2.init(Config.player2StartX, Config.player2StartY);

        // generate level blocks
        blocks[0] = new Block(0, 420, 50, 50);
        blocks[1] = new Block(200, 420, 50, 50);
        blocks[2] = new Block(250, 420, 50, 50);
        blocks[3] = new Block(300, 420, 50, 50);
        blocks[4] = new Block(350, 420, 50, 50);
        blocks[5] = new Block(400, 420, 50, 50);
        blocks[6] = new Block(300, 200, 50, 50);
        blocks[7] = new Block(650, 520, 100, 200);

        // generate coins
        do
        {
            Coin coin = new Coin();
            if (coin.validLocation(blocks))
            {
                coins.add(coin);
            }
        } while (coins.size() < Config.maxCoins);


    }

    //Updates all game objects.
    public void step(ActionEvent e)
    {
        //Updates the player's movement.
        player1.collisionCheck(coins, blocks);
        player1.step();

        player2.collisionCheck(coins, blocks);
        player2.step();

        // adds a new coin when less than max coins
        // generate coins
        while (coins.size() < Config.maxCoins)
        {
            Coin coin = new Coin();
            if (coin.validLocation(blocks))
            {
                coins.add(coin);
            }
        }
    }

    //Draws all game objects.
    public void draw(Graphics2D g2d)
    {
        // Draws the background
        level.draw(g2d, this);

        //Draws the player.
        player1.draw(g2d, this);
        player2.draw(g2d, this);

        // draw scores
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 60));
        g2d.drawString(String.valueOf(player1.getScore()), player1.getPositionX() + player1.getWidth()/4, 100);
        g2d.drawString(String.valueOf(player2.getScore()), player2.getPositionX() + player2.getWidth()/4, 100);

        // draw coins
        for (Coin coin : coins)
        {
            coin.draw(g2d, this);
        }
    }

    //Automatically activates when a keyboard key is pressed.
    public void keyPress(int key)
    {
        //Sends key-press input to player.
        player1.keyPress(key);
        player2.keyPress(key);

        //Prints key pressed to console log.
        //System.out.println("Key Pressed: " + key);
    }

    //Automatically activates when a keyboard key is released.
    public void keyRelease(int key)
    {
        //Sends key-release input to player.
        player1.keyRelease(key);
        player2.keyRelease(key);

        //Prints key released to console log.
        //System.out.println("Key Released: " + key);
    }

    //Activates when mouse button is pressed and then released.
    public void mouseClick(int x, int y, int button)
    {
        //Prints mouse coordinates to console log.
        //System.out.println("Mouse button " + button + " clicked: (" + x + ", " + y + ")");
    }

    //Activates when mouse button is pressed.
    public void mousePress(int x, int y, int button)
    {
        //Prints mouse coordinates to console log.
        //System.out.println("Mouse button " + button + " pressed: (" + x + ", " + y + ")");
    }

    //Activates when mouse button is released.
    public void mouseRelease(int x, int y, int button)
    {
        //Prints mouse coordinates to console log.
        //System.out.println("Mouse button " + button + " released: (" + x + ", " + y + ")");
    }

    //Activates when mouse cursor enters window.
    public void mouseEnter(int x, int y)
    {
        //Prints mouse coordinates to console log.
        //System.out.println("Mouse Entered: (" + x + ", " + y + ")");
    }

    //Activates when mouse cursor exits window.
    public void mouseExit(int x, int y)
    {
        //Prints mouse coordinates to console log.
        //System.out.println("Mouse Exited: (" + x + ", " + y + ")");
    }

    //Activates when mouse moves within the window.
    public void mouseMovement(int x, int y)
    {
        //Prints mouse coordinates to console log.
        //System.out.println("Mouse Moved: (" + x + ", " + y + ")");
    }

    //Activates when mouse is moved while button is pressed within the window.
    public void mouseDragging(int x, int y, int button)
    {
        //Prints mouse coordinates to console log.
        //System.out.println("Mouse button " + button + " dragged: (" + x + ", " + y + ")");
    }

    //Activates when mouse wheel has moved.
    public void mouseWheel(int direction)
    {
        //Prints mouse wheel amount to console log.
        //System.out.println("Mouse wheel direction: " + direction);
    }

    //////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////ENGINE////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////

    //Updates program based on speed delay interval.
    private Timer timer;

    //Constructs/initializes the game.
    public Game()
    {
        //Sets up keyboard input, using nested private GameKeyboardAdapter class.
        addKeyListener(new GameKeyboardAdapter());
        addMouseListener(new GameMouseListener());
        addMouseMotionListener(new GameMouseMotionListener());
        addMouseWheelListener(new GameMouseWheelListener());

        //Initializes the game.
        init();

        //Allows keyboard input to work.
        setFocusable(true);

        //For every DELAY amount in milliseconds, the timer will call the actionPerformed() method,
        //which updates the movement of objects in the game. Think of it as the program's frame-rate.
        //Program animation speed interval.
        int DELAY = 15;
        this.timer = new Timer(DELAY, this);
        this.timer.start();
    }

    //All game graphics are drawn inside the paintComponent() method.
    @Override
    public void paintComponent(Graphics g)
    {
        //Draws JPanel window
        super.paintComponent(g);

        //The Graphics2D class extends the Graphics class.
        //It provides more sophisticated control over geometry,
        //coordinate transformations, colour management, and text layout.
        Graphics2D g2d = (Graphics2D) g;

        //Draws all game components.
        draw(g2d);

        //Toolkit.getDefaultToolkit().sync() synchronises the painting on systems that buffer graphics events.
        //Without this line, the animation might not be smooth on Linux.
        Toolkit.getDefaultToolkit().sync();
    }

    //Updates the movement of objects in the game. This method is repeatedly called by the Timer object.
    //NOTE:	In order to use the actionPerformed() method, the ActionListener interface must be implemented
    //		in the class' signature header, above.
    @Override
    public void actionPerformed(ActionEvent e)
    {
        //Updates all game objects.
        step(e);

        //Causes the paintComponent() drawing method to be called.
        //This way we can regularly redraw the game.Game, thus making the animation.
        repaint();
    }

    //This inner private class detects keyboard inputs by the user.
    private class GameKeyboardAdapter extends KeyAdapter
    {
        //Detects whenever a key (represented by variable "e") is released.
        @Override
        public void keyReleased(KeyEvent e)
        {
            //Gets the key value of the key just released.
            int key = e.getKeyCode();

            //Send key to game function.
            keyRelease(key);
        }

        //Detects whenever a key (represented by variable "e") is pressed down.
        @Override
        public void keyPressed(KeyEvent e)
        {
            //Gets the key value of the key just pressed.
            int key = e.getKeyCode();

            //Send key to game function.
            keyPress(key);
        }
    }

    //This inner private class detects mouse inputs and location by the user.
    private class GameMouseListener implements MouseListener
    {
        //Activates when mouse button is pressed and then released.
        @Override
        public void mouseClicked(MouseEvent e)
        {
            //Gets coordinates of mouse, and assigns it to mouse coordinate variables.
            int mouseX = e.getX();
            int mouseY = e.getY();

            //Figures out which button on mouse was pressed.
            //0 means left, 1 means middle, 2 means right.
            int mouseButton = -1;
            if (SwingUtilities.isLeftMouseButton(e))
                mouseButton = 0;
            else if (SwingUtilities.isMiddleMouseButton(e))
                mouseButton = 1;
            else if (SwingUtilities.isRightMouseButton(e))
                mouseButton = 2;

            //Sends mouse coordinates to game function.
            mouseClick(mouseX,mouseY,mouseButton);
        }

        //Activates when mouse button is pressed.
        @Override
        public void mousePressed(MouseEvent e)
        {
            //Gets coordinates of mouse, and assigns it to mouse coordinate variables.
            int mouseX = e.getX();
            int mouseY = e.getY();

            //Figures out which button on mouse was pressed.
            //0 means left, 1 means middle, 2 means right.
            int mouseButton = -1;
            if (SwingUtilities.isLeftMouseButton(e))
                mouseButton = 0;
            else if (SwingUtilities.isMiddleMouseButton(e))
                mouseButton = 1;
            else if (SwingUtilities.isRightMouseButton(e))
                mouseButton = 2;

            //Sends mouse coordinates to game function.
            mousePress(mouseX,mouseY,mouseButton);
        }

        //Activates when mouse button is released.
        @Override
        public void mouseReleased(MouseEvent e)
        {
            //Gets coordinates of mouse, and assigns it to mouse coordinate variables.
            int mouseX = e.getX();
            int mouseY = e.getY();

            //Figures out which button on mouse was pressed.
            //0 means left, 1 means middle, 2 means right.
            int mouseButton = -1;
            if (SwingUtilities.isLeftMouseButton(e))
                mouseButton = 0;
            else if (SwingUtilities.isMiddleMouseButton(e))
                mouseButton = 1;
            else if (SwingUtilities.isRightMouseButton(e))
                mouseButton = 2;

            //Sends mouse coordinates to game function.
            mouseRelease(mouseX,mouseY,mouseButton);
        }

        //Activates when mouse cursor enters window.
        @Override
        public void mouseEntered(MouseEvent e)
        {
            //Gets coordinates of mouse, and assigns it to mouse coordinate variables.
            int mouseX = e.getX();
            int mouseY = e.getY();

            //Sends mouse coordinates to game function.
            mouseEnter(mouseX,mouseY);
        }

        //Activates when mouse cursor exits window.
        @Override
        public void mouseExited(MouseEvent e)
        {
            //Gets coordinates of mouse, and assigns it to mouse coordinate variables.
            int mouseX = e.getX();
            int mouseY = e.getY();

            //Sends mouse coordinates to game function.
            mouseExit(mouseX,mouseY);
        }
    }

    //This inner private class detects mouse movement and location by the user.
    private class GameMouseMotionListener extends Frame implements MouseMotionListener
    {
        //Activates when mouse moves within the window.
        @Override
        public void mouseMoved(MouseEvent e)
        {
            //Gets coordinates of mouse, and assigns it to mouse coordinate variables.
            int mouseX = e.getX();
            int mouseY = e.getY();

            //Sends mouse coordinates to game function.
            mouseMovement(mouseX,mouseY);
        }

        //Activates when mouse is moved while button is pressed within the window.
        @Override
        public void mouseDragged(MouseEvent e)
        {
            //Gets coordinates of mouse, and assigns it to mouse coordinate variables.
            int mouseX = e.getX();
            int mouseY = e.getY();

            //Figures out which button on mouse was pressed.
            //0 means left, 1 means middle, 2 means right.
            int mouseButton = -1;
            if (SwingUtilities.isLeftMouseButton(e))
                mouseButton = 0;
            else if (SwingUtilities.isMiddleMouseButton(e))
                mouseButton = 1;
            else if (SwingUtilities.isRightMouseButton(e))
                mouseButton = 2;

            //Sends mouse coordinates to game function.
            mouseDragging(mouseX,mouseY,mouseButton);
        }
    }

    //This inner private class detects mouse wheel movement by the user.
    private class GameMouseWheelListener extends JPanel implements MouseWheelListener
    {
        //Activates when mouse wheel has moved.
        @Override
        public void mouseWheelMoved(MouseWheelEvent e)
        {
            //Sends mouse wheel status to game function.
            //-1 means downwards, 1 means upwards.
            mouseWheel(e.getWheelRotation());
        }
    }
}