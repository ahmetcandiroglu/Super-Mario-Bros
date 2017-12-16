package manager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class InputManager implements KeyListener, MouseListener{

    private GameEngine engine;

    InputManager(GameEngine engine) {
        this.engine = engine; }

    @Override
    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();
        GameStatus status = engine.getGameStatus();
        ButtonAction currentAction = ButtonAction.NO_ACTION;

        if (keyCode == KeyEvent.VK_UP) {
            if(status == GameStatus.START_SCREEN || status == GameStatus.MAP_SELECTION)
                currentAction = ButtonAction.GO_UP;
            else
                currentAction = ButtonAction.JUMP;
        }
        else if(keyCode == KeyEvent.VK_DOWN){
            if(status == GameStatus.START_SCREEN || status == GameStatus.MAP_SELECTION)
                currentAction = ButtonAction.GO_DOWN;
        }
        else if (keyCode == KeyEvent.VK_RIGHT) {
            currentAction = ButtonAction.M_RIGHT;
        }
        else if (keyCode == KeyEvent.VK_LEFT) {
            currentAction = ButtonAction.M_LEFT;
        }
        else if (keyCode == KeyEvent.VK_ENTER) {
            currentAction = ButtonAction.SELECT;
        }
        else if (keyCode == KeyEvent.VK_ESCAPE) {
            if(status == GameStatus.RUNNING || status == GameStatus.PAUSED )
                currentAction = ButtonAction.PAUSE_RESUME;
            else
                currentAction = ButtonAction.GO_TO_START_SCREEN;

        }
        else if (keyCode == KeyEvent.VK_SPACE){
            currentAction = ButtonAction.FIRE;
        }


        notifyInput(currentAction);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(engine.getGameStatus() == GameStatus.MAP_SELECTION){
            engine.selectMapViaMouse();
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.VK_RIGHT || event.getKeyCode() == KeyEvent.VK_LEFT)
            notifyInput(ButtonAction.ACTION_COMPLETED);
    }

    private void notifyInput(ButtonAction action) {
        if(action != ButtonAction.NO_ACTION)
            engine.receiveInput(action);
    }

    @Override
    public void keyTyped(KeyEvent arg0) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
