package data;

import helper.Clock;
import helper.StateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;

import static helper.Artist.HEIGHT;
import static helper.Artist.TILE_SIZE;

public class Player {

    public static int Cash, Lives;
    private TileGrid tileGrid;
    private TileType[] tileTypes;
    private WaveManager waveManager;
    private List<Tower> towerList;
    private boolean leftMouseButtonDown, rightMouseButtonDown, holdingTower;
    private Tower tempTower;

    public Player(TileGrid tileGrid, WaveManager waveManager) {
        this.tileGrid = tileGrid;
        tileTypes = new TileType[3];
        tileTypes[0] = TileType.Grass;
        tileTypes[1] = TileType.Dirt;
        tileTypes[2] = TileType.Water;
        this.waveManager = waveManager;
        this.towerList = new ArrayList<Tower>();
        this.leftMouseButtonDown = false;
        this.rightMouseButtonDown = false;
        this.holdingTower = false;
        this.tempTower = null;
        Cash = 0;
        Lives = 0;
    }

    public static boolean modifyCash (int amount) {
        if (Cash + amount >= 0) {
            Cash += amount;
            System.out.println(Cash);
            return true;
        }
        System.out.println(Cash);
        return false;
    }

    public static void modifyLives(int amount) {
        Lives += amount;
    }

    public void setup() {
        Cash = 70;
        Lives = 10;
    }

    public void setTile() {
        tileGrid.setTile((int) Math.floor(Mouse.getX() / TILE_SIZE), (int) Math.floor((HEIGHT - Mouse.getY() - 1) / TILE_SIZE), TileType.Dirt);
    }

    public void update() {
        // update holding tower
        if (holdingTower) {
            tempTower.setX(getMouseTile().getX());
            tempTower.setY(getMouseTile().getY());
            tempTower.draw();
        }

        // update all towers in the game
        for (Tower t : towerList) {
            t.update();
            t.draw();
            t.updateEnemyList(waveManager.getCurrentWave().getEnemyList());
        }

        // handle mouse input
        if (Mouse.isButtonDown(0) && !leftMouseButtonDown) {
            placeTower();
        }
        if (Mouse.isButtonDown(1) && !rightMouseButtonDown) {
            System.out.println("Right clicked");
        }

        leftMouseButtonDown = Mouse.isButtonDown(0);
        rightMouseButtonDown = Mouse.isButtonDown(1);

        // handle keyboard input
        while (Keyboard.next()) {
            if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()) {
                Clock.changeMultiplier(-0.2f);
            }
        }
    }

    private void placeTower() {
        Tile currentTile = getMouseTile();
        if (holdingTower) {
            if (!currentTile.getOccupied() && modifyCash(-tempTower.getCost())) {
                towerList.add(tempTower);
                currentTile.setOccupied(true);
                holdingTower = false;
                tempTower = null;
            }
        }
    }

    public void pickTower (Tower t) {
        tempTower = t;
        holdingTower = true;
    }

    private Tile getMouseTile () {
        return tileGrid.getTile(Mouse.getX() / TILE_SIZE, (HEIGHT - Mouse.getY() - 1) / TILE_SIZE);
    }
}
