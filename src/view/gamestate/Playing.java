package view.gamestate;

import view.connection.Client;
import view.main.ViewObject;
import view.map.Structure;
import view.player.Hitbox;
import view.player.Player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class Playing implements StateMethods {

    private Map<Integer, ViewObject> objects = new HashMap<>();
    private Map<Integer, Structure> structures = new HashMap<>();
    private Map<Integer, Player> players = new HashMap<>();
    private Map<Integer, Hitbox> hitboxs = new HashMap<>();

    private Player player;

    public Playing() {

    }

    public void addObject(ViewObject object) {
        objects.put(object.getId(), object);
        if (object instanceof Structure) {structures.put(object.getId(), (Structure) object);}
        if (object instanceof Player) {
            players.put(object.getId(), (Player) object);
            if (player == null) {
                player = (Player) object;
            }
        }
        if (object instanceof Hitbox) {hitboxs.put(object.getId(), (Hitbox) object);}
    }

    public void removeObject(ViewObject object) {
        objects.remove(object.getId());
        if (object instanceof Structure) {structures.remove(object.getId());}
        if (object instanceof Player) {players.remove(object.getId());}
        if (object instanceof Hitbox) {hitboxs.remove(object.getId());}
    }

    public void removeObject(Integer id) {
        objects.remove(id);
        structures.remove(id);
        players.remove(id);
        hitboxs.remove(id);
    }

    @Override
    public void draw(Graphics g) {
        for (ViewObject object : objects.values()) {
            object.draw(g);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            Client.getInstance().getSender().send("create;player;titouan;" + e.getX() + ";" + e.getY());
        } else if (e.getButton() == MouseEvent.BUTTON2) {
            Client.getInstance().getSender().send("create;hitbox;" + e.getX() + ";" + e.getY() + ";50;50;-1");
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            Client.getInstance().getSender().send("create;structure;" + e.getX() + ";" + e.getY() + ";400;50");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // move
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            Client.getInstance().getSender().send("jump;" + player.getId());
        } else if (e.getKeyCode() == KeyEvent.VK_Z) {
            Client.getInstance().getSender().send("up;" + player.getId());
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            Client.getInstance().getSender().send("down;" + player.getId());
        } else if (e.getKeyCode() == KeyEvent.VK_Q) {
            Client.getInstance().getSender().send("left;" + player.getId());
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            Client.getInstance().getSender().send("right;" + player.getId());
        } else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            Client.getInstance().getSender().send("dash;" + player.getId());
        }
        // attack
        else if (e.getKeyCode() == KeyEvent.VK_UP) {
            Client.getInstance().getSender().send("upattack;" + player.getId());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_Z) {
            Client.getInstance().getSender().send("releaseup;" + player.getId());
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            Client.getInstance().getSender().send("releasedown;" + player.getId());
        } else if (e.getKeyCode() == KeyEvent.VK_Q) {
            Client.getInstance().getSender().send("releaseleft;" + player.getId());
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            Client.getInstance().getSender().send("releaseright;" + player.getId());
        }
    }

    public Map<Integer, Player> getPlayers() {
        return players;
    }

    public Map<Integer, Structure> getStructures() {
        return structures;
    }

    public Map<Integer, Hitbox> getHitboxs() {
        return hitboxs;
    }

    public boolean exists(int id) {
        return objects.containsKey(id);
    }
}
