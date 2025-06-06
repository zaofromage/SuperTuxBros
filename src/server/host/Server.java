package server.host;

import server.game.main.Game;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.IOException;
import java.net.*;

public class Server implements Runnable {

    private static Server instance;

    public static final int PORT = 4551;

    private ArrayList<ClientHandler> clients;
    private ServerSocket server;

    private ExecutorService pool;

    private boolean running = true;

    private Game game;

    private Server() throws IOException {
        game = new Game();
        clients = new ArrayList<>();
        server = new ServerSocket(PORT, 0, InetAddress.getByName("0.0.0.0"));
        pool = Executors.newFixedThreadPool(8);

        pool.execute(this);
    }

    @Override
    public void run() {
        while (running) {
            try {
                Socket client = server.accept();
                ClientHandler ch = new ClientHandler(client, clients, this);
                clients.add(ch);
                pool.execute(ch);
            } catch (IOException e) {
                if (running) {
                    e.printStackTrace();
                } else {
                    System.out.println("Serveur arreté proprement");
                }
            }
        }
    }

    public void close() {
        running = false;
        try {
            if (server != null && !server.isClosed()) {
                server.close(); // Cela lève une SocketException dans le thread `run()`
            }
            for (ClientHandler ch : clients) {
                ch.close();
            }
            pool.shutdownNow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String message) {
        clients.get(0).send(message);
    }

    public static void instantiate() {
        try {
            instance = new Server();
            instance.getGame().start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Server getInstance() {
        return instance;
    }

    public Game getGame() {
        return game;
    }
}