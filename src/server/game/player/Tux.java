package server.game.player;

import server.game.main.Game;
import server.game.main.GameSpawner;
import server.game.main.Hitbox;
import utils.Time;
import utils.Vector;

import java.awt.*;

public class Tux extends Character {


    public Tux(double x, double y) {
        super(x, y);
    }

    @Override
    public void upTilt() {
        Time.delay(() -> GameSpawner.spawnHitbox(new Hitbox(new Rectangle((int)(x - (look*40)), (int)y-10, 30, 30), new Vector(-2*look, -5), this, 500)), (long)(4 * Game.deltaTime));
        Time.delay(() -> GameSpawner.spawnHitbox(new Hitbox(new Rectangle((int)(x), (int)y-40, 30, 30), new Vector(0, -5), this, 500)), (long) (8 * Game.deltaTime));
        Time.delay(() -> GameSpawner.spawnHitbox(new Hitbox(new Rectangle((int)(x + (look*40)), (int)y-10, 30, 30), new Vector(2*look, -5), this, 500)), (long)(12 * Game.deltaTime));
    }

    @Override
    public void downTilt() {

    }

    @Override
    public void forwardTilt() {

    }

    @Override
    public void jab() {

    }

    @Override
    public void upAir() {

    }

    @Override
    public void downAir() {

    }

    @Override
    public void forwardAir() {

    }

    @Override
    public void backAir() {

    }
}
