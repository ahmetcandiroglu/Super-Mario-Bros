package model.enemy;

import model.GameObject;

import java.awt.*;

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

     // incomplete; update obstruction condition
    public void move(Direction direction) {
    	if (isMovable()) {
    		
    		boolean obstruction = false;
    		Point location = getLocation();
    		Point newLocation;
    		
    		while (!obstruction) {
    			if( this.direction == Direction.LEFT )
        		{
    				newLocation = new Point(location.x - 1, location.y);
    				setLocation(newLocation);
        		}
    			if( this.direction == Direction.RIGHT )
        		{
    				newLocation = new Point(location.x + 1, location.y);
    				setLocation(newLocation);
        		}
    		}
    		
    	}
    }

    @Override
    public void draw() {

    }
}
