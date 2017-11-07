package manager;

import model.Map;
import model.brick.Brick;
import model.enemy.Enemy;
import model.hero.Mario;
import model.prize.Prize;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UIManager extends JPanel{

    private Map map;

    private double mapRatio;

    private Font gameFont;

    private GameStatus gameStatus;

    private BufferedImage startScreenImage;

    private double remainingTime;

    UIManager(int width, int height){
        super();

        setPreferredSize(new Dimension(width, height));
        mapRatio = 1;

        try{
            gameFont = Font.createFont(Font.TRUETYPE_FONT, new File("./src/media/font/mario-font.ttf"));
            startScreenImage = ImageIO.read(new File("./src/media/background/start-screen.png"));
        }
        catch (FontFormatException | IOException e){
            gameFont = new Font("Verdana", Font.PLAIN, 12);
            System.out.println(e);
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //System.out.println("UIManager started drawing, game status is " + gameStatus);
        Graphics2D g2 = (Graphics2D) g.create();

        drawBackground(g2);
        drawMario(g2);
        drawBricks(g2);

        if(gameStatus == GameStatus.START_SCREEN)
            drawStartScreen(g2);

        g2.dispose();
    }

    void drawMapComponents(Map map, GameStatus gameStatus, double remainingTime){
        this.map = map;
        this.gameStatus = gameStatus;
        this.remainingTime = remainingTime;
        repaint();
    }

    private void drawBackground(Graphics2D g){
        BufferedImage backgroundImage = map.getBackgroundImage();
        Point marioLocation = map.getMarioLocation();

        int width = getWidth();
        int height = getHeight();
        mapRatio = ((double)backgroundImage.getWidth()) / backgroundImage.getHeight();

        Image imageToDraw = backgroundImage.getScaledInstance((int)(height * mapRatio), height, Image.SCALE_SMOOTH);
        BufferedImage canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        //Graphics2D g2d = canvas.createGraphics();
        g.drawImage(imageToDraw, 0, 0, null);
        //g2d.dispose();

        g.setFont(gameFont.deriveFont(25f));
        g.setColor(Color.WHITE);
        g.drawString("" + (int)remainingTime, getWidth()-200, 55);

        /*backgroundImage = backgroundImage.getSubimage(marioLocation.x - 70, 0, w, backgroundImage.getHeight());
        g.drawImage(backgroundImage, 0, 0, this);*/
    }

    private void drawMario(Graphics2D g){
        Mario mario = map.getMario();
        BufferedImage marioImage = mario.getStyle();
        Point marioLocation = mario.getLocation();

        Image imageToDraw = marioImage.getScaledInstance(mario.getDimension().width, mario.getDimension().height, Image.SCALE_SMOOTH);
        g.drawImage(imageToDraw, marioLocation.x, marioLocation.y, null);

    }

    private void drawBricks(Graphics2D g){
        ArrayList<Brick> bricks = map.getBricks();
        Point marioLocation = map.getMarioLocation();
        Dimension brickDimension = Brick.DIMENSION;

        for (Brick brick : bricks) {
            Point brickLocation = brick.getLocation();
            BufferedImage brickIcon = brick.getStyle();
            Image imageToDraw = brickIcon.getScaledInstance(
                    brickDimension.width * 3,
                    brickDimension.height * 3,
                    Image.SCALE_SMOOTH);

            //if(brickLocation.x >= marioLocation.x - 82 && brickLocation.x < marioLocation.x + 700){
                g.drawImage(imageToDraw, brickLocation.x * 3, brickLocation.y * 3, null);
            //}
        }
    }

    private void drawEnemies(Graphics2D g, ArrayList<Enemy> enemies){}

    private void drawPrizes(Graphics2D g, ArrayList<Prize> prizes){}

    private void drawStartScreen(Graphics2D g){
        g.setFont(gameFont.deriveFont(48f));
        g.setColor(Color.WHITE);
        g.drawString("Start Game", getWidth()/2-250, (int)(getHeight()/1.5));

        if(startScreenImage != null){
            g.drawImage(startScreenImage,
                    (getWidth()-startScreenImage.getWidth())/2,
                    (getHeight()-startScreenImage.getHeight())/4, null);
        }
    }

    void gameOver() {
    }
}