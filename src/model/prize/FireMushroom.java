package model.prize;

import model.hero.FireMario;
import model.hero.Mario;
import model.hero.SmallMario;
import model.hero.SuperMario;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FireMushroom extends BoostItem {

    public FireMushroom(double x, double y, BufferedImage style) {
        super(x, y, style);
    }

    @Override
    public Mario onTouch(Mario mario) {
        mario.acquirePoints(getPoint());

        if(mario instanceof SmallMario || mario instanceof SuperMario){
            int remainingLives = mario.getRemainingLives();
            int points = mario.getPoints();

            Mario newMario = new FireMario(mario.getX(), mario.getY(), loadFireStyle());
            newMario.setRemainingLives(remainingLives);
            newMario.setPoints(points);
            return newMario;
        }

        return mario;
    }

    private BufferedImage loadFireStyle(){
        BufferedImage style = null;
        try {
            style = ImageIO.read( new File("./src/media/mario/fire-mario.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return style;
    }
}
