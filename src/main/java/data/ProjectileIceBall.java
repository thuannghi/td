package data;

import org.newdawn.slick.opengl.Texture;

public class ProjectileIceBall extends Projectile {

    public ProjectileIceBall(ProjectileType type, Enemy target, float x, float y, int width, int height) {
        super(type, target, x, y, width, height);
    }

    @Override
    public void damage () {
        super.getTarget().setSpeed(35);
        super.damage();
    }
}
