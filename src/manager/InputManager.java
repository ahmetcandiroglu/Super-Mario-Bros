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
            action = ButtonAction.JUMP;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            action = ButtonAction.M_RIGHT;
        } else if (keyCode == KeyEvent.VK_LEFT) {
            action = ButtonAction.M_LEFT;
        } else if (keyCode == KeyEvent.VK_ENTER) {
            action = ButtonAction.START;
        } else if (keyCode == KeyEvent.VK_ESCAPE) {
            action = ButtonAction.PAUSE_RESUME;
        } else if (keyCode == KeyEvent.VK_SPACE){
            action = ButtonAction.FIRE;
        }

        analyzeInput(action);
    }

    @Override
    public void keyReleased(KeyEvent event) {
        int keyCode = event.getKeyCode();
        analyzeInput(ButtonAction.ACTION_COMPLETED);
    }

    private void analyzeInput(ButtonAction action) {
        if (action == ButtonAction.START) {
            startGame();
        }
        else if (action == ButtonAction.PAUSE_RESUME) {
            pauseOrResumeGame();
        }
        else{
            engine.notifyInput(action);
        }
    }

    private void startGame() {
        engine.startGame();
    }

    private void pauseOrResumeGame() {
        engine.pauseGame();
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }
}
