package data;

import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;
import java.util.List;

import static helper.Artist.*;
import static helper.Clock.delta;

public class TowerCannon {

    private float x, y, timeSinceLastShot, firingSpeed, angle, range;
    private int width, height;
    private Texture baseTexture, cannonTexture;
    private Tile startTile;
    private List<Projectile> projectiles;
    private List<Enemy> enemies;
    private Enemy target;
    private boolean targeted;

    public TowerCannon(Texture baseTexture, Tile startTile, int damgage, int range, List<Enemy> enemies) {
        this.x = startTile.getX();
        this.y = startTile.getY();
        this.range = range;
        this.baseTexture = baseTexture;
        this.cannonTexture = quickLoad("cannonGun");
        this.startTile = startTile;
        this.width = (int) startTile.getWidth();
        this.height = (int) startTile.getHeight();
        this.firingSpeed = 3;
        this.timeSinceLastShot = 0;
        this.projectiles = new ArrayList<Projectile>();
        this.enemies = enemies;
        this.targeted = false;
//        this.target = acquireTarget();
//        this.angle = calculateAngle();
    }

    public void update () {
        if (!targeted) {
            target = acquireTarget();
        }
        if (target == null || target.isAlive() == false) {
            targeted = false;
        }
        timeSinceLastShot += delta();
        if (timeSinceLastShot > firingSpeed) {
            shoot();
        }
        for (Projectile p : projectiles) {
            p.update();
        }

        angle = calculateAngle();
        draw();
    }

    public void updateEnemyList (List<Enemy> newList) {
        enemies = newList;
    }

    private Enemy acquireTarget () {
        Enemy clostest = null;
        float clostestDistance = 10000;
        for (Enemy e : enemies) {
            if (isInRange(e) && findDistanceEnemy(e) < clostestDistance) {
                clostestDistance = findDistanceEnemy(e);
                clostest = e;
            }
        }
        if (clostest != null) {
            targeted = true;
        }

        return clostest;
    }

    private boolean isInRange (Enemy enemy) {
        float xDistance = Math.abs(enemy.getX() - x);
        float yDistance = Math.abs(enemy.getY() - x);
        if (xDistance < range && yDistance < range){
            return true;
        }
        return false;
    }

    private float findDistanceEnemy (Enemy enemy) {
        float xDistance = Math.abs(enemy.getX() - x);
        float yDistance = Math.abs(enemy.getY() - x);
        return xDistance +yDistance;
    }

    private float calculateAngle () {
        double angleTemp = Math.atan2(target.getY() - y, target.getX() - x);
        return (float) Math.toDegrees(angleTemp) - 90;
    }

    private void shoot() {
        timeSinceLastShot = 0;
//        projectiles.add(new ProjectileIceBall(quickLoad("bullet"), target, x + TILE_SIZE / 2 - TILE_SIZE / 4, y + TILE_SIZE / 2 - TILE_SIZE / 4, 32, 32, 900, 10));
    }

    private void draw() {
        drawQuadTex(baseTexture, x, y, width, height);
        drawQuadTexRot(cannonTexture, x, y, width, height, angle);
    }
}
