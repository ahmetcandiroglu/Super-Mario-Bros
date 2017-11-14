package view;

import manager.GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UIManager extends JPanel{

    private GameEngine engine;
    private Font gameFont;
    private BufferedImage startScreenImage, heartIcon, sprite, coinIcon;


    public UIManager(GameEngine engine, int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));

        this.engine = engine;
        this.heartIcon = engine.getImageLoader().loadImage("/heart-icon.png");
        this.sprite = engine.getImageLoader().loadImage("/sprite.png");
        this.coinIcon = engine.getImageLoader().getSubImage(sprite, 1, 5, 48, 48);

        try {
            gameFont = Font.createFont(Font.TRUETYPE_FONT, new File("./src/media/font/mario-font.ttf"));
            startScreenImage = engine.getImageLoader().loadImage("/start-screen.png");
        } catch (FontFormatException | IOException e) {
            gameFont = new Font("Verdana", Font.PLAIN, 12);
            System.out.println(e);
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        g2.translate(-engine.getCamera().getX(), -engine.getCamera().getY());

        engine.getGameMap().drawMap(g2);

        g2.translate(engine.getCamera().getX(), engine.getCamera().getY());

        g2.setFont(gameFont.deriveFont(30f));
        g2.setColor(Color.WHITE);
        drawRemainingLives(g2);
        drawAcquiredCoins(g2);

        g2.dispose();
    }

    private void drawAcquiredCoins(Graphics2D g2) {
        String displayedStr = "" + engine.getGameMap().getMario().getCoins();
        g2.drawImage(heartIcon, getWidth()-115, 10, null);
        g2.drawString(displayedStr, 100, 50);
    }

    private void drawRemainingLives(Graphics2D g2) {
        String displayedStr = "" + engine.getGameMap().getMario().getRemainingLives();
        g2.drawImage(coinIcon, 50, 10, null);
        g2.drawString(displayedStr, getWidth()-65, 50);
    }


}