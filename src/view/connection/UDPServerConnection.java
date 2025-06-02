package view.connection;

import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import server.host.ClientHandler;
import view.gamestate.GameState;
import view.gamestate.Playing;
import view.main.Game;
import view.map.Structure;
import view.player.Hitbox;
import view.player.Character;

public class UDPServerConnection implements Runnable {

    private DatagramSocket socket;

    private Game game;

    private boolean running = true;

    public UDPServerConnection(Game g) {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.game = g;
    }

    @Override
    public void run() {
        while (running) {
            try {
                byte[] data = new byte[1024];
                DatagramPacket packet = new DatagramPacket(data, data.length);
                socket.receive(packet);
                String res = new String(packet.getData(), 0, packet.getLength());
                String header = ClientHandler.getHeader(res);
                String[] body = ClientHandler.getBody(res);
                switch (GameState.state) {
                    case MENU:
                        break;
                    case PLAYING:
                        Playing playing = game.getPlaying();
                        switch (header) {
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
                                }
                                break;
                        }
                        break;
                }

            } catch (IOException e) {
                if (running) {
                    System.out.println("udp conn pas normal");
                } else {
                    System.out.println("server fermé");
                }
            }
        }
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public void close() {
        running = false;
        socket.close();
    }

}
