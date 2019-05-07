package data;

import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;
import java.util.List;

import static helper.Artist.drawQuadTex;
import static helper.Artist.drawQuadTexRot;
import static helper.Clock.delta;

public abstract class Tower implements Entity {

    public List<Projectile> projectiles;
    public TowerType type;
    private float x, y, timeSinceLastShot, firingSpeed, angle;
    private int width, height, range, cost;
    public Enemy target;
    private Texture[] textures;
    private List<Enemy> enemies;
    private boolean targeted;

    public Tower(TowerType type, Tile startTile, List<Enemy> enemies) {
        this.textures = type.textures;
        this.x = startTile.getX();
        this.y = startTile.getY();
        this.width = startTile.getWidth();
        this.height = startTile.getHeight();
        this.range = type.range;
        this.cost = type.cost;
        this.enemies = enemies;
        this.targeted = false;
        this.timeSinceLastShot = 0f;
        this.projectiles = new ArrayList<Projectile>();
        this.firingSpeed = type.firingSpeed;
        this.angle = 0f;
        this.type = type;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void draw() {
        drawQuadTex(textures[0], x, y, width, height);
        if (textures.length > 1) {
            for (int i = 1; i < textures.length; i++) {
                drawQuadTexRot(textures[i], x, y, width, height, angle);
            }
        }
    }

    private Enemy acquireTarget() {
        Enemy clostest = null;
        // arbitrary distance (larger than map), to help with sorting enemy distances
        float clostestDistance = 1000;
        // go through each enemy and return nearest one
        for (Enemy e : enemies) {
            if (isInRange(e) && findDistanceEnemy(e) < clostestDistance && e.getHiddenHealth() > 0) {
//            if (isInRange(e) && e.getHiddenHealth() > 0) {
                clostestDistance = findDistanceEnemy(e);
                clostest = e;
            }
        }
        if (clostest != null) {
            targeted = true;
        }
        return clostest;
    }

    private boolean isInRange(Enemy enemy) {
        float xDistance = Math.abs(enemy.getX() - x);
        float yDistance = Math.abs(enemy.getY() - y);
        return xDistance < range && yDistance < range;
    }

    private float findDistanceEnemy(Enemy enemy) {
        float xDistance = Math.abs(enemy.getX() - x);
        float yDistance = Math.abs(enemy.getY() - y);
        return xDistance + yDistance;
    }

    private float calculateAngle() {
        double angleTemp = Math.atan2(target.getY() - y, target.getX() - x);
        return (float) Math.toDegrees(angleTemp) - 90;
    }

    public abstract void shoot(Enemy target);

    public void updateEnemyList(List<Enemy> newList) {
        enemies = newList;
    }

    public void update() {

        if (target != null) {
            if (!isInRange(target)) {
                targeted = false;
            }
        }

        if (!targeted || target.getHiddenHealth() < 0) {
            target = acquireTarget();
        } else {
            angle = calculateAngle();
            if (timeSinceLastShot > firingSpeed) {
                shoot(target);
                timeSinceLastShot = 0;
            }
        }
        if (target == null || target.isAlive() == false) {
            targeted = false;
        }
        timeSinceLastShot += delta();

        for (Projectile p : projectiles) {
            p.update();
        }

        draw();
    }

    public Enemy getTarget() {
        return target;
    }
}
