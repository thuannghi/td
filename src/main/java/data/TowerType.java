package data;

import org.newdawn.slick.opengl.Texture;

import static helper.Artist.quickLoad;

public enum TowerType {

    TowerRed(new Texture[]{quickLoad("cannonbase"), quickLoad("cannongun")}, ProjectileType.CannonBall, 10, 250, 4, 0),
    TowerBlue(new Texture[]{quickLoad("cannonbaseblue"), quickLoad("cannongunblue")}, ProjectileType.CannonBall, 15, 250, 4, 15),
    TowerIce(new Texture[]{quickLoad("mushroombase"), quickLoad("cannongun")}, ProjectileType.IceBall, 10, 220, 3, 20);

    Texture[] textures;
    ProjectileType projectileType;
    int damage, range, cost;
    float firingSpeed;

    TowerType(Texture[] textures, ProjectileType projectileType, int damage, int range, float firingSpeed, int cost) {
        this.textures = textures;
        this.projectileType = projectileType;
        this.damage = damage;
        this.range = range;
        this.cost = cost;
        this.firingSpeed = firingSpeed;
    }
}
