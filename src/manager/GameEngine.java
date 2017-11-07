package manager;

import model.Fireball;
import model.Map;
import model.brick.Brick;
import model.enemy.Enemy;
import model.hero.Mario;
import model.prize.BoostItem;
import model.prize.Coin;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameEngine extends JFrame{

	private Map gameMap;

	private MapCreator mapCreator;

	private InputManager inputManager;

	private UIManager uiManager;

	private GameStatus gameStatus;

	private GameEngine(String title){
		super(title);

		init();

		addKeyListener(inputManager);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		setLayout(new BorderLayout());
		add(uiManager, BorderLayout.CENTER);
        pack();

        setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		System.out.println("Game engine initialized..");
	}

	private void init(){
		uiManager = new UIManager(1280, 720);

		mapCreator = new MapCreator();
        gameMap = mapCreator.createMap(4, 400);

        inputManager = new InputManager(this, gameMap.getTimeLimit());
        gameStatus = GameStatus.START_SCREEN;
        drawMap(gameMap, gameStatus);
	}

	private boolean isTimeUp(){
		return inputManager.getRemainingTime() == 0;
	}

	private boolean isDead(){
		return gameMap.isDead();
	}

	private void gameOver() {
		inputManager.gameOver();
		uiManager.gameOver();
		System.out.println("Game over..");
	}

	void gameLoop(){
        if(isTimeUp()){
            System.out.println("time is up");
            gameOver();
		}
		else if(isDead()){
            System.out.println("dead");
            gameOver();
		}

		checkContact();
		//inputManager.updateTime();
		drawMap(gameMap, gameStatus);
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

	private void updateLocations(){
		//TODO: automate locations of enemies and boost items
	}

	private void drawMap(Map updatedMap, GameStatus gameStatus){

	    uiManager.drawMapComponents(updatedMap, gameStatus, inputManager.getRemainingTime());
	}

	void analyzeInput(ButtonAction action){
	    if(gameStatus == GameStatus.RUNNING){
            //gameMap.actOnMario(action);
            boolean isRight = action == ButtonAction.M_RIGHT;
            moveMario(isRight);
	    }
	}

    GameStatus getGameStatus() {
        return gameStatus;
    }

	void setGameStatus(GameStatus status){
	    gameStatus = status;
    }

	public static void main(String[] args) {
		GameEngine game = new GameEngine("Super Mario Bros.");

	}


	//test method
    void moveMario(boolean isRight) {
		Mario m = gameMap.getMario();
		Point p = m.getLocation();
		if(isRight)
			p.x = p.x + 4;
		else
			p.x = p.x - 4;

		m.setLocation(p);
		drawMap(gameMap, gameStatus);
	}
}
