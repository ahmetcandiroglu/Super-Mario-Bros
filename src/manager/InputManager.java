package manager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class InputManager implements KeyListener{

    private GameEngine engine;
    private double remainingTime;

    InputManager(GameEngine engine, double remainingTime) {
        this.engine = engine;
        this.remainingTime = remainingTime;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        ButtonAction action = ButtonAction.NO_ACTION;
        int keyCode = event.getKeyCode();

        if (keyCode == KeyEvent.VK_UP) {
            if(engine.getGameStatus() == GameStatus.START_SCREEN)
                action = ButtonAction.GO_UP;
            else
                action = ButtonAction.JUMP;
        } else if(keyCode == KeyEvent.VK_DOWN){
            if(engine.getGameStatus() == GameStatus.START_SCREEN)
                action = ButtonAction.GO_DOWN;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            action = ButtonAction.M_RIGHT;
        } else if (keyCode == KeyEvent.VK_LEFT) {
            action = ButtonAction.M_LEFT;
        } else if (keyCode == KeyEvent.VK_ENTER) {
            action = ButtonAction.SELECT;
        } else if (keyCode == KeyEvent.VK_ESCAPE) {
            if(engine.getGameStatus() == GameStatus.RUNNING || engine.getGameStatus() == GameStatus.PAUSED )
                action = ButtonAction.PAUSE_RESUME;
            else
                action = ButtonAction.GO_TO_START_SCREEN;

        } else if (keyCode == KeyEvent.VK_SPACE){
            action = ButtonAction.FIRE;
        }

        notifyInput(action);
    }

    @Override
    public void keyReleased(KeyEvent event) {
        int keyCode = event.getKeyCode();
        notifyInput(ButtonAction.ACTION_COMPLETED);
    }

    private void notifyInput(ButtonAction action) {
        if(action != ButtonAction.NO_ACTION)
            engine.receiveInput(action);
    }
    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }
}
