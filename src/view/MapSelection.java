package view;

import java.awt.*;
import java.io.File;

public class MapSelection {

    private File[] maps;
    private MapSelectionItem[] mapSelectionItems;

    public MapSelection(){
        getMaps();
        this.mapSelectionItems = createItems(this.maps);
    }

    public void draw(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0,0, 1280, 720);

        if(mapSelectionItems == null)
            return;

        String title = "Select a Map";
        int x_location = (1280 - g.getFontMetrics().stringWidth(title))/2;
        g.setColor(Color.YELLOW);
        g.drawString(title, x_location, 150);

        for(MapSelectionItem item : mapSelectionItems){
            g.setColor(Color.WHITE);
            int width = g.getFontMetrics().stringWidth(item.getName().split("[.]")[0]);
            int height = g.getFontMetrics().getHeight();
            item.setDimension( new Dimension(width, height));
            item.setLocation( new Point((1280-width)/2, item.getLocation().y));
            g.drawString(item.getName().split("[.]")[0], item.getLocation().x, item.getLocation().y);
        }
    }

    private void getMaps(){
        File file = new File("src/media/maps");
        this.maps = file.listFiles();
    }

    private MapSelectionItem[] createItems(File[] maps){
        if(maps == null)
            return null;

        int defaultGridSize = 100;
        MapSelectionItem[] items = new MapSelectionItem[maps.length];
        for (int i = 0; i < items.length; i++) {
            Point location = new Point(0, (i+1)*defaultGridSize+200);
            items[i] = new MapSelectionItem(maps[i], location);
        }

        return items;
    }

    public String selectMap(Point mouseLocation) {
        for(MapSelectionItem item : mapSelectionItems) {
            Dimension dimension = item.getDimension();
            Point location = item.getLocation();
            boolean inX = location.x <= mouseLocation.x && location.x + dimension.width >= mouseLocation.x;
            boolean inY = location.y >= mouseLocation.y && location.y - dimension.height <= mouseLocation.y;
            if(inX && inY){
                return item.getName();
            }
        }
        return null;
    }

    public String selectMap(int index){
        if(index < mapSelectionItems.length && index > -1)
            return mapSelectionItems[index].getName();
        return null;
    }

    public int changeSelectedMap(int index, boolean up) {
        if(up){
            if(index <= 0)
                return mapSelectionItems.length - 1;
            else
                return index - 1;
        }
        else{
            if(index >= mapSelectionItems.length - 1)
                return 0;
            else
                return index + 1;
        }
    }
}
