package model.prize;

import model.hero.Mario;
import model.hero.SmallMario;
import model.hero.SuperMario;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SuperMushroom extends BoostItem{

    public SuperMushroom(double x, double y, BufferedImage style) {
        super(x, y, style);
    }

    @Override
    public Mario onTouch(Mario mario) {
        mario.acquirePoints(getPoint());

        if(mario instanceof SmallMario){
            int remainingLives = mario.getRemainingLives();
            int points = mario.getPoints();

            Mario newMario = new SuperMario(mario.getX(), mario.getY(), loadSuperStyle());
            newMario.setRemainingLives(remainingLives);
            newMario.setPoints(points);
            return newMario;
        }

        return mario;
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
