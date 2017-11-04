package manager;

import model.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class UIManager extends JPanel{

    private int width;

    private int height;

    private GameStatus gameStatus;

    private JPanel pausePanel;

    private ImageIcon loadingScreen;

    private ImageIcon gameOverScreen;

    private BufferedImage backgroundImage;


    UIManager(int width, int height){
        super();
        this.width = width;
        this.height = height;

        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    void drawMapComponents(Map map){
        // draw background img
        BufferedImage backgroundImage = map.getBackgroundImage();
        //Point locationMario = map.getHeroLocation();
        backgroundImage = backgroundImage.getSubimage(0, 0, getWidth(), getHeight());
        setBackgroundImage(backgroundImage);
        repaint();

        // draw bricks
        // draw mario
        // draw enemies
    }

    private void setBackgroundImage(BufferedImage backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    void gameOver() {
    }
}