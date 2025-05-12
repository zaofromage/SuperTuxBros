package server.host;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.HashMap;
import java.util.Map;

public class UDPServer implements Runnable {

    private static UDPServer instance;

    public static final int PORT = 4552;

    private HashMap<InetAddress, Integer> clients = new HashMap<>();
    private DatagramSocket serverSocket;
    private byte[] data = new byte[1024];
    private boolean running = true;

    private UDPServer() {
        try {
            serverSocket = new DatagramSocket(PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (running) {
            try {
                DatagramPacket packet = new DatagramPacket(data, data.length);
                serverSocket.receive(packet);
                clients.put(packet.getAddress(), packet.getPort());
                String request = new String(packet.getData(), 0, packet.getLength());
            } catch (IOException e) {
                if (running) {
                    System.out.println("pas normal udp");
                } else {
                    System.out.println("udp bien fermé");
                }
            }

        }
    }

    public void send(String msg) {
        byte[] response = msg.getBytes();
        for (Map.Entry<InetAddress, Integer> a : clients.entrySet()) {
            DatagramPacket toSend = new DatagramPacket(response, response.length, a.getKey(), a.getValue());
            try {
                serverSocket.send(toSend);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        running = false;
        serverSocket.close();
    }

    public static void instantiate() {
        instance = new UDPServer();
    }

    public static UDPServer getInstance() {
        return instance;
    }
}