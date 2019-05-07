package data;

import java.util.List;

public class TowerCannonBlue extends Tower {

    public TowerCannonBlue(TowerType type, Tile startTile, List<Enemy> enemies) {
        super(type, startTile, enemies);
    }

    public void shoot(Enemy target) {
        super.projectiles.add(new ProjectileCannonBall(super.type.projectileType, target, super.getX(), super.getY(), 32, 32));
        super.target.reduceHiddenHealth(super.type.projectileType.damage);
    }
}
