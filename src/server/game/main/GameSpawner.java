package server.game.main;

import java.util.function.Consumer;

public class GameSpawner {
    private static Consumer<Hitbox> hitboxAdder;

    public static void setHitboxAdder(Consumer<Hitbox> adder) {
        hitboxAdder = adder;
    }

    public static void spawnHitbox(Hitbox hitbox) {
        if (hitboxAdder != null) hitboxAdder.accept(hitbox);
    }
}

