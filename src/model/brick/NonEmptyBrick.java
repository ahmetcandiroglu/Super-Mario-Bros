package model.brick;

import model.prize.Prize;

import java.util.LinkedList;

public class NonEmptyBrick {

    private boolean visible;

    private LinkedList<Prize> prize;

    public Prize revealPrize(){
        return null;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
