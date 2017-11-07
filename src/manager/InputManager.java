package manager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager implements KeyListener, ActionListener {

	private final int FPS = 1;

	private GameEngine engine;

	private Timer timer;

	private double remainingTime;


	InputManager(GameEngine engine, double remainingTime){
		this.engine = engine;
		this.remainingTime = remainingTime;
		timer = new Timer( 1000/FPS, this);
	}

	@Override
	public void keyPressed(KeyEvent event) {
	    ButtonAction action = ButtonAction.NO_ACTION;

		if(event.getKeyCode() == KeyEvent.VK_UP){
			action = ButtonAction.JUMP;
		}
		else if(event.getKeyCode() == KeyEvent.VK_RIGHT){
            action = ButtonAction.M_RIGHT;
		}
		else if(event.getKeyCode() == KeyEvent.VK_LEFT){
            action = ButtonAction.M_LEFT;
		}
		else if(event.getKeyCode() == KeyEvent.VK_ENTER){
            action = ButtonAction.START;
		}
		else if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
            action = ButtonAction.PAUSE_RESUME;
		}

        System.out.println("Given input: " + action);
        System.out.println("Engine status : " + engine.getGameStatus());

        analyzeInput(action);
        System.out.println("Engine status : " + engine.getGameStatus());
    }

	@Override
	public void actionPerformed(ActionEvent e) {
        updateTime();
        engine.gameLoop();
	}

	private void analyzeInput(ButtonAction action){
        if(action == ButtonAction.START){
            startGame();
        }
        else if(action == ButtonAction.PAUSE_RESUME){
		    pauseOrResumeGame();
        }
        else if(action != ButtonAction.NO_ACTION){
            engine.analyzeInput(action);
        }
	}

	private void startGame(){
        if(engine.getGameStatus() == GameStatus.START_SCREEN) {
            engine.setGameStatus(GameStatus.RUNNING);
            timer.start();
        }
	}

	private void pauseOrResumeGame(){
	    if(engine.getGameStatus() == GameStatus.RUNNING){
            engine.setGameStatus(GameStatus.PAUSED);
            timer.stop();
        }
        else if(engine.getGameStatus() == GameStatus.PAUSED){
            engine.setGameStatus(GameStatus.RUNNING);
            timer.start();
        }
	}

	private void updateTime(){
        remainingTime = remainingTime - 1/(double)FPS;
    }

	double getRemainingTime() {
		return remainingTime;
	}

	void gameOver() {
		engine.setGameStatus(GameStatus.GAME_OVER);
		timer.stop();
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
