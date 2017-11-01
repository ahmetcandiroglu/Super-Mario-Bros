package model.enemy;

import model.GameObject;

public class Enemy extends GameObject{

    private boolean movable;

    private Direction direction;

    private EnemyType type;

    public boolean isMovable() {
        return movable;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public EnemyType getType() {
        return type;
    }

    public void setType(EnemyType type) {
        this.type = type;
    }

    public void move(){}

    @Override
    public void draw() {

    }
}
