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

public class SuperMushroom extends BoostItem{

    public SuperMushroom(double x, double y, BufferedImage style) {
        super(x, y, style);
        setPoint(125);
    }

    @Override
    public void onTouch(Map gameMap) {
        Mario mario = gameMap.getMario();
        mario.acquirePoints(getPoint());
        ImageLoader imageLoader = new ImageLoader();

        if(!(mario instanceof SuperMario) ){
            Mario newMario = new SuperMario(
                    mario.getX(),
                    mario.getY(),
                    imageLoader.getLeftFrames(1),
                    imageLoader.getRightFrames(1));

            newMario.setRemainingLives(mario.getRemainingLives());
            newMario.setPoints(mario.getPoints());
            newMario.setCoins(mario.getCoins());
            gameMap.setMario(newMario);
        }
    }

    private BufferedImage loadSuperStyle(){
        BufferedImage style = null;
        try {
            style = ImageIO.read( new File("./src/media/mario/super-mario.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return style;
    }
}
