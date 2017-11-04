package manager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager implements KeyListener {

	private boolean keyPressed;
	private ButtonAction lastAction;

	InputManager(){
		keyPressed = false;
		lastAction = ButtonAction.NO_ACTION;
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if(event.getKeyCode() == KeyEvent.VK_UP){
			lastAction = ButtonAction.JUMP;
		}
		else if(event.getKeyCode() == KeyEvent.VK_RIGHT){
			lastAction = ButtonAction.M_RIGHT;
		}
		else if(event.getKeyCode() == KeyEvent.VK_LEFT){
			lastAction = ButtonAction.M_LEFT;
		}
		else if(event.getKeyCode() == KeyEvent.VK_ENTER){
			lastAction = ButtonAction.START;
		}
		else if(event.getKeyCode() == KeyEvent.VK_ESCAPE && lastAction != ButtonAction.PAUSE){
			lastAction = ButtonAction.PAUSE;
		}
		else if(event.getKeyCode() == KeyEvent.VK_ESCAPE && lastAction == ButtonAction.PAUSE){
			lastAction = ButtonAction.RESUME;
		}
		else{
			lastAction = ButtonAction.NO_ACTION;
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	ButtonAction getAction() {
		if(keyPressed){
			keyPressed = false;
			return lastAction;
		}
		else{
			return ButtonAction.NO_ACTION;
		}
	}
}
