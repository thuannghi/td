package data;

import static helper.Artist.quickLoad;

public class EnemyUFO extends Enemy {

    public EnemyUFO(int tileX, int tileY, TileGrid grid) {
        super(tileX, tileY, grid);
        this.setTexture(quickLoad("UFO"));
        this.setSpeed(80);
    }
}
