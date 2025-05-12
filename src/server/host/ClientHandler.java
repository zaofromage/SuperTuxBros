package server.host;

import server.game.main.Game;
import server.game.map.Structure;
import server.game.player.Player;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ClientHandler implements Runnable {

    private Socket client;
    private BufferedReader in;
    private PrintWriter out;

    private ArrayList<ClientHandler> clients;
    private Server server;

    private boolean running = true;

    private Game game;

    public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients, Server server) throws IOException {
        client = clientSocket;
        this.server = server;
        this.game = server.getGame();
        this.clients = clients;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(), true);
        out.println("Connected to server");
    }

    @Override
    public void run() {
        try {
            while (running) {
                String request = in.readLine();
                if (request != null) {
                    String header = getHeader(request);
                    String[] body = getBody(request);
                    switch (header) {
                        case "create":
                            switch (body[0]) {
                                case "structure":
                                    Structure struct = new Structure(Integer.parseInt(body[1]), Integer.parseInt(body[2]), Integer.parseInt(body[3]), Integer.parseInt(body[4]));
                                    game.addObject(struct);
                                    break;
                                case "player":
                                    Player p = new Player(body[1], Double.parseDouble(body[2]), Double.parseDouble(body[3]), game.getStructures());
                                    game.addObject(p);
                                    break;
                            }
                            break;
                        case "jump":
                            game.getPlayers().get(Integer.parseInt(body[0])).jump();
                            break;
                        case "up":
                            game.getPlayers().get(Integer.parseInt(body[0])).moveUp();
                            break;
                        case "releaseup":
                            game.getPlayers().get(Integer.parseInt(body[0])).releaseUp();
                            break;
                        case "down":
                            game.getPlayers().get(Integer.parseInt(body[0])).moveDown();
                            break;
                        case "releasedown":
                            game.getPlayers().get(Integer.parseInt(body[0])).releaseDown();
                            break;
                        case "left":
                            game.getPlayers().get(Integer.parseInt(body[0])).moveLeft();
                            break;
                        case "releaseleft":
                            game.getPlayers().get(Integer.parseInt(body[0])).releaseLeft();
                            break;
                        case "right":
                            game.getPlayers().get(Integer.parseInt(body[0])).moveRight();
                            break;
                        case "releaseright":
                            game.getPlayers().get(Integer.parseInt(body[0])).releaseRight();
                            break;
                    }
                }
            }
        } catch (IOException e) {
            if (running) {
                System.out.println("client handler pas normal");
            } else {
                System.out.println("server fermé");
            }
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.close();
        }
    }

    public void close() {
        running = false;
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String msg) {
        for (ClientHandler c : clients) {
            c.out.println(msg);
        }
    }

    public static String getHeader(String req) {
        return req.split(";")[0];
    }

    public static String[] getBody(String req) {
        String[] split = req.split(";");
        String[] body = new String[split.length  - 1];
        for (int i = 1; i < split.length; i++) {
            body[i - 1] = split[i];
        }
        return body;
    }
}
