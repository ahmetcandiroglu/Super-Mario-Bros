package manager;

import model.Map;
import model.brick.Brick;
import model.brick.OrdinaryBrick;
import model.brick.Pipe;
import model.brick.SurpriseBrick;
import model.enemy.Enemy;
import model.enemy.Goomba;
import model.enemy.KoopaTroopa;
import model.hero.Mario;
import model.hero.SmallMario;
import model.prize.Coin;
import model.prize.Prize;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class MapCreator {

    private BufferedImage mapImage, backgroundImage, mario;
    private BufferedImage superMushroom, oneUpMushroom, fireMushroom, coin;
    private BufferedImage ordinaryBrick, surpriseBrick, groundBrick, pipe;
    private BufferedImage goomba, koopa;


    MapCreator(int screenWidth, int screenHeight) {

        try {
            mapImage = ImageIO.read(new File("./src/media/map/created.png"));
            backgroundImage = ImageIO.read(new File("./src/media/background/background.png"));
            mario = ImageIO.read(new File("./src/media/mario/small-mario.png"));
            ordinaryBrick = ImageIO.read(new File("./src/media/brick/ordinary-brick.png"));
            surpriseBrick = ImageIO.read(new File("./src/media/brick/surprise-brick.png"));
            groundBrick = ImageIO.read(new File("./src/media/brick/ground-brick.png"));
            pipe = ImageIO.read(new File("./src/media/brick/pipe-s.png"));
            goomba = ImageIO.read(new File("./src/media/enemy/goomba.png"));
            koopa = ImageIO.read(new File("./src/media/enemy/koopa.png"));
            superMushroom = ImageIO.read(new File("./src/media/map/created.png"));
            oneUpMushroom = ImageIO.read(new File("./src/media/map/created.png"));
            fireMushroom = ImageIO.read(new File("./src/media/map/created.png"));
            coin = ImageIO.read(new File("./src/media/map/created.png"));

            System.out.println("Images have been loaded..");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Map createMap(double timeLimit) {
        if (mapImage == null) {
            System.out.println("Given path is invalid...");
            return null;
        }

        Map createdMap = new Map(timeLimit, backgroundImage);
        int brickSize = this.surpriseBrick.getHeight();

        int mario = new Color(160, 160, 160).getRGB();
        int ordinaryBrick = new Color(0, 0, 255).getRGB();
        int surpriseBrick = new Color(255, 255, 0).getRGB();
        int groundBrick = new Color(255, 0, 0).getRGB();
        int pipe = new Color(0, 255, 0).getRGB();
        int goomba = new Color(0, 255, 255).getRGB();
        int koompa = new Color(255, 0, 255).getRGB();


        for (int x = 0; x < mapImage.getWidth(); x++) {
            for (int y = 0; y < mapImage.getHeight(); y++) {

                int currentPixel = mapImage.getRGB(x, y);
                Prize prize = new Coin(x, y, this.coin, 50);

                if (currentPixel == ordinaryBrick) {
                    Brick brick = new OrdinaryBrick(x*brickSize, y*brickSize, this.ordinaryBrick);
                    createdMap.addBrick(brick);
                }
                else if (currentPixel == surpriseBrick) {
                    Brick brick = new SurpriseBrick(x*brickSize, y*brickSize, this.surpriseBrick, prize);
                    createdMap.addBrick(brick);
                }
                else if (currentPixel == pipe) {
                    Brick brick = new Pipe(x*brickSize, y*brickSize, this.pipe);
                    createdMap.addGroundBrick(brick);
                }
                else if (currentPixel == groundBrick) {
                    Brick brick = new Pipe(x*brickSize, y*brickSize, this.groundBrick);
                    createdMap.addGroundBrick(brick);
                }
                else if (currentPixel == goomba) {
                    Enemy enemy = new Goomba(x*brickSize, y*brickSize, this.goomba);
                    createdMap.addEnemy(enemy);
                }
                else if (currentPixel == koompa) {
                    Enemy enemy = new KoopaTroopa(x*brickSize, y*brickSize, this.koopa);
                    createdMap.addEnemy(enemy);
                }
                else if (currentPixel == mario) {
                    Mario marioObject = new SmallMario(x*brickSize, y*brickSize, this.mario);
                    createdMap.setMario(marioObject);
                }
            }
        }

        System.out.println("Map created..");
        return createdMap;
    }


}
