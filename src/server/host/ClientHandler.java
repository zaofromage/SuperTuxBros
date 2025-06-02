package server.host;

import server.game.main.Game;
import server.game.map.Structure;
import server.game.main.Hitbox;
import server.game.player.Character;
import server.game.player.Player;
import server.game.player.Tux;
import utils.Vector;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

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
                        case "launch":
                            game.launch();
                            send("launch;");
                            break;
                        case "create":
                            switch (body[0]) {
                                case "structure":
                                    Structure struct = new Structure(Integer.parseInt(body[1]), Integer.parseInt(body[2]), Integer.parseInt(body[3]), Integer.parseInt(body[4]));
                                    game.addObject(struct);
                                    break;
                                case "player":
                                    Player p = new Player(body[1]);
                                    game.addObject(p);
                                    sendTo("playerid;" + p.getId());
                                    break;
                                case "hitbox":
                                    Character character = Integer.parseInt(body[5]) <= 0 ? (Character) game.getObjects().get(Integer.parseInt(body[5])) : null;
                                    Hitbox h = new Hitbox(new Rectangle(Integer.parseInt(body[1]), Integer.parseInt(body[2]), Integer.parseInt(body[3]), Integer.parseInt(body[4])), new Vector(0, -10), character, 10000);
                                    game.addObject(h);
                                    break;
                            }
                            break;
                        // command
                        // move
                        case "jump":
                            Player player = (Player) game.getObjects().get(Integer.parseInt(body[0]));
                            Character character = (Character) game.getObjects().get(player.getCharacterId());
                            character.jump(game.getStructures());
                            break;
                        case "dash":
                            player = (Player) game.getObjects().get(Integer.parseInt(body[0]));
                            character = (Character) game.getObjects().get(player.getCharacterId());
                            character.dash();
                            break;
                        case "up":
                            player = (Player) game.getObjects().get(Integer.parseInt(body[0]));
                            character = (Character) game.getObjects().get(player.getCharacterId());
                            character.moveUp();
                            break;
                        case "releaseup":
                            player = (Player) game.getObjects().get(Integer.parseInt(body[0]));
                            character = (Character) game.getObjects().get(player.getCharacterId());
                            character.releaseUp();
                            break;
                        case "down":
                            player = (Player) game.getObjects().get(Integer.parseInt(body[0]));
                            character = (Character) game.getObjects().get(player.getCharacterId());
                            character.moveDown();
                            break;
                        case "releasedown":
                            player = (Player) game.getObjects().get(Integer.parseInt(body[0]));
                            character = (Character) game.getObjects().get(player.getCharacterId());
                            character.releaseDown();
                            break;
                        case "left":
                            player = (Player) game.getObjects().get(Integer.parseInt(body[0]));
                            character = (Character) game.getObjects().get(player.getCharacterId());
                            character.moveLeft();
                            break;
                        case "releaseleft":
                            player = (Player) game.getObjects().get(Integer.parseInt(body[0]));
                            character = (Character) game.getObjects().get(player.getCharacterId());
                            character.releaseLeft();
                            break;
                        case "right":
                            player = (Player) game.getObjects().get(Integer.parseInt(body[0]));
                            character = (Character) game.getObjects().get(player.getCharacterId());
                            character.moveRight();
                            break;
                        case "releaseright":
                            player = (Player) game.getObjects().get(Integer.parseInt(body[0]));
                            character = (Character) game.getObjects().get(player.getCharacterId());
                            character.releaseRight();
                            break;
                        // attack
                        case "upattack":
                            player = (Player) game.getObjects().get(Integer.parseInt(body[0]));
                            character = (Character) game.getObjects().get(player.getCharacterId());
                            character.upTilt();
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

    public void sendTo(String msg) {
        out.println(msg);
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
