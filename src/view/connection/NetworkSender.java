package view.connection;

import server.host.UDPServer;
import view.main.Game;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;

public class NetworkSender {

    private DatagramSocket socketUDP;
    private PrintWriter out;
    private InetAddress ip;

    public NetworkSender(PrintWriter out, InetAddress ip, DatagramSocket socketUDP, Game game) {
        this.out = out;
        this.ip = ip;
        this.socketUDP = socketUDP;
    }

    public void send(String msg) {
        out.println(msg);
    }

    public void sendUDP(String mes) {
        byte[] toSend = mes.getBytes();
        DatagramPacket packet = new DatagramPacket(toSend, toSend.length, ip, UDPServer.PORT);
        try {
            socketUDP.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        socketUDP.close();
        out.close();
    }
}
