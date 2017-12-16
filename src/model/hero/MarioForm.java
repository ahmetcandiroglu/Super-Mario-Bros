package model.hero;

import view.Animation;
import view.ImageLoader;

import java.awt.image.BufferedImage;

public class MarioForm {

    public static final int SMALL = 0, SUPER = 1, FIRE = 2;

    private Animation animation;
    private boolean isSuper, isFire; //note: fire form has priority over super form
    private BufferedImage fireballStyle;

    public MarioForm(Animation animation, boolean isSuper, boolean isFire){
        this.animation = animation;
        this.isSuper = isSuper;
        this.isFire = isFire;

        ImageLoader imageLoader = new ImageLoader();
        BufferedImage fireball = imageLoader.loadImage("/sprite.png");
        fireballStyle = imageLoader.getSubImage(fireball, 3, 4, 24, 24);
    }

    public BufferedImage getCurrentStyle(boolean toRight, boolean movingInX, boolean movingInY){

        BufferedImage style;

        if(movingInY && toRight){
            style = animation.getRightFrames()[0];
        }
        else if(movingInY){
            style = animation.getLeftFrames()[0];
        }
        else if(movingInX){
            style = animation.animate(5, toRight);
        }
        else {
            if(toRight){
                style = animation.getRightFrames()[1];
            }
            else
                style = animation.getLeftFrames()[1];
        }

        return style;
    }

    public MarioForm onTouchEnemy(ImageLoader imageLoader) {
        BufferedImage[] leftFrames = imageLoader.getLeftFrames(0);
        BufferedImage[] rightFrames= imageLoader.getRightFrames(0);

        Animation newAnimation = new Animation(leftFrames, rightFrames);

        return new MarioForm(newAnimation, false, false);
    }

    public Fireball fire(boolean toRight, double x, double y) {
        if(isFire){
            return new Fireball(x, y + 48, fireballStyle, toRight);
        }
        return null;
    }

    public boolean isSuper() {
        return isSuper;
    }

    public void setSuper(boolean aSuper) {
        isSuper = aSuper;
    }

    public boolean isFire() {
        return isFire;
    }
}
