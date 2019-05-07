package data;

import helper.StateManager;
import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;
import java.util.List;

import static helper.Artist.*;
import static helper.Clock.delta;

public class Enemy implements Entity {

    private int width, height, currentCheckpoint;
    private float speed, x, y, health, startHealth, hiddenHealth;
    private Texture texture, healthBackground, healthForeground, healthBorder;
    private Tile startTile;
    private boolean first, alive;
    private TileGrid tileGrid;
    private List<Checkpoint> checkpoints;
    private int[] directions;
    public Enemy(int tileX, int tileY, TileGrid grid) {
        this.texture = quickLoad("enemy_floating");
        this.healthBackground = quickLoad("Health_Background");
        this.healthForeground = quickLoad("Health_Foreground");
        this.healthBorder = quickLoad("Health_Border");
        this.startTile = grid.getTile(tileX, tileY);
        this.x = startTile.getX();
        this.y = startTile.getY();
        this.width = TILE_SIZE;
        this.height = TILE_SIZE;
        this.speed = 50;
        this.health = 50;
        this.hiddenHealth = health;
        this.startHealth = health;
        this.tileGrid = grid;
        this.checkpoints = new ArrayList<Checkpoint>();
        this.first = true;
        this.alive = true;
        this.directions = new int[2];
//        // X direction
        this.directions[0] = 0;
//        // Y direction
        this.directions[1] = 0;
        this.directions = findNextD(startTile);
        this.currentCheckpoint = 0;
        populateCheckpointList();
    }

    public Enemy(Texture texture, Tile startTile, TileGrid tileGrid, int width, int height, float speed, float health) {
        this.texture = texture;
        this.healthBackground = quickLoad("Health_Background");
        this.healthForeground = quickLoad("Health_Foreground");
        this.healthBorder = quickLoad("Health_Border");
        this.startTile = startTile;
        this.x = startTile.getX();
        this.y = startTile.getY();
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.health = health;
        this.hiddenHealth = health;
        this.startHealth = health;
        this.tileGrid = tileGrid;
        this.checkpoints = new ArrayList<Checkpoint>();
        this.first = true;
        this.alive = true;
        this.directions = new int[2];
//        // X direction
        this.directions[0] = 0;
//        // Y direction
        this.directions[1] = 0;
        this.directions = findNextD(startTile);
        this.currentCheckpoint = 0;
        populateCheckpointList();
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Tile getStartTile() {
        return startTile;
    }

    public void setStartTile(Tile startTile) {
        this.startTile = startTile;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void draw() {
        float healthPercentage = health / startHealth;
        drawQuadTex(texture, x, y, width, height);
        drawQuadTex(healthBackground, x, y - 16, width, 8);
        drawQuadTex(healthForeground, x, y - 16, TILE_SIZE * healthPercentage, 8);
        drawQuadTex(healthBorder, x, y - 16, width, 8);
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getHiddenHealth() {
        return hiddenHealth;
    }

    public void reduceHiddenHealth(float amount) {
        this.hiddenHealth -= amount;
    }

    public void update() {
        // check if it's the first time this class is updated, if so do nothing
        if (first) {
            first = false;
        } else {
            if (checkpointReached()) {
                // check if there are more checkpoints before moving on
                if (currentCheckpoint + 1 == checkpoints.size()) {
                    endOfMazeReached();
                } else {
                    currentCheckpoint++;
                }
            } else {
                // if not a checkpoint, continue in current direction
                x += delta() * checkpoints.get(currentCheckpoint).getxDirection() * speed;
                y += delta() * checkpoints.get(currentCheckpoint).getyDirection() * speed;
            }
//            x += delta() * directions[0];
//            y += delta() * directions[1];

        }
    }

    // run when last checkpoint is reached by enemy
    private void endOfMazeReached() {
        Player.modifyLives(-1);
        die();
        if (Player.Lives <= 0) {
            StateManager.setGameState(StateManager.GameState.MAINMENU);
        }
    }

    // take damage from external source
    public void damage(int amount) {
        health -= amount;
        if (health <= 0) {
            die();
            Player.modifyCash(10);
        }
    }

    private void die() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    private Checkpoint findNextC(Tile s, int[] dir) {
        Tile nextTile = null;
        Checkpoint c = null;

        // boolean to decide if next checkpoint is found
        boolean found = false;

        // integer to increment each loop
        int counter = 1;

        while (!found) {
            if (s.getXPlace() + dir[0] * counter == tileGrid.getTilesWide() ||
                    s.getYPlace() + dir[1] * counter == tileGrid.getTilesHigh() ||
                    s.getTileType() !=
                            tileGrid.getTile(s.getXPlace() + dir[0] * counter,
                                    s.getYPlace() + dir[1] * counter).getTileType()) {

                found = true;
                // move counter back 1 to find tile before new tiletype
                counter -= 1;
                nextTile = tileGrid.getTile(s.getXPlace() + dir[0] * counter, s.getYPlace() + dir[1] * counter);
            }
            counter++;
        }
        c = new Checkpoint(nextTile, dir[0], dir[1]);
        return c;
    }

    private boolean checkpointReached() {
        boolean reached = false;
        Tile t = checkpoints.get(currentCheckpoint).getTile();

        if (x > t.getX() - 3 &&
                x < t.getX() + 3 &&
                y > t.getY() - 3 &&
                y < t.getY() + 3) {
            reached = true;
            x = t.getX();
            y = t.getY();
        }

        return reached;
    }

    private void populateCheckpointList() {
        // add first checkpoint manually base on startTile
        checkpoints.add(findNextC(startTile, directions = findNextD(startTile)));
        int counter = 0;
        boolean count = true;
        while (count) {
            int[] currentD = findNextD(checkpoints.get(counter).getTile());
//            Check if a next direction/checkpoint exist, end after 20 checkpoint
            if (currentD[0] == 2 || counter == 20) {
                count = false;
            } else {
                checkpoints.add(findNextC(checkpoints.get(counter).getTile(),
                        directions = findNextD(checkpoints.get(counter).getTile())));
            }
            counter++;
        }
    }

    private int[] findNextD(Tile s) {
        int[] dir = new int[2];
        Tile u = tileGrid.getTile(s.getXPlace(), s.getYPlace() - 1);
        Tile r = tileGrid.getTile(s.getXPlace() + 1, s.getYPlace());
        Tile d = tileGrid.getTile(s.getXPlace(), s.getYPlace() + 1);
        Tile l = tileGrid.getTile(s.getXPlace() - 1, s.getYPlace());

        // check if current inhabited tiletype matches tiletype above, right, down or left
        if (s.getTileType() == u.getTileType() && directions[1] != 1) {
            dir[0] = 0;
            dir[1] = -1;
        } else if (s.getTileType() == r.getTileType() && directions[0] != -1) {
            dir[0] = 1;
            dir[1] = 0;
        } else if (s.getTileType() == d.getTileType() && directions[1] != -1) {
            dir[0] = 0;
            dir[1] = 1;
        } else if (s.getTileType() == l.getTileType() && directions[0] != 1) {
            dir[0] = -1;
            dir[1] = 0;
        } else {
            dir[0] = 2;
            dir[1] = 2;
        }

        return dir;
    }

    public TileGrid getTileGrid() {
        return tileGrid;
    }

    public void setTileGrid(TileGrid tileGrid) {
        this.tileGrid = tileGrid;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
