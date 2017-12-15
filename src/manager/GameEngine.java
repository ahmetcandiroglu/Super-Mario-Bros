package manager;

import model.Fireball;
import model.brick.Brick;
import model.enemy.Enemy;
import model.hero.Mario;
import model.prize.BoostItem;
import model.prize.Prize;
import view.ImageLoader;
import view.StartScreenSelection;
import view.UIManager;
import model.Map;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class GameEngine implements Runnable {

    private final static int WIDTH = 1268, HEIGHT = 708;

    private Map gameMap;
    private UIManager uiManager;
    private SoundManager soundManager;
    private GameStatus gameStatus;
    private boolean isRunning;
    private Camera camera;
    private ImageLoader imageLoader;
    private Thread thread;
    private StartScreenSelection startScreenSelection = StartScreenSelection.START_GAME;
    private int selectedMap = 0;

    private GameEngine() {
        init();
    }

    private void init() {
        imageLoader = new ImageLoader();
        InputManager inputManager = new InputManager(this);
        gameStatus = GameStatus.START_SCREEN;
        camera = new Camera();
        uiManager = new UIManager(this, WIDTH, HEIGHT);
        soundManager = new SoundManager();

        JFrame frame = new JFrame("Super Mario Bros.");
        frame.add(uiManager);
        frame.addKeyListener(inputManager);
        frame.addMouseListener(inputManager);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        start();
    }

    private synchronized void start() {
        if (isRunning)
            return;

        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    private void reset(){
        camera = new Camera();
        setGameStatus(GameStatus.START_SCREEN);
    }

    public void selectMapViaMouse() {
        String path = uiManager.selectMapViaMouse(uiManager.getMousePosition());
        if (path != null) {
            createMap(path);
        }
    }

    public void selectMapViaKeyboard(){
        String path = uiManager.selectMapViaKeyboard(selectedMap);
        if (path != null) {
            createMap(path);
        }
    }

    public void changeSelectedMap(boolean up){
        selectedMap = uiManager.changeSelectedMap(selectedMap, up);
    }

    private void createMap(String path) {
        MapCreator mapCreator = new MapCreator(imageLoader);
        gameMap = mapCreator.createMap("/maps/" + path, 400);
        setGameStatus(GameStatus.RUNNING);
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();

        while (isRunning && !thread.isInterrupted()) {

            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                if (gameStatus == GameStatus.RUNNING) {
                    gameLoop();
                }
                delta--;
            }
            render();

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
            }
        }
    }

    private void render() {
        uiManager.repaint();
    }

    private void gameLoop() {
        updateLocations();
        checkCollisions();
        updateCamera();

        if (isGameOver()) {
            setGameStatus(GameStatus.GAME_OVER);
        }

        int missionPassed = passMission();
        if(missionPassed > -1){
            gameMap.getMario().acquirePoints(missionPassed);
            setGameStatus(GameStatus.MISSION_PASSED);
        }
    }

    private void updateCamera() {
        double marioVelocityX = gameMap.getMario().getVelX();
        double shiftAmount = 0;

        if (marioVelocityX > 0 && gameMap.getMario().getX() - 600 > camera.getX()) {
            shiftAmount = marioVelocityX;
        }

        camera.moveCam(shiftAmount, 0);
    }

    private void updateLocations() {
        gameMap.updateLocations();
    }

    private void checkCollisions() {
        Mario mario = gameMap.getMario();
        ArrayList<Brick> bricks = gameMap.getAllBricks();
        ArrayList<Enemy> enemies = gameMap.getEnemies();
        ArrayList<Prize> revealedPrizes = gameMap.getRevealedPrizes();
        ArrayList<Fireball> fireballs = gameMap.getFireballs();

        checkBottomCollisions(mario, bricks, enemies);
        checkTopCollisions(mario, bricks, enemies);
        checkRightCollisions(mario, bricks, enemies);
        checkLeftCollisions(mario, bricks, enemies);
        checkEnemyCollisions(bricks, enemies);
        checkFireballContact(mario, fireballs, enemies);
        checkPrizeContact(mario, revealedPrizes);
        checkPrizeCollision(revealedPrizes, bricks);
    }

    private void checkBottomCollisions(Mario mario, ArrayList<Brick> bricks, ArrayList<Enemy> enemies) {
        Rectangle marioBottomBounds = mario.getBottomBounds();

        if (!mario.isJumping())
            mario.setFalling(true);

        for (Brick brick : bricks) {
            Rectangle brickTopBounds = brick.getTopBounds();
            if (marioBottomBounds.intersects(brickTopBounds)) {
                mario.setY(brick.getY() - mario.getDimension().height + 1);
                mario.setFalling(false);
                mario.setVelY(0);
            }
        }

        for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext(); ) {
            Enemy enemy = iterator.next();
            Rectangle enemyTopBounds = enemy.getTopBounds();
            if (marioBottomBounds.intersects(enemyTopBounds)) {
                mario.acquirePoints(100);
                iterator.remove();
                soundManager.playStomp();
            }
        }

        if (mario.getY() + mario.getDimension().height >= gameMap.getBottomBorder()) {
            mario.setY(gameMap.getBottomBorder() - mario.getDimension().height);
            mario.setFalling(false);
            mario.setVelY(0);
        }
    }

    private void checkTopCollisions(Mario mario, ArrayList<Brick> bricks, ArrayList<Enemy> enemies) {
        Rectangle marioTopBounds = mario.getTopBounds();

        for (Brick brick : bricks) {
            Rectangle brickBottomBounds = brick.getBottomBounds();
            if (marioTopBounds.intersects(brickBottomBounds)) {
                mario.setVelY(0);
                mario.setY(brick.getY() + brick.getDimension().height);
                brick.reveal(gameMap);
            }
        }
    }

    private void checkRightCollisions(Mario mario, ArrayList<Brick> bricks, ArrayList<Enemy> enemies) {
        Rectangle marioRightBounds = mario.getRightBounds();

        for (Brick brick : bricks) {
            Rectangle brickLeftBounds = brick.getLeftBounds();
            if (marioRightBounds.intersects(brickLeftBounds)) {
                mario.setVelX(0);
                mario.setX(brick.getX() - mario.getDimension().width);
            }
        }

        for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext(); ) {
            Enemy enemy = iterator.next();
            Rectangle enemyLeftBounds = enemy.getLeftBounds();
            if (marioRightBounds.intersects(enemyLeftBounds)) {
                mario.onTouchEnemy(imageLoader, this);
                iterator.remove();
                shakeCamera();
            }
        }
    }

    private void checkLeftCollisions(Mario mario, ArrayList<Brick> bricks, ArrayList<Enemy> enemies) {
        Rectangle marioLeftBounds = mario.getLeftBounds();

        for (Brick brick : bricks) {
            Rectangle brickRightBounds = brick.getRightBounds();
            if (marioLeftBounds.intersects(brickRightBounds)) {
                mario.setVelX(0);
                mario.setX(brick.getX() + brick.getDimension().width);
            }
        }

        for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext(); ) {
            Enemy enemy = iterator.next();
            Rectangle enemyRightBounds = enemy.getRightBounds();
            if (marioLeftBounds.intersects(enemyRightBounds)) {
                mario.onTouchEnemy(imageLoader, this);
                iterator.remove();
                shakeCamera();
            }
        }

        if (mario.getX() <= camera.getX() && mario.getVelX() < 0) {
            mario.setVelX(0);
            mario.setX(camera.getX());
        }
    }

    private void checkEnemyCollisions(ArrayList<Brick> bricks, ArrayList<Enemy> enemies) {

        for (Enemy enemy : enemies) {
            boolean standsOnBrick = false;

            for (Brick brick : bricks) {
                Rectangle enemyBounds = enemy.getLeftBounds();
                Rectangle brickBounds = brick.getRightBounds();

                Rectangle enemyBottomBounds = enemy.getBottomBounds();
                Rectangle brickTopBounds = brick.getTopBounds();

                if (enemy.getVelX() > 0) {
                    enemyBounds = enemy.getRightBounds();
                    brickBounds = brick.getLeftBounds();
                }

                if (enemyBounds.intersects(brickBounds)) {
                    enemy.setVelX(-enemy.getVelX());
                }

                if (enemyBottomBounds.intersects(brickTopBounds)){
                    enemy.setFalling(false);
                    enemy.setVelY(0);
                    enemy.setY(brick.getY()-enemy.getDimension().height);
                    standsOnBrick = true;
                }
            }

            if(enemy.getY() + enemy.getDimension().height > gameMap.getBottomBorder()){
                enemy.setFalling(false);
                enemy.setVelY(0);
                enemy.setY(gameMap.getBottomBorder()-enemy.getDimension().height);
            }

            if (!standsOnBrick && enemy.getY() < gameMap.getBottomBorder()){
                enemy.setFalling(true);
            }
        }
    }

    private void checkPrizeCollision(ArrayList<Prize> prizes, ArrayList<Brick> bricks) {
        for (Prize prize : prizes) {
            if (prize instanceof BoostItem) {
                BoostItem boost = (BoostItem) prize;
                Rectangle prizeBottomBounds = boost.getBottomBounds();
                Rectangle prizeRightBounds = boost.getRightBounds();
                Rectangle prizeLeftBounds = boost.getLeftBounds();
                boost.setFalling(true);

                for (Brick brick : bricks) {
                    Rectangle brickBounds;

                    if (boost.isFalling()) {
                        brickBounds = brick.getTopBounds();

                        if (brickBounds.intersects(prizeBottomBounds)) {
                            boost.setFalling(false);
                            boost.setVelY(0);
                            boost.setY(brick.getY() - boost.getDimension().height + 1);
                            if (boost.getVelX() == 0)
                                boost.setVelX(2);
                        }
                    }

                    if (boost.getVelX() > 0) {
                        brickBounds = brick.getLeftBounds();

                        if (brickBounds.intersects(prizeRightBounds)) {
                            boost.setVelX(-boost.getVelX());
                        }
                    } else if (boost.getVelX() < 0) {
                        brickBounds = brick.getRightBounds();

                        if (brickBounds.intersects(prizeLeftBounds)) {
                            boost.setVelX(-boost.getVelX());
                        }
                    }
                }

                if (boost.getY() + boost.getDimension().height > gameMap.getBottomBorder()) {
                    boost.setFalling(false);
                    boost.setVelY(0);
                    boost.setY(gameMap.getBottomBorder() - boost.getDimension().height);
                    if (boost.getVelX() == 0)
                        boost.setVelX(2);
                }

            }
        }
    }

    private void checkPrizeContact(Mario mario, ArrayList<Prize> prizes) {
        Rectangle marioBounds = mario.getBounds();

        for (Iterator<Prize> prizeIterator = prizes.iterator(); prizeIterator.hasNext(); ) {
            Prize prize = prizeIterator.next();
            if (prize instanceof BoostItem) {
                Rectangle prizeBounds = ((BoostItem) prize).getBounds();
                if (prizeBounds.intersects(marioBounds)) {
                    prize.playOnTouch(this);
                    ((BoostItem) prize).onTouch(gameMap);
                    prizeIterator.remove();
                }
            }
        }

    }

    private void checkFireballContact(Mario mario, ArrayList<Fireball> fireballs, ArrayList<Enemy> enemies) {

        for (Iterator<Fireball> fireballIterator = fireballs.iterator(); fireballIterator.hasNext(); ) {
            Fireball fireball = fireballIterator.next();
            Rectangle fireballBounds = fireball.getBounds();

            for (Iterator<Enemy> enemyIterator = enemies.iterator(); enemyIterator.hasNext(); ) {
                Enemy enemy = enemyIterator.next();
                Rectangle enemyBounds = enemy.getBounds();

                if (fireballBounds.intersects(enemyBounds)) {
                    mario.acquirePoints(100);
                    fireballIterator.remove();
                    enemyIterator.remove();
                }
            }
        }
    }

    public void receiveInput(ButtonAction input) {

        if (gameStatus == GameStatus.START_SCREEN) {
            if (input == ButtonAction.SELECT && startScreenSelection == StartScreenSelection.START_GAME) {
                startGame();
            } else if (input == ButtonAction.SELECT && startScreenSelection == StartScreenSelection.VIEW_ABOUT) {
                setGameStatus(GameStatus.ABOUT_SCREEN);
            } else if (input == ButtonAction.SELECT && startScreenSelection == StartScreenSelection.VIEW_HELP) {
                setGameStatus(GameStatus.HELP_SCREEN);
            } else if (input == ButtonAction.GO_UP) {
                selectOption(true);
            } else if (input == ButtonAction.GO_DOWN) {
                selectOption(false);
            }
        }
        else if(gameStatus == GameStatus.MAP_SELECTION){
            if(input == ButtonAction.SELECT){
                selectMapViaKeyboard();
            }
            else if(input == ButtonAction.GO_UP){
                changeSelectedMap(true);
            }
            else if(input == ButtonAction.GO_DOWN){
                changeSelectedMap(false);
            }
        } else if (gameStatus == GameStatus.RUNNING) {
            Mario mario = gameMap.getMario();
            if (input == ButtonAction.JUMP) {
                mario.jump(this);
            } else if (input == ButtonAction.M_RIGHT) {
                mario.move(true, camera);
            } else if (input == ButtonAction.M_LEFT) {
                mario.move(false, camera);
            } else if (input == ButtonAction.ACTION_COMPLETED) {
                mario.setVelX(0);
            } else if (input == ButtonAction.FIRE) {
                mario.fire(gameMap);
                soundManager.playFireball();
            } else if (input == ButtonAction.PAUSE_RESUME) {
                pauseGame();
            }
        } else if (gameStatus == GameStatus.PAUSED) {
            if (input == ButtonAction.PAUSE_RESUME) {
                pauseGame();
            }
        } else if(gameStatus == GameStatus.GAME_OVER && input == ButtonAction.GO_TO_START_SCREEN){
            reset();
        } else if(gameStatus == GameStatus.MISSION_PASSED && input == ButtonAction.GO_TO_START_SCREEN){
            reset();
        }

        if(input == ButtonAction.GO_TO_START_SCREEN){
            setGameStatus(GameStatus.START_SCREEN);
        }
    }

    private void selectOption(boolean selectUp) {
        startScreenSelection = startScreenSelection.select(selectUp);
    }

    private void startGame() {
        if (gameStatus != GameStatus.GAME_OVER) {
            setGameStatus(GameStatus.MAP_SELECTION);
        }
    }

    private void pauseGame() {
        if (gameStatus == GameStatus.RUNNING) {
            setGameStatus(GameStatus.PAUSED);
        } else if (gameStatus == GameStatus.PAUSED) {
            setGameStatus(GameStatus.RUNNING);
        }
    }

    private void shakeCamera(){
        camera.shakeCamera();
    }

    private boolean isGameOver() {
        if(gameStatus == GameStatus.RUNNING)
            return gameMap.getMario().getRemainingLives() == 0;
        return false;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public StartScreenSelection getStartScreenSelection() {
        return startScreenSelection;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;

        if(gameStatus == GameStatus.RUNNING){
            soundManager.playBackground();
        } else if(gameStatus == GameStatus.GAME_OVER){
            soundManager.playGameOver();
        }
    }

    public int getScore() {
        return gameMap.getMario().getPoints();
    }

    public int getRemainingLives() {
        return gameMap.getMario().getRemainingLives();
    }

    public int getCoins() {
        return gameMap.getMario().getCoins();
    }

    public static void main(String... args) {
        new GameEngine();
    }

    public int getSelectedMap() {
        return selectedMap;
    }

    public void drawMap(Graphics2D g2) {
        gameMap.drawMap(g2);
    }

    public Point getCameraLocation() {
        return new Point((int)camera.getX(), (int)camera.getY());
    }

    private int passMission(){
        if(gameMap.getMario().getX() >= gameMap.getEndPoint().x){
            int height = (int)gameMap.getMario().getY();
            return height * 2;
        }
        else
            return -1;
    }

    public void playCoin() {
        soundManager.playCoin();
    }

    public void playOneUp() {
        soundManager.playOneUp();
    }

    public void playSuperMushroom() {
        soundManager.playSuperMushroom();
    }

    public void playMarioDies() {
        soundManager.playMarioDies();
    }

    public void playJump() {
        soundManager.playJump();
    }
}
