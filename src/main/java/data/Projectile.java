package data;

import org.newdawn.slick.opengl.Texture;

import static helper.Artist.*;
import static helper.Clock.delta;

public abstract class Projectile implements Entity {

    private Texture texture;
    private float x, y, speed, xVelocity, yVelocity;
    private int damage, width, height;
    private Enemy target;
    private boolean alive;

    public Projectile(ProjectileType type, Enemy target, float x, float y, int width, int height) {
        this.texture = type.texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = type.speed;
        this.damage = type.damage;
        this.target = target;
        this.xVelocity = 0f;
        this.yVelocity = 0f;
        this.alive = true;
        calculateDirection();
    }

    private void calculateDirection() {
        float totalAllowedMovement = 1.0f;
        float xDistanceFromTarget = Math.abs(target.getX() - x - TILE_SIZE / 4 + TILE_SIZE / 2);
        float yDistanceFromTarget = Math.abs(target.getY() - y - TILE_SIZE / 4 + TILE_SIZE / 2);
        float totalDistanceFromTarget = xDistanceFromTarget + yDistanceFromTarget;
        float xPercentOfMovement = xDistanceFromTarget / totalDistanceFromTarget;
        xVelocity = xPercentOfMovement;
        yVelocity = totalAllowedMovement - xPercentOfMovement;
        if (target.getX() < x) {
            xVelocity *= -1;
        }
        if (target.getY() < y) {
            yVelocity *= -1;
        }
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
        return 0;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void update() {
        if (alive) {
            calculateDirection();
            x += xVelocity * speed * delta();
            y += yVelocity * speed * delta();
            if (checkCollision(x, y, width, height, target.getX(), target.getY(), target.getWidth(), target.getHeight())) {
                damage();
            }
            draw();
        }
    }

    public void damage () {
        target.damage(damage);
        alive = false;
    }

    public void draw() {
        drawQuadTex(texture, x, y, 32, 32);
    }

    public Enemy getTarget () {
        return target;
    }

    public void setAlive (boolean status) {
        this.alive = status;
    }
 }
