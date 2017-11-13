package view;

import manager.GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UIManager extends JPanel{

    private GameEngine engine;
    private Font gameFont;
    private BufferedImage startScreenImage;


    public UIManager(GameEngine engine, int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));

        this.engine = engine;

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
        g2.dispose();
    }
}