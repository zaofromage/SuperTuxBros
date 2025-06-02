package server.game.system;

import server.game.main.Game;
import server.game.map.Structure;
import server.game.player.Player;

import java.awt.*;
import java.util.Collection;
import java.util.Map;

public class MovementSystem implements System {

    private static int jumpDelta = 15;

    @Override
    public void update(Game game) {
        for (Player p : game.getPlayers()) {
            isGrounded(p, game.getStructures());
            movePlayer(p, game.getStructures());
            applyGravity(p, game.getStructures());
        }
    }

    private void movePlayer(Player p, Collection<Structure> structures) {
        double dx = p.getX() + p.getRigidbody().x + p.getDir().x * p.getSpeed() * (p.isInDash() ? p.getDashSpeed() : 1);
        double dy = p.getY() + p.getRigidbody().y + p.getDir().y * p.getSpeed() * (p.isInDash() ? p.getDashSpeed() : 1);

        Structure collideX = null;
        Structure collideY = null;

        if (structures.isEmpty() && (dx != 0 && dy != 0)) {
            p.set(dx, dy);
        } else {
            for (Structure s : structures) {
                if (s.getHurtbox().intersects(new Rectangle((int)dx, (int)p.getY(), p.getHurtbox().width, p.getHurtbox().height))) {
                    collideX = s;
                }
                if (s.getHurtbox().intersects(new Rectangle((int)p.getX(), (int)dy, p.getHurtbox().width, p.getHurtbox().height))) {
                    collideY = s;
                }
            }

            if (collideX == null && collideY == null) {
                p.set(dx, dy);
            } else if (collideX == null) {
                if (collideY.getHurtbox().y >= dy) {
                    p.recoverDash(2);
                    p.getRigidbody().y = 0;
                    p.set(dx, collideY.getY() - p.getHurtbox().height);
                } else {
                    p.set(dx, collideY.getY() + collideY.getHurtbox().height);
                }
            } else if (collideY == null) {
                p.getRigidbody().x = 0;
                if (collideX.getHurtbox().x >= dx) {
                    p.set(collideX.getX() - p.getHurtbox().width, dy);
                } else {
                    p.set(collideX.getX() + collideX.getHurtbox().width, dy);
                }
            }
        }
    }

    private void applyGravity(Player p, Collection<Structure> structures) {
        if (isWalled(p, structures)) {
            p.getRigidbody().y = 1;
        } else {
            p.getRigidbody().y += p.getRigidbody().y < 10 ? 0.5 : 0;
        }
    }

    public boolean isWalled(Player p, Collection<Structure> structures) {
        for (Structure s : structures) {
            if (s.getHurtbox().intersects(new Rectangle((int)p.getX() + 1, (int)p.getY(), p.getHurtbox().width, p.getHurtbox().height)) ||
                    s.getHurtbox().intersects(new Rectangle((int)p.getX() - 1, (int)p.getY(), p.getHurtbox().width, p.getHurtbox().height))) { // bas
                return true;
            }
        }
        return false;
    }

    public boolean isGrounded(Player p, Collection<Structure> structures) {
        for (Structure s : structures) {
            if (s.getHurtbox().intersects(new Rectangle((int)p.getX(), (int)p.getY()+jumpDelta, p.getHurtbox().width, p.getHurtbox().height)) && s.getHurtbox().y >= p.getY()) { // bas
                p.setGrounded(true);
                return true;
            }
        }
        p.setGrounded(false);
        return false;
    }
}
