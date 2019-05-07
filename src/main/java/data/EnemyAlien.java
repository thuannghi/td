package data;

public class EnemyAlien extends Enemy {

    public EnemyAlien(int tileX, int tileY, TileGrid grid) {
        super(tileX, tileY, grid);
        this.setSpeed(45);

    }
}
