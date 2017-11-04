package manager;

import model.Fireball;
import model.Map;
import model.brick.Brick;
import model.enemy.Enemy;
import model.prize.BoostItem;
import model.prize.Coin;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameEngine extends JFrame{

	private final int FPS = 25;

	private double remainingTime;

	private Map gameMap;

	private InputManager inputManager;

	private static UIManager uiManager;

	private Timer timer;


	private GameEngine(String title){
		super(title);

		init();

		setContentPane(uiManager);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(true);
	}

	private void init(){
		remainingTime = 0;
		uiManager = new UIManager(720, 480);
		gameMap = new Map(4, 400*1000);
		timer = new Timer(1000/FPS, null);
	}

	private void startGame(){
		timer.start();
	}

	private void pauseGame(){
		timer.stop();
	}

	private void resumeGame(){
		timer.start();
	}

	private boolean isTimeUp(){
		return remainingTime <= gameMap.getTimeLimitInMicro();
	}

	private boolean isDead(){
		return gameMap.isDead();
	}

	private void gameOver() {
		timer.stop();
		uiManager.gameOver();
	}

	public void gameLoop(ButtonAction action){
		if(isTimeUp()){
			gameOver();
		}
		else if(isDead()){
			gameOver();
		}

		analyzeInput(action);
		checkContact();
		updateTime();
		drawMap(gameMap);
	}

	private void checkFireballContact(){
		ArrayList<Point> fireballLocations = gameMap.getFireballLocations();
		Dimension fireballDimension = Fireball.DIMENSION;

		ArrayList<Point> enemyLocations = gameMap.getEnemyLocations();
		ArrayList<Dimension> enemyDimensions = gameMap.getEnemyDimensions();

		for(int i = 0; i < enemyLocations.size(); i++){
			for (int j = 0; j < fireballLocations.size(); j++) {
				int a = fireballLocations.get(j).x;
				int b = a + fireballDimension.width;
				int c = fireballLocations.get(j).y;
				int d = c + fireballDimension.height;

				Point enemyLocation = enemyLocations.get(i);
				Dimension enemyDimension = enemyDimensions.get(i);

				boolean x_axis = (a < enemyLocation.x + enemyDimension.width && a > enemyLocation.x)
						|| (b < enemyLocation.x + enemyDimension.width && b > enemyLocation.x);

				boolean y_axis = (c < enemyLocation.y + enemyDimension.height && c > enemyLocation.y)
						|| (d < enemyLocation.y + enemyDimension.height && d > enemyLocation.y);

				if(x_axis || y_axis){
					gameMap.killEnemy(gameMap.getEnemyWithIndex(i));
					gameMap.removeFireballWithIndex(j);
				}
			}
		}
	}

	private void checkBoostItemContact() {
		Point marioLocation = gameMap.getMarioLocation();
		Dimension marioDimension = gameMap.getMarioDimensions();

		int a = marioLocation.x;
		int b = marioLocation.x + marioDimension.width;
		int c = marioLocation.y;
		int d = marioLocation.y + marioDimension.height;

		ArrayList<Point> boostItemLocations = gameMap.getBoostItemLocations();
		Dimension coinDimension = Coin.DIMENSION;

		for(int i = 0; i < boostItemLocations.size(); i++){
			Point boostItemLocation = boostItemLocations.get(i);

			boolean x_axis = (a < boostItemLocation.x + coinDimension.width && a > boostItemLocation.x)
					|| (b < boostItemLocation.x + coinDimension.width && b > boostItemLocation.x);

			boolean y_axis = (c < boostItemLocation.y + coinDimension.height && c > boostItemLocation.y)
					|| (d < boostItemLocation.y + coinDimension.height && d > boostItemLocation.y);

			if(x_axis || y_axis){
				acquireBoostItem(gameMap.getBoostItemWithIndex(i));
			}
		}
	}

	private void acquireBoostItem(BoostItem boost){
		gameMap.acquireBoostItem(boost);
	}

	private void checkEnemyContact(){
		Point marioLocation = gameMap.getMarioLocation();
		Dimension marioDimension = gameMap.getMarioDimensions();

		int a = marioLocation.x;
		int b = marioLocation.x + marioDimension.width;
		int d = marioLocation.y + marioDimension.height;

		ArrayList<Point> enemyLocations = gameMap.getEnemyLocations();
		ArrayList<Dimension> enemyDimensions = gameMap.getEnemyDimensions();

		for(int i = 0; i < enemyLocations.size(); i++){
			Point enemyLocation = enemyLocations.get(i);
			Dimension enemyDimension = enemyDimensions.get(i);

			boolean touched = (a < enemyLocation.x + enemyDimension.width && a > enemyLocation.x)
					|| (b < enemyLocation.x + enemyDimension.width && b > enemyLocation.x);

			boolean stomped = (d < enemyLocation.y + enemyDimension.height && d > enemyLocation.y);

			if(touched){
				touchedEnemy(gameMap.getEnemyWithIndex(i));
			}
			else if(stomped){
				stompOnEnemy(gameMap.getEnemyWithIndex(i));
			}
		}
	}

	private void touchedEnemy(Enemy enemy){
		gameMap.touchedEnemy(enemy);
	}

	private void stompOnEnemy(Enemy enemy){
		gameMap.killEnemy(enemy);
	}

	private void checkBrickContact(){
		Point marioLocation = gameMap.getMarioLocation();

		ArrayList<Point> brickLocations = gameMap.getBrickLocations();
		Dimension brickDimension = Brick.DIMENSION;

		for(int i = 0; i < brickLocations.size(); i++){
			Point brickLocation = brickLocations.get(i);

			boolean y_axis = (marioLocation.y < brickLocation.y + brickDimension.height
					&& marioLocation.y > brickLocation.y);

			if(y_axis){
				revealBrick(gameMap.getBrickWithIndex(i));
			}
		}
	}

	private void revealBrick(Brick brickToReveal){
		gameMap.revealBrick(brickToReveal);
	}

	private void checkCoinContact(){
		Point marioLocation = gameMap.getMarioLocation();
		Dimension marioDimension = gameMap.getMarioDimensions();

		int a = marioLocation.x;
		int b = marioLocation.x + marioDimension.width;
		int c = marioLocation.y;
		int d = marioLocation.y + marioDimension.height;

		ArrayList<Point> coinLocations = gameMap.getCoinLocations();
		Dimension coinDimension = Coin.DIMENSION;

		for(int i = 0; i < coinLocations.size(); i++){
			Point coinLocation = coinLocations.get(i);

			boolean x_axis = (a < coinLocation.x + coinDimension.width && a > coinLocation.x)
					|| (b < coinLocation.x + coinDimension.width && b > coinLocation.x);

			boolean y_axis = (c < coinLocation.y + coinDimension.height && c > coinLocation.y)
					|| (d < coinLocation.y + coinDimension.height && d > coinLocation.y);

			if(x_axis || y_axis){
				acquireCoin(gameMap.getCoinWithIndex(i));
			}
		}
	}

	private void acquireCoin(Coin coin){
		gameMap.acquireCoin(coin);
	}

	private void checkContact(){

		checkFireballContact();

		checkBoostItemContact();

		checkEnemyContact();

		checkBrickContact();

		checkCoinContact();
	}

	private void drawMap(Map updatedMap){
		uiManager.drawMapComponents(updatedMap);
	}

	private void updateTime(){
		remainingTime =- 1000/FPS;
	}

	private void analyzeInput(ButtonAction action){
		switch (action){
			case START: {
				startGame();
			}
			case PAUSE: {
				pauseGame();
			}
			case RESUME: {
				resumeGame();
			}
			case NO_ACTION: {

			}
			default: gameMap.actOnMario(action);
		}
	}

	public static void main(String[] args) {

		GameEngine game = new GameEngine("Super Mario Bros.");
	}
}
