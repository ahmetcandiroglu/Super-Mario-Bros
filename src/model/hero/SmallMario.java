package model.hero;

import java.awt.image.BufferedImage;

public class SmallMario extends Mario{

    public SmallMario(double x, double y, BufferedImage[] leftFrames, BufferedImage[] rightFrames) {
        super(x, y, leftFrames, rightFrames);
        setDimension(48,48);
    }
}
