package model.brick;

import model.GameObject;

public class Brick extends GameObject{

    private BrickType type;

    public BrickType getType() {
        return type;
    }

    public void setType(BrickType type) {
        this.type = type;
    }

    @Override
    public void draw() {

    }
}
