package data;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static helper.Artist.TILE_SIZE;
import static helper.Clock.delta;

public class Wave {

    private float timeSinceLastSpawn, spawnTime;
    private Enemy[] enemyTypes;
    private List<Enemy> enemies;
    private int enemiesPerWave, enemiesSpawned;
    private boolean waveCompleted;

    public Wave(Enemy[] enemyTypes, float spawnTime, int enemiesPerWave) {
        this.spawnTime = spawnTime;
        this.enemyTypes = enemyTypes;
        this.timeSinceLastSpawn = 0;
        this.enemiesPerWave = enemiesPerWave;
        this.enemiesSpawned = 0;
        this.enemies = new CopyOnWriteArrayList<Enemy>();
        this.waveCompleted = false;
        spawn();
    }

    public void update() {
        boolean allEnemiesDead = true;
        if (enemiesSpawned < enemiesPerWave) {
            timeSinceLastSpawn += delta();
            if (timeSinceLastSpawn > spawnTime) {
                spawn();
                timeSinceLastSpawn = 0;
            }
        }

        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                allEnemiesDead = false;
                enemy.update();
                enemy.draw();
            } else {
                enemies.remove(enemy);
            }
        }
        if (allEnemiesDead) {
            waveCompleted = true;
        }
    }

    private void spawn() {
        enemies.add(new Enemy(enemyTypes[0].getTexture(), enemyTypes[0].getStartTile(), enemyTypes[0].getTileGrid(),
                TILE_SIZE, TILE_SIZE, enemyTypes[0].getSpeed(), enemyTypes[0].getHealth()));
//        enemies.add(new Enemy(enemyTypes[1].getTexture(), enemyTypes[1].getStartTile(), enemyTypes[1].getTileGrid(),
//                TILE_SIZE, TILE_SIZE, enemyTypes[1].getSpeed(), enemyTypes[1].getHealth()));

        enemiesSpawned++;
    }

    public boolean isCompleted() {
        return waveCompleted;
    }

    public List<Enemy> getEnemyList() {
        return enemies;
    }
}
