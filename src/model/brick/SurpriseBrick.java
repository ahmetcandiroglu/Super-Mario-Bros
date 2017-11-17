package model.brick;

import model.Map;
import model.hero.Mario;
import model.prize.Prize;
import view.ImageLoader;

import java.awt.image.BufferedImage;

public class SurpriseBrick extends Brick{

    private Prize prize;

    public SurpriseBrick(double x, double y, BufferedImage style, Prize prize) {
        super(x, y, style);
        setBreakable(false);
        setEmpty(false);
        this.prize = prize;
    }

    @Override
    public void reveal(Map gameMap){
        ImageLoader imageLoader = new ImageLoader();
        BufferedImage newStyle = imageLoader.loadImage("/sprite.png");
        newStyle = imageLoader.getSubImage(newStyle, 1, 2, 48, 48);

        if(prize != null){
            gameMap.addRevealedPrize(prize);
            prize.reveal(gameMap);
        }

        setEmpty(true);
        setStyle(newStyle);
        this.prize = null;
    }

    @Override
    public Prize getPrize(){
        return prize;
    }
}
