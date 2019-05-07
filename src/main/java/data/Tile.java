package data;

import org.newdawn.slick.opengl.Texture;

import static helper.Artist.*;

public class Tile {
    private int width, height;
    private float x, y;
    private Texture texture;
    private TileType tileType;
    private boolean occupied;

    public float getX() {
        return x;
    }

    public int getXPlace () {
        return (int) x / TILE_SIZE;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public int getYPlace () {
        return (int) y / TILE_SIZE;
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

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public Tile(float x, float y, int width, int height, TileType tileType) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.tileType = tileType;
        this.texture = quickLoad(tileType.textureName);
        checBuildable();
    }

    private void checBuildable () {
        if (tileType.buildable) {
            occupied = false;
        } else {
            occupied = true;
        }
    }

    public boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public void draw () {
        drawQuadTex(texture, x, y, width, height);
    }
}
