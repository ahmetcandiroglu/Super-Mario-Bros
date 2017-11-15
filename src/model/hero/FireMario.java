package model.hero;

import model.Fireball;
import model.Map;
import view.ImageLoader;

import java.awt.image.BufferedImage;

public class FireMario extends Mario{

    public FireMario(double x, double y, BufferedImage[] leftFrames, BufferedImage[] rightFrames) {
        super(x, y, leftFrames, rightFrames);

    }

    @Override
    public void fire(Map gameMap){
        ImageLoader imageLoader = new ImageLoader();
        BufferedImage fireball = imageLoader.loadImage("/sprite.png");
        fireball = imageLoader.getSubImage(fireball, 3, 4, 24, 24);

        boolean toRight = getVelX() >= 0;

        gameMap.addFireball(new Fireball(getX(), getY() + 48, fireball, toRight));

    }
}
