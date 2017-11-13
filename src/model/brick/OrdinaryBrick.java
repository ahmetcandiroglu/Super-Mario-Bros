package model.brick;

import java.awt.image.BufferedImage;

public class OrdinaryBrick extends Brick {


    public OrdinaryBrick(double x, double y, BufferedImage style){
        super(x, y, style);
        setBreakable(true);
        setEmpty(true);
    }
}
