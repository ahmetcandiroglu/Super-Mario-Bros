package model.prize;

import manager.GameEngine;
import model.Map;

public interface Prize {

    int getPoint();

    void reveal(Map gameMap);

    void playOnTouch(GameEngine engine);
}
