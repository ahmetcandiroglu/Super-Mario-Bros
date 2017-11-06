package model;

import javax.swing.*;
import java.awt.*;

public abstract class GameObject {

    private Point location;

    private ImageIcon style;

    private Dimension dimension;

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public ImageIcon getStyle() {
        return style;
    }

    public void setStyle(ImageIcon style) {
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
