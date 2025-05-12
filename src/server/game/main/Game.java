package server.game.main;

import server.game.map.Structure;
import server.game.player.Player;
import server.host.Server;
import server.host.UDPServer;

import java.util.HashMap;
import java.util.Map;

public class Game implements GameObject.Listener, Runnable {

    // Game loop
    private static final int tickRate = 60;
    public static double deltaTime = 1000 / tickRate;
    private Thread gameLoop;


    private Map<Integer, GameObject> objects = new HashMap<>();

    private Map<Integer, Structure> structures = new HashMap<>();
    private Map<Integer, Player> players = new HashMap<>();

    public Game() {
        //addObject(new Structure(200, 800, 1500, 200));
    }

    public void start() {
        gameLoop = new Thread(this);
        gameLoop.start();
    }

    public void update() {
        for (GameObject object : objects.values()) {
            object.update();
        }
    }

    public void addObject(GameObject object) {
        objects.put(object.getId(), object);
        object.addListener(this);
        if (object instanceof Structure) structures.put(object.getId(), (Structure) object);
        if (object instanceof Player) players.put(object.getId(), (Player) object);
        onChange(object);
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

    @Override
    public void run() {

        final long tickTime = 1000 / tickRate;
        long last = System.nanoTime();

        while (true) {
            long now = System.currentTimeMillis();
            deltaTime = (now - last) / 1000;
            last = now;
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

    public Map<Integer, Structure> getStructures() {
        return structures;
    }

    public Map<Integer, Player> getPlayers() {
        return players;
    }
}