package manager;

import model.Map;
import model.brick.Brick;
import model.brick.NonEmptyBrick;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MapCreator {

    private Dimension brickDimension = new Dimension(16, 16);

    private BufferedImage mapImage;

    private BufferedImage surpriseBrick, ordinaryBrick;

    public MapCreator(){

        try{
            mapImage = ImageIO.read(new File("./src/media/background/map.png"));
            surpriseBrick = ImageIO.read(new File("./src/media/brick/surprise-brick.png"));
            ordinaryBrick = ImageIO.read(new File("./src/media/brick/ordinary-brick.png"));

            System.out.println("Images have been loaded..");
        }
        catch(IOException e){
            mapImage = null;
            System.out.println("One or more image do not exist!");
        }
    }

    public Map createMap(int lifeSpan, double timeLimitInMicro){
        if(mapImage == null){
            System.out.println("Given path is invalid...");
            return null;
        }

        // assuming that bricks can only be in 16x16 sized grids
        BufferedImage imageToProcess;
        Map createdMap = new Map(lifeSpan, timeLimitInMicro);

        for(int x = 0; x < mapImage.getWidth(); x = x + 16){
            for (int y = 0; y < mapImage.getHeight(); y = y + 16) {
                imageToProcess = mapImage.getSubimage(x, y, 16, 16);
                boolean containsSurprise = true;
                boolean containsOrdinary = true;

                for(int pixel_x = 0; pixel_x < 16; pixel_x = pixel_x + 4){
                    for (int pixel_y = 0;  pixel_y < 16; pixel_y = pixel_y + 4) {
                        int mapPixel = imageToProcess.getRGB(pixel_x, pixel_y);
                        int surprisePixel = surpriseBrick.getRGB(pixel_x, pixel_y);
                        int ordinaryPixel = ordinaryBrick.getRGB(pixel_x, pixel_y);

                        if(mapPixel != surprisePixel){
                            containsSurprise = false;
                        }
                        if(mapPixel != ordinaryPixel){
                            containsOrdinary = false;
                        }
                    }
                }

                if(containsOrdinary){
                    Brick brick = new Brick(new Point(x, y), new ImageIcon(ordinaryBrick));
                    createdMap.addBrick(brick);
                    System.out.println("Ordinary brick at : " + x + " - " + y);
                }
                else if(containsSurprise){
                    Brick brick = new NonEmptyBrick(new Point(x, y), new ImageIcon(surpriseBrick));
                    createdMap.addBrick(brick);
                    System.out.println("Surprise brick at : " + x + " - " + y);
                }
            }
        }

        System.out.println("Finished..");
        return createdMap;
    }

}
