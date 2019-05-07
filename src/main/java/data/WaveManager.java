package data;

import java.util.Random;

import static helper.Leveler.loadMap;

public class WaveManager {

    private static TileGrid map = loadMap("newMap1");
    private float timeSinceLastWave, timeBeweenEnemies;
    private int waveNumber, enemiesPerWave;
    private float enemySpeed;
    private Enemy[] enemyTypes;
    private Wave currentWave;


    public WaveManager(Enemy[] enemyTypes, float timeBeweenEnemies, int enemiesPerWave) {
        this.enemyTypes = enemyTypes;
        this.timeBeweenEnemies = timeBeweenEnemies;
        this.enemiesPerWave = enemiesPerWave;
        this.enemySpeed = enemyTypes[0].getSpeed();
        this.timeSinceLastWave = 0;
        this.waveNumber = 0;
        this.currentWave = null;
        newWave();
    }

    public void update() {
        if (!currentWave.isCompleted()) {
            currentWave.update();
        } else {
            newWave();
        }
    }

    private void newWave() {
        this.enemiesPerWave++;
        enemySpeed += 15;
        int startTile = 0;
        Random random = new Random();
        if (random.nextBoolean()) {
            startTile = 9;
        }
        if (startTile == 0) {
            enemyTypes[0].setStartTile(map.getTile(0, 9));
        } else {
            enemyTypes[0].setStartTile(map.getTile(2, 0));
        }

        enemyTypes[0].setSpeed(enemySpeed);
        if (timeBeweenEnemies >= 0.25) {
            timeBeweenEnemies /= 2;
        }

        currentWave = new Wave(enemyTypes, timeBeweenEnemies, enemiesPerWave);
        waveNumber++;
        System.out.println("Beginning wave " + waveNumber);
    }

    public Wave getCurrentWave() {
        return currentWave;
    }

    public int getWaveNumber() {
        return waveNumber;
    }
}
