package model.hero;

import model.GameObject;
import model.enemy.Direction;

public abstract class Hero extends GameObject{

    abstract void move(Direction direction);

    abstract void jump();
}
