package data;

import org.newdawn.slick.opengl.Texture;

import static helper.Artist.quickLoad;

public enum ProjectileType {

    CannonBall(quickLoad("bullet"), 15, 600),
    IceBall(quickLoad("newbullet"), 8, 450);

    Texture texture;
    int damage;
    float speed;

    ProjectileType(Texture texture, int damage, float speed) {
        this.texture = texture;
        this.damage = damage;
        this.speed = speed;
    }
}
