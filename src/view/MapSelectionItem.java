package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class MapSelectionItem {

    private BufferedImage image;
    private String name;
    private Point location;
    private Dimension dimension;

    public MapSelectionItem(String map, Point location){
        this.location = location;
        this.name = map;

        ImageLoader loader = new ImageLoader();
        this.image = loader.loadImage("/maps/" + map);

        this.dimension = new Dimension();
    }


    public String getName() {
        return name;
    }

    public Point getLocation() {
        return location;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}
