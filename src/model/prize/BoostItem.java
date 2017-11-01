package model.prize;

import model.GameObject;

public class BoostItem extends GameObject{

    private boolean revealed = false;

    private BoostType type;

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public BoostType getType() {
        return type;
    }

    public void setType(BoostType type) {
        this.type = type;
    }

    @Override
    public void draw() {

    }
}
