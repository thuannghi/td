package data;

import UI.UI;
import UI.UI.Menu;
import helper.StateManager;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import static helper.Artist.drawQuadTex;
import static helper.Artist.quickLoad;

public class Game {

    private TileGrid grid;
    private Player player;
    private WaveManager waveManager;
    private UI gameUI;
    private Menu towerPickerMenu;
    private Texture menuBackground;
    private Enemy[] enemyTypes;

    private TowerCannonBlue towerCannonBlue;

    public Game(TileGrid grid) {
        this.grid = grid;
/*        waveManager = new WaveManager(new Enemy(quickLoad("enemy_floating"), grid.getTile(2, 0), grid, TILE_SIZE, TILE_SIZE, 70, 25),
                2, 2)*/
        enemyTypes = new Enemy[2];

        enemyTypes[0] = new EnemyAlien(0, 9, grid);
        enemyTypes[1] = new EnemyUFO(2, 0, grid);
        waveManager = new WaveManager(enemyTypes, 4, 1);
        player = new Player(grid, waveManager);
        player.setup();
        this.menuBackground = quickLoad("menu_background2");
        setupUI();
    }

    private void setupUI() {
        gameUI = new UI();
        gameUI.createMenu("TowerPicker", 1280, 100, 192, 960, 2, 0);
        towerPickerMenu = gameUI.getMenu("TowerPicker");
        towerPickerMenu.quickAdd("BlueCannon", "cannonbaseblue");
        towerPickerMenu.quickAdd("CannonIce", "mushroombase");
    }

    private void updateUI() {
        gameUI.draw();
        gameUI.drawString(1320, 700, "Lives: " + Player.Lives);
        gameUI.drawString(1320, 800, "Cash: " + Player.Cash);
        gameUI.drawString(1340, 600, "Wave: " + waveManager.getWaveNumber());
        gameUI.drawString(0, 0, "Frames in last second: " + StateManager.framesInLastSecond);

        if (Mouse.next()) {
            boolean mouseClicked = Mouse.isButtonDown(0);
            if (mouseClicked) {
                if (towerPickerMenu.isBouttonClicked("BlueCannon")) {
                    player.pickTower(new TowerCannonBlue(TowerType.TowerBlue, grid.getTile(0, 0), waveManager.getCurrentWave().getEnemyList()));
                }
                if (towerPickerMenu.isBouttonClicked("CannonIce")) {
                    player.pickTower(new TowerCannonIce(TowerType.TowerIce, grid.getTile(0, 0), waveManager.getCurrentWave().getEnemyList()));
                }
            }
        }
    }

    public void update() {
        drawQuadTex(menuBackground, 1280, 0, 192, 960);
        grid.draw();
        waveManager.update();
        player.update();
        updateUI();
    }
}
