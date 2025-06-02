package server.game.main;

import server.game.map.Structure;
import server.game.player.Player;
import server.game.system.*;
import server.host.Server;
import server.host.UDPServer;

import java.lang.System;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Game implements GameObject.Listener, Runnable {

    // Game loop
    private static final int tickRate = 60;
    public static double deltaTime = 1000 / tickRate;
    private Thread gameLoop;


    private Map<Integer, GameObject> objects = new HashMap<>();

    private Map<Integer, Structure> structures = new HashMap<>();
    private Map<Integer, Player> players = new HashMap<>();
    private Map<Integer, Hitbox> hitboxs = new HashMap<>();

    private List<server.game.system.System> systems = List.of(
            new MovementSystem(),
            new HitboxSystem()
    );

    public Game() {
        GameSpawner.setHitboxAdder(hitbox -> addObject(hitbox));
    }

    public void start() {
        gameLoop = new Thread(this);
        gameLoop.start();
    }

    public void update() {
        for (server.game.system.System system : systems) {
            system.update(this);
        }
        for (GameObject object : objects.values()) {
            object.update();
        }
        List<GameObject> toRemove = objects.values().stream().filter(go -> go.isDestroyed()).collect(Collectors.toList());
        for (GameObject object : toRemove) {
            removeObject(object);
        }
    }

    public void addObject(GameObject object) {
        objects.put(object.getId(), object);
        object.addListener(this);
        if (object instanceof Structure) structures.put(object.getId(), (Structure) object);
        if (object instanceof Player) players.put(object.getId(), (Player) object);
        if (object instanceof Hitbox) hitboxs.put(object.getId(), (Hitbox) object);
        onChange(object);
    }

    public void removeObject(GameObject object) {
        objects.remove(object.getId());
        if (object instanceof Structure) structures.remove(object.getId());
        if (object instanceof Player) players.remove(object.getId());
        if (object instanceof Hitbox) hitboxs.remove(object.getId());
    }

    public void removeObject(Integer id) {
        objects.remove(id);
        structures.remove(id);
        players.remove(id);
        hitboxs.remove(id);
    }

    public void serverUpdate() {
        for (GameObject object : objects.values()) {
            Server.getInstance().send(object.getInfo());
        }
    }

    @Override
    public void onChange(GameObject object) {
        Server.getInstance().send(object.getInfo());
    }

    @Override
    public void onChangeUDP(GameObject gameObject) {
        UDPServer.getInstance().send(gameObject.getInfo());
    }

    public void onDestroy(GameObject object) {
        Server.getInstance().send("destroy;" + object.getId());
    }

    @Override
    public void run() {

        final long tickTime = 1000 / tickRate;
        long last = System.nanoTime();

        while (true) {
            long now = System.currentTimeMillis();
            update();

            long duration = System.currentTimeMillis() - now;
            long sleepTime = tickTime - duration;
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Map<Integer, GameObject> getObjects() {
        return objects;
    }

    public Collection<Structure> getStructures() {
        return structures.values();
    }

    public Collection<Player> getPlayers() {
        return players.values();
    }

    public Collection<Hitbox> getHitboxs() {
        return hitboxs.values();
    }
}