package data;

import static helper.Artist.TILE_SIZE;
import static helper.Artist.drawQuadTex;

public class TileGrid {

    public Tile[][] map;
    private int tilesWide, tilesHigh;

    public int getTilesWide() {
        return tilesWide;
    }

    public void setTilesWide(int tilesWide) {
        this.tilesWide = tilesWide;
    }

    public int getTilesHigh() {
        return tilesHigh;
    }

    public void setTilesHigh(int tilesHigh) {
        this.tilesHigh = tilesHigh;
    }

    public TileGrid() {
        this.tilesWide = 20;
        this.tilesHigh = 15;
        map = new Tile[tilesWide][tilesHigh];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = new Tile(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Grass);
            }
        }
    }

    public TileGrid(int[][] newMap) {
        this.tilesWide = newMap[0].length;
        this.tilesHigh = newMap.length;
        map = new Tile[tilesWide][tilesHigh];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                switch (newMap[j][i]) {
                    case 0:
                        map[i][j] = new Tile(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Grass);
                        break;
                    case 1:
                        map[i][j] = new Tile(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Dirt);
                        break;
                    case 2:
                        map[i][j] = new Tile(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Water);
                        break;
                }
            }
        }
    }

    public void setTile (int xCoord, int yCoord, TileType tileType) {
        try {
            map[xCoord][yCoord] = new Tile(xCoord * TILE_SIZE, yCoord * TILE_SIZE, TILE_SIZE, TILE_SIZE, tileType);
        } catch (Exception e) {

        }
    }

    public Tile getTile(int xPlace, int yPlace) {
        if (xPlace < tilesWide && yPlace < tilesHigh && xPlace > -1 && yPlace > -1) {
            return map[xPlace][yPlace];
        } else {
            return new Tile(0, 0, 0, 0, TileType.NULL);
        }
    }

    public void draw() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
//                Tile t = map[i][j];
//                drawQuadTex(t.getTexture(), t.getX(), t.getY(), t.getWidth(), t.getHeight());
                map[i][j].draw();
            }
        }
    }
}
