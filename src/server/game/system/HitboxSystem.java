package server.game.system;

import server.game.main.Game;
import server.game.main.Hitbox;
import server.game.player.Player;

import java.util.Collection;

public class HitboxSystem implements System {

    @Override
    public void update(Game game) {
        applyHit(game.getPlayers(), game.getHitboxs());
    }

    public void applyHit(Collection<Player> players, Collection<Hitbox> hitboxs) {
        for (Hitbox hitbox : hitboxs) {
            for (Player player : players) {
                if (hitbox.getHitbox().intersects(player.getHurtbox())) {
                    if (hitbox.getImmune() == null || !player.equals(hitbox.getImmune())) {
                        player.getRigidbody().x = 0;
                        player.getRigidbody().y = 0;
                        player.getRigidbody().add(hitbox.getDir());
                        hitbox.destroy();
                    }
                }
            }
        }
    }
}
