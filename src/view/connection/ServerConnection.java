package view.connection;

import server.host.ClientHandler;
import view.gamestate.GameState;
import view.gamestate.Playing;
import view.map.Structure;
import view.main.Game;
import view.player.Hitbox;
import view.player.Character;
import view.player.Player;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerConnection implements Runnable {

    private final BufferedReader in;

    private final Game game;

    private boolean running = true;

    public ServerConnection(Socket s, Game game) throws IOException {
        this.game = game;
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
    }

    @Override
    public void run() {
        String serverResponse;
        try {
            while (running) {
                serverResponse = in.readLine();
                if (serverResponse != null) {
                    String header = ClientHandler.getHeader(serverResponse);
                    String[] body = ClientHandler.getBody(serverResponse);
                    switch (GameState.state) {
                        case MENU:
                            break;
                        case SELECT:
                            switch (header) {
                                case "playerid":
                                    game.setPlayer(new Player(Integer.parseInt(body[0])));
                                    break;
                                case "launch":
                                    game.switchState(GameState.PLAYING);
                                    break;
                            }
                            break;
                        case PLAYING:
                            Playing playing = game.getPlaying();
                            switch (header) {
                                case "destroy":
                                    playing.getObjects().get(Integer.parseInt(body[0])).destroy();
                                    break;
                                case "update":
                                    int id = Integer.parseInt(body[1]);
                                    switch (body[0]) {
                                        case "structure":
                                            if (playing.exists(id)) {
                                                playing.getStructures().get(id).set(Integer.parseInt(body[2]), Integer.parseInt(body[3]), Integer.parseInt(body[4]), Integer.parseInt(body[5]));
                                            } else {
                                                playing.addObject(new Structure(id, Integer.parseInt(body[2]), Integer.parseInt(body[3]), Integer.parseInt(body[4]), Integer.parseInt(body[5])));
                                            }
                                            break;
                                        case "character":
                                            if (playing.exists(id)) {
                                                playing.getPlayers().get(id).set(Double.parseDouble(body[2]), Double.parseDouble(body[3]), Integer.parseInt(body[4]));
                                            } else {
                                                playing.addObject(new Character(id, Double.parseDouble(body[2]), Double.parseDouble(body[3]), Integer.parseInt(body[4])));
                                            }
                                            break;
                                        case "hitbox":
                                            if (playing.exists(id)) {
                                                playing.getHitboxs().get(id).set(Integer.parseInt(body[2]), Integer.parseInt(body[3]), Integer.parseInt(body[4]), Integer.parseInt(body[5]));
                                            } else {
                                                playing.addObject(new Hitbox(id, new Rectangle(Integer.parseInt(body[2]), Integer.parseInt(body[3]), Integer.parseInt(body[4]), Integer.parseInt(body[5]))));
                                            }
                                            break;
                                        case "player":
                                            break;
                                    }
                                    break;
                            }
                            break;
                    }
                }
            }
        } catch (IOException e) {
            if (running) {
                System.out.println("pas normal server conn");
            } else {
                System.out.println("server fermé");
            }
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        running = false;
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
