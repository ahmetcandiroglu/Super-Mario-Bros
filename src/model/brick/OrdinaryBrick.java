package model.brick;

import view.Animation;
import view.ImageLoader;

import java.awt.image.BufferedImage;

public class OrdinaryBrick extends Brick {

    private Animation animation;

    public OrdinaryBrick(double x, double y, BufferedImage style){
        super(x, y, style);
        setBreakable(true);
        setEmpty(true);

        ImageLoader imageLoader = new ImageLoader();
        BufferedImage[] frames = imageLoader.getBrickFrames();
    }
}
