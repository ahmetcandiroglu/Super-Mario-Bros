package model.prize;

import manager.GameEngine;
import model.Map;
import model.hero.Mario;

import java.awt.image.BufferedImage;

public class OneUpMushroom extends BoostItem{

    public OneUpMushroom(double x, double y, BufferedImage style) {
        super(x, y, style);
        setPoint(200);
    }

    @Override
    public void onTouch(Map gameMap) {
        Mario mario = gameMap.getMario();
        mario.acquirePoints(getPoint());
        mario.setRemainingLives(mario.getRemainingLives() + 1);
    }

    @Override
    public void playOnTouch(GameEngine engine) {
        engine.playOneUp();
    }
}
