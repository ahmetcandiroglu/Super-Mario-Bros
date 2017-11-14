package model.hero;

import view.ImageLoader;

import java.awt.image.BufferedImage;

public class SmallMario extends Mario{

    public SmallMario(double x, double y, BufferedImage[] leftFrames, BufferedImage[] rightFrames) {
        super(x, y, leftFrames, rightFrames);
        setDimension(48,48);
    }

    @Override
    public Mario onTouchEnemy(ImageLoader imageLoader) {
        if(getRemainingLives() > 0){
            this.setRemainingLives(getRemainingLives() - 1);
            return this;
        }
        return null;
    }
}
