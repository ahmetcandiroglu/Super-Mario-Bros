package model.brick;

import java.awt.image.BufferedImage;

public class Pipe extends Brick{

    //TODO: implement length for pipe

    public Pipe(double x, double y, BufferedImage style){
        super(x, y, style);
        setBreakable(false);
        setEmpty(false);
    }
}
