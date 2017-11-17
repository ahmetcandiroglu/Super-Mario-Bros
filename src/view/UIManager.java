package view;

import manager.GameEngine;
import manager.GameStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UIManager extends JPanel{

    private GameEngine engine;
    private Font gameFont;
    private BufferedImage startScreenImage, aboutScreenImage, helpScreenImage, gameOverScreen;
    private BufferedImage sprite, heartIcon, coinIcon, selectIcon;

    public UIManager(GameEngine engine, int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));

        this.engine = engine;
        ImageLoader loader = engine.getImageLoader();

        this.sprite = loader.loadImage("/sprite.png");
        this.heartIcon = loader.loadImage("/heart-icon.png");
        this.coinIcon = loader.getSubImage(sprite, 1, 5, 48, 48);
        this.selectIcon = loader.loadImage("/select-icon.png");
        this.startScreenImage = loader.loadImage("/start-screen.png");
        this.helpScreenImage = loader.loadImage("/help-screen.png");
        this.aboutScreenImage = loader.loadImage("/about-screen.png");
        this.gameOverScreen = loader.loadImage("/game-over.png");

        try {
            gameFont = Font.createFont(Font.TRUETYPE_FONT, new File("./src/media/font/mario-font.ttf"));
        } catch (FontFormatException | IOException e) {
            gameFont = new Font("Verdana", Font.PLAIN, 12);
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        GameStatus gameStatus = engine.getGameStatus();

        if(gameStatus == GameStatus.START_SCREEN){
            drawStartScreen(g2);
        }
        else if(gameStatus == GameStatus.ABOUT_SCREEN){
            drawAboutScreen(g2);
        }
        else if(gameStatus == GameStatus.HELP_SCREEN){
            drawHelpScreen(g2);
        }
        else if(gameStatus == GameStatus.GAME_OVER){
            drawGameOverScreen(g2);
        }
        else {
            g2.translate(-engine.getCamera().getX(), -engine.getCamera().getY());
            engine.getGameMap().drawMap(g2);
            g2.translate(engine.getCamera().getX(), engine.getCamera().getY());

            drawPoints(g2);
            drawRemainingLives(g2);
            drawAcquiredCoins(g2);

            if(gameStatus == GameStatus.PAUSED){
                drawPauseScreen(g2);
            }
        }

        g2.dispose();
    }

    private void drawHelpScreen(Graphics2D g2) {
        g2.drawImage(helpScreenImage, 0, 0, null);
    }

    private void drawAboutScreen(Graphics2D g2) {
        g2.drawImage(aboutScreenImage, 0, 0, null);
    }

    private void drawGameOverScreen(Graphics2D g2) {
        g2.drawImage(gameOverScreen, 0, 0, null);
    }

    private void drawPauseScreen(Graphics2D g2) {
        g2.setFont(gameFont.deriveFont(50f));
        g2.setColor(Color.WHITE);
        String displayedStr = "PAUSED";
        int stringLength = g2.getFontMetrics().stringWidth(displayedStr);
        g2.drawString(displayedStr, (getWidth()-stringLength)/2, getHeight()/2);
    }

    private void drawAcquiredCoins(Graphics2D g2) {
        g2.setFont(gameFont.deriveFont(30f));
        g2.setColor(Color.WHITE);
        String displayedStr = "" + engine.getGameMap().getMario().getCoins();
        g2.drawImage(coinIcon, getWidth()-115, 10, null);
        g2.drawString(displayedStr, getWidth()-65, 50);
    }

    private void drawRemainingLives(Graphics2D g2) {
        g2.setFont(gameFont.deriveFont(30f));
        g2.setColor(Color.WHITE);
        String displayedStr = "" + engine.getGameMap().getMario().getRemainingLives();
        g2.drawImage(heartIcon, 50, 10, null);
        g2.drawString(displayedStr, 100, 50);
    }

    private void drawPoints(Graphics2D g2){
        g2.setFont(gameFont.deriveFont(25f));
        g2.setColor(Color.WHITE);
        String displayedStr = "" + engine.getGameMap().getMario().getPoints();
        int stringLength = g2.getFontMetrics().stringWidth(displayedStr);;
        //g2.drawImage(coinIcon, 50, 10, null);
        g2.drawString(displayedStr, getWidth()/2-stringLength, 50);
    }

    private void drawStartScreen(Graphics2D g2){
        int row = engine.getSelection().getLineNumber();
        g2.drawImage(startScreenImage, 0, 0, null);
        g2.drawImage(selectIcon, 375, row * 70 + 440, null);
    }
}