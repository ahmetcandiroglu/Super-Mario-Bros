package model.prize;

import model.Map;
import model.hero.FireMario;
import model.hero.Mario;
import model.hero.SmallMario;
import model.hero.SuperMario;
import view.ImageLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FireFlower extends BoostItem {

    public FireFlower(double x, double y, BufferedImage style) {
        super(x, y, style);
        setPoint(150);
    }

    @Override
    public void onTouch(Map gameMap) {
        Mario mario = gameMap.getMario();
        mario.acquirePoints(getPoint());
        ImageLoader imageLoader = new ImageLoader();

        if(!(mario instanceof FireMario) ){
            Mario newMario = new FireMario(
                    mario.getX(),
                    mario.getY(),
                    imageLoader.getLeftFrames(2),
                    imageLoader.getRightFrames(2));

            newMario.setRemainingLives(mario.getRemainingLives());
            newMario.setPoints(mario.getPoints());
            newMario.setCoins(mario.getCoins());
            gameMap.setMario(newMario);
        }
    }

    @Override
    public void updateLocation(){}

}
