package view.connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import server.host.Server;
import view.main.Game;

public class Client {

    private static Client instance;

    private Socket socket;

    private ServerConnection serverConn;
    private UDPServerConnection udpConn;
    private NetworkSender sender;

    private Client(String ip, Game game) throws IOException {
        socket = new Socket(ip, Server.PORT);
        serverConn = new ServerConnection(socket, game);
        udpConn = new UDPServerConnection(game);
        new Thread(serverConn).start();
        new Thread(udpConn).start();

        sender = new NetworkSender(new PrintWriter(socket.getOutputStream(), true), InetAddress.getByName(ip), udpConn.getSocket(), game);
        sender.sendUDP("setup");
    }

    public void close() {
        try {
            socket.close();
            serverConn.close();
            udpConn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public NetworkSender getSender() {
        return sender;
    }

    public static Client getInstance() {
        return instance;
    }

    public static void instantiate(String ip, Game game) {
        try {
            instance = new Client(ip, game);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
