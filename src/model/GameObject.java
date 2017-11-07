package model;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {

    private Point location;

    private BufferedImage style;

    private Dimension dimension;

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public BufferedImage getStyle() {
        return style;
    }

    public void setStyle(BufferedImage style) {
        this.style = style;
    }

    public abstract void draw();

    public Dimension getDimension(){
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }
}
