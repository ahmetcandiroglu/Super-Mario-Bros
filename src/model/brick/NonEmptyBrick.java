package model.brick;

import model.prize.Prize;

import java.util.ArrayList;

public class NonEmptyBrick extends Brick{

    private boolean visible;

    private ArrayList<Prize> prizeList;

    public Prize revealPrize(){
        int size = prizeList.size();
        if(size > 0){
            if(size == 1) {
                setType(BrickType.ORD_UNBREAKABLE);
            }

            return prizeList.remove(size-1);
        }
        return null;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
