package server.game.main;

import server.game.map.Structure;
import server.game.player.Character;
import server.game.player.Player;
import server.game.system.*;
import server.host.Server;
import server.host.UDPServer;
import utils.Time;
import view.main.GamePanel;

import java.lang.System;

import java.util.*;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Game implements GameObject.Listener, Runnable {

    // Game loop
    private static final int tickRate = 60;
    public static double deltaTime = 1000 / tickRate;
    private Thread gameLoop;

    private ScheduledFuture<?> syncLoop;


    private Map<Integer, GameObject> objects = new HashMap<>();

    private Map<Integer, Structure> structures = new HashMap<>();
    private Map<Integer, Character> characters = new HashMap<>();
    private Map<Integer, Hitbox> hitboxs = new HashMap<>();
    private Map<Integer, Player> players = new HashMap<>();

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
        syncLoop = Time.interval(() -> serverUpdate(), 1000);
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

    public void launch() {
        addObject(new Structure(200, GamePanel.dim.height*3 / 4, GamePanel.dim.width - 400, 200));
        Time.delay(() -> {
//            int i = 0;
//            int total = players.size();
//            int y = GamePanel.dim.height / 2;
//
//            for (Player player : players.values()) {
//                int x;
//                if (i % 2 == 0) {
//                    x = (i / 2) * (GamePanel.dim.width / total / 2);
//                } else {
//                    x = GamePanel.dim.width - ((i / 2) * (GamePanel.dim.width / total / 2));
//                }
//                addObject(player.createCharacter(x, y));
//                i++;
//              }
            for (Player player : players.values()) {
                addObject(player.createCharacter(GamePanel.dim.width/2, 200));
            }
            serverUpdate();
        }, 3, TimeUnit.SECONDS);
    }

    public void addObject(GameObject object) {
        objects.put(object.getId(), object);
        object.addListener(this);
        if (object instanceof Structure) structures.put(object.getId(), (Structure) object);
        if (object instanceof Character) characters.put(object.getId(), (Character) object);
        if (object instanceof Hitbox) hitboxs.put(object.getId(), (Hitbox) object);
        if (object instanceof Player) players.put(object.getId(), (Player) object);
        onChange(object);
    }

    public void removeObject(GameObject object) {
        objects.remove(object.getId());
        if (object instanceof Structure) structures.remove(object.getId());
        if (object instanceof Character) characters.remove(object.getId());
        if (object instanceof Hitbox) hitboxs.remove(object.getId());
        if (object instanceof Player) players.remove(object.getId());
    }

    public void removeObject(Integer id) {
        objects.remove(id);
        structures.remove(id);
        characters.remove(id);
        hitboxs.remove(id);
        players.remove(id);
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

    public Collection<Character> getCharacters() {
        return characters.values();
    }

    public Collection<Player> getPlayers() { return players.values(); }

    public Collection<Hitbox> getHitboxs() {
        return hitboxs.values();
    }
}