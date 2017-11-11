package model;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {

    private double x, y;

    private double velX, velY;

    private Dimension dimension;

    private BufferedImage style;

    private double gravityAcc;

    private boolean falling, jumping;

    public GameObject(double x, double y, BufferedImage style){
        setLocation(x, y);
        setStyle(style);
        setDimension( getStyle().getWidth(), getStyle().getHeight());
        setGravityAcc(0.38);
    }

    public void draw(Graphics g) {
        BufferedImage style = getStyle();

        if(style != null){
            g.drawImage(style, (int)x, (int)y, null);
        }
    }

    public void updateLocation() {
        double updatedX = getX() + getVelX();
        double updatedY = getY() + getVelY();
        double updatedVelY = getVelY();

        if(isFalling()){
            updatedVelY = updatedVelY + getGravityAcc();
        }
        if(isJumping()){
            updatedVelY = updatedVelY - getGravityAcc();
        }

        setLocation(updatedX, updatedY);
        setVelY(updatedVelY);
    }

    public void setLocation(double x, double y) {
        setX(x);
        setY(y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Dimension getDimension(){
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public void setDimension(int width, int height){ this.dimension =  new Dimension(width, height); }

    public BufferedImage getStyle() {
        return style;
    }

    public void setStyle(BufferedImage style) {
        this.style = style;
    }

    public double getVelX() {
        return velX;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public double getGravityAcc() {
        return gravityAcc;
    }

    public void setGravityAcc(double gravityAcc) {
        this.gravityAcc = gravityAcc;
    }

    public Rectangle getTopBounds(){
        return new Rectangle((int)x, (int)y, dimension.width, dimension.height/2);
    }

    public Rectangle getBottomBounds(){
        return new Rectangle((int)x, (int)y + dimension.height/2, dimension.width, dimension.height/2);
    }

    public Rectangle getLeftBounds(){
        return new Rectangle((int)x, (int)y, dimension.width/4, dimension.height);
    }

    public Rectangle getRightBounds(){
        return new Rectangle((int)x + 3*dimension.width/4, (int)y, dimension.width/4, dimension.height);
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }
}
