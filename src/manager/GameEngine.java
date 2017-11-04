package manager;

import model.Fireball;
import model.Map;
import model.brick.Brick;
import model.brick.NonEmptyBrick;
import model.enemy.Enemy;
import model.hero.MarioForm;
import model.prize.BoostItem;
import model.prize.BoostType;
import model.prize.Coin;
import model.prize.Prize;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameEngine extends JFrame{

	private final int FPS = 25;

	private double remainingTime;
	private int remainingLives;
	private int acquiredCoins;
	private int acquiredPoints;
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
		remainingLives = 3;
		acquiredCoins = 0;
		acquiredPoints = 0;
		uiManager = new UIManager(720, 480);
		gameMap = new Map();
		timer = new Timer(1000/FPS, null);
	}

	public void startGame(){
		init();
	}

	public boolean pauseGame(){
		if(timer.isRunning()){
			timer.stop();
			return true;
		}
		else{
			return false;
		}
	}

	public boolean resumeGame(){
		if(!timer.isRunning()){
			timer.start();
			return true;
		}
		else{
			return false;
		}
	}

	private boolean isTimeUp(){
		return remainingTime <= gameMap.getTimeLimitInMicro();
	}

	private boolean isDead(){
		return remainingLives == 0;
	}

	public void gameLoop(ButtonAction action){
		if(isTimeUp()){
			gameOver();
		}
		else if(isDead()){
			gameOver();
		}

		checkContact();

		//get action from input manager

		//act on mario

		updateTime();

		drawMap(gameMap);
	}

	private void gameOver() {
	}

	private void killEnemy(Enemy enemyToKill){
		gameMap.killEnemy(enemyToKill);
	}

	private Prize revealBrick(NonEmptyBrick brickToReveal){
		return brickToReveal.revealPrize();
	}

	private void acquireBoostItem(BoostItem boost){
		BoostType boostType = boost.getType();

		switch (boostType){
			case FIRE_FLOWER:{
				gameMap.addMarioForm(MarioForm.FIRE);
			}
			case ONEUP_MUSH:{
				remainingLives++;
			}
			case SUPER_MUSH:{
				gameMap.addMarioForm(MarioForm.SUPER);
			}
			case SUPER_STAR:{
				gameMap.addMarioForm(MarioForm.INVINCIBLE);
			}
		}

		acquirePoint(boost.getPoint());
	}

	private void acquireCoin(Coin coin){
		acquiredCoins++;
		acquirePoint(coin.getPoint());
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

	private BoostItem checkBoostItemContact() {
		return null;
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

	private void touchedEnemy(Enemy enemy){}

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
				if(gameMap.getBrickWithIndex(i) instanceof NonEmptyBrick){
					revealBrick((NonEmptyBrick) gameMap.getBrickWithIndex(i));
				}
			}
		}
	}

	private void checkContact(){

		checkFireballContact();

		checkBoostItemContact();

		checkEnemyContact();

		checkBrickContact();
	}

	private void drawMap(Map updatedMap){

	}

	private void updateTime(){
		remainingTime =- 1000/FPS;
	}

	private void actOnMario(ButtonAction action){}

	private void acquirePoint(int point){
		acquiredPoints =+ point;
	}

	public static void main(String[] args) {

		GameEngine game = new GameEngine("Super Mario Bros.");
	}
}
