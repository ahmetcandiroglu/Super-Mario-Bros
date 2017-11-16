package model.prize;

import model.Map;
import model.hero.Mario;
import model.hero.MarioForm;
import view.Animation;
import view.ImageLoader;

import java.awt.image.BufferedImage;

public class FireFlower extends BoostItem {

    public FireFlower(double x, double y, BufferedImage style) {
        super(x, y, style);
        setPoint(150);
    }

    @Override
    public void onTouch(Map gameMap) {
        Mario mario = gameMap.getMario();
        mario.acquirePoints(getPoint());

        ImageLoader imageLoader = new ImageLoader();

        if(!mario.getMarioForm().isFire()){
            BufferedImage[] leftFrames = imageLoader.getLeftFrames(MarioForm.FIRE);
            BufferedImage[] rightFrames = imageLoader.getRightFrames(MarioForm.FIRE);

            Animation animation = new Animation(leftFrames, rightFrames);
            MarioForm newForm = new MarioForm(animation, true, true);
            mario.setMarioForm(newForm);
            mario.setDimension(48, 96);
        }
    }

    @Override
    public void updateLocation(){}

}
