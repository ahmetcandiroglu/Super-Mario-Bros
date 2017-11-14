package model.prize;

import model.Map;

public interface Prize {

    int getPoint();

    void reveal(Map gameMap);
}
