package server.game.system;

import server.game.main.Game;
import server.game.main.Hitbox;
import server.game.player.Character;

import java.util.Collection;

public class HitboxSystem implements System {

    @Override
    public void update(Game game) {
        applyHit(game.getCharacters(), game.getHitboxs());
    }

    public void applyHit(Collection<Character> characters, Collection<Hitbox> hitboxs) {
        for (Hitbox hitbox : hitboxs) {
            for (Character character : characters) {
                if (hitbox.getHitbox().intersects(character.getHurtbox())) {
                    if (hitbox.getImmune() == null || !character.equals(hitbox.getImmune())) {
                        character.getRigidbody().x = 0;
                        character.getRigidbody().y = 0;
                        character.getRigidbody().add(hitbox.getDir());
                        hitbox.destroy();
                    }
                }
            }
        }
    }
}
