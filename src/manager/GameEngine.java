package manager;

import model.brick.Brick;
import model.enemy.Enemy;
import model.hero.Mario;
import view.ImageLoader;
import view.UIManager;
import model.Map;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class GameEngine implements Runnable {

    private final static int WIDTH = 1268, HEIGHT = 708;

    private Map gameMap;
    private InputManager inputManager;
    private UIManager uiManager;
    private GameStatus gameStatus;
    private boolean isRunning;
    private Camera camera;
    private ImageLoader imageLoader;
    private Thread thread;

    private GameEngine() {
        init();
    }

    private synchronized void start(){
        if(isRunning)
            return;

        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    private void init() {
        imageLoader = new ImageLoader();
        MapCreator mapCreator = new MapCreator(imageLoader);
        gameMap = mapCreator.createMap("/map.png", 400);

        inputManager = new InputManager(this, gameMap.getTimeLimit());
        gameStatus = GameStatus.START_SCREEN;
        camera = new Camera();
        uiManager = new UIManager(this, WIDTH, HEIGHT);

        JFrame frame = new JFrame("Super Mario Bros.");
        frame.add(uiManager);
        frame.addKeyListener(inputManager);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        start();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;

        while(isRunning && !thread.isInterrupted()) {

            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames + " TICKS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    private void render() {
        uiManager.repaint();
    }

    private void tick() {
        updateCamera();
        updateLocations();
        checkCollisions();

        if(isGameOver())
            thread.interrupt();
    }

    private void updateCamera() {
        double marioVelocityX = gameMap.getMario().getVelX();

        if( marioVelocityX > 0 && gameMap.getMario().getX() - 200 > camera.getX()){
            camera.setX( camera.getX() + marioVelocityX);
        }
    }

    private void updateLocations() {
        gameMap.updateLocations();
    }

    private void checkCollisions(){
        Mario mario = gameMap.getMario();
        ArrayList<Brick> bricks = gameMap.getAllBricks();
        ArrayList<Enemy> enemies = gameMap.getEnemies();

        checkBottomCollisions(mario, bricks, enemies);
        checkTopCollisions(mario, bricks, enemies);
        checkRightCollisions(mario, bricks, enemies);
        checkLeftCollisions(mario, bricks, enemies);
        checkEnemyCollisions(bricks, enemies);
    }

    private void checkBottomCollisions(Mario mario, ArrayList<Brick> bricks, ArrayList<Enemy> enemies){
        Rectangle marioBottomBounds = mario.getBottomBounds();

        for(Brick brick: bricks){
            Rectangle brickTopBounds = brick.getTopBounds();
            if(marioBottomBounds.intersects(brickTopBounds)){
                mario.setFalling(false);
                mario.setVelY(0);
                mario.setY(brick.getY() - mario.getDimension().height);
            }
        }

        for(Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext();){
            Enemy enemy = iterator.next();
            Rectangle enemyTopBounds = enemy.getTopBounds();
            if(marioBottomBounds.intersects(enemyTopBounds)){
                mario.acquirePoints(100);
                iterator.remove();
            }
        }

        if(mario.getY() + mario.getDimension().height >= gameMap.getBottomBorder()){
            mario.setY(gameMap.getBottomBorder() - mario.getDimension().height);
            mario.setFalling(false);
            mario.setVelY(0);
        }
    }

    private void checkTopCollisions(Mario mario, ArrayList<Brick> bricks, ArrayList<Enemy> enemies) {
        Rectangle marioTopBounds = mario.getTopBounds();

        for(Brick brick: bricks){
            Rectangle brickBottomBounds = brick.getBottomBounds();
            if(marioTopBounds.intersects(brickBottomBounds)){
                mario.setVelY(0);
                mario.setY(brick.getY() + brick.getDimension().height);
                brick.reveal(gameMap);
            }
        }
    }

    private void checkRightCollisions(Mario mario, ArrayList<Brick> bricks, ArrayList<Enemy> enemies) {
        Rectangle marioRightBounds = mario.getRightBounds();

        for(Brick brick: bricks){
            Rectangle brickLeftBounds = brick.getLeftBounds();
            if(marioRightBounds.intersects(brickLeftBounds)){
                mario.setVelX(0);
                mario.setX(brick.getX() - mario.getDimension().width);
            }
        }

        for(Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext();){
            Enemy enemy = iterator.next();
            Rectangle enemyLeftBounds = enemy.getLeftBounds();
            if(marioRightBounds.intersects(enemyLeftBounds)){
                gameMap.setMario(mario.onTouchEnemy(imageLoader));
                iterator.remove();
            }
        }
    }

    private void checkLeftCollisions(Mario mario, ArrayList<Brick> bricks, ArrayList<Enemy> enemies) {
        Rectangle marioLeftBounds = mario.getLeftBounds();

        for(Brick brick: bricks){
            Rectangle brickRightBounds = brick.getRightBounds();
            if(marioLeftBounds.intersects(brickRightBounds)){
                mario.setVelX(0);
                mario.setX(brick.getX() + brick.getDimension().width);
            }
        }

        for(Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext();){
            Enemy enemy = iterator.next();
            Rectangle enemyRightBounds = enemy.getRightBounds();
            if(marioLeftBounds.intersects(enemyRightBounds)){
                gameMap.setMario(mario.onTouchEnemy(imageLoader));
                iterator.remove();
            }
        }

        if(mario.getX() <= camera.getX() && mario.getVelX() < 0){
            mario.setVelX(0);
            mario.setX(camera.getX());
        }
    }

    private void checkEnemyCollisions(ArrayList<Brick> bricks, ArrayList<Enemy> enemies){

        for(Enemy enemy: enemies){
            boolean standsOnBrick = false;

            for(Brick brick: bricks){
                Rectangle enemyBounds = enemy.getLeftBounds();
                Rectangle brickBounds = brick.getRightBounds();

                Rectangle enemyBottomBounds = enemy.getBottomBounds();
                Rectangle brickTopBounds = brick.getTopBounds();

                if(enemy.getVelX() > 0){
                    enemyBounds = enemy.getRightBounds();
                    brickBounds = brick.getLeftBounds();
                }

                if(enemyBounds.intersects(brickBounds)){
                    enemy.setVelX(-enemy.getVelX());
                }

                if(enemyBottomBounds.intersects(brickTopBounds))
                    standsOnBrick = true;
            }

            if(!standsOnBrick && enemy.getY() + 48 < gameMap.getBottomBorder())
                enemy.setVelX(-enemy.getVelX());
        }
    }

    public void notifyInput(ButtonAction input) {
        Mario mario = gameMap.getMario();

        if(input == ButtonAction.JUMP){
            mario.jump();
        }
        else{
            if(input == ButtonAction.M_RIGHT){
                mario.move(true, camera);
            }
            else if(input == ButtonAction.M_LEFT){
                mario.move(false, camera);
            }
            else if(input == ButtonAction.ACTION_COMPLETED){
                mario.setVelX(0);
            }

            if(!mario.isJumping())
                mario.setFalling(true);
        }
    }

    private boolean isGameOver(){
        return gameMap.getMario().getRemainingLives() == 0;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public Camera getCamera() {
        return camera;
    }

    public Map getGameMap() {
        return gameMap;
    }


    public static void main(String[] args) {

        new GameEngine();
    }

}
