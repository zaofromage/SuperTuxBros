package view.gamestate;

import view.connection.Client;
import view.main.Game;
import view.main.ViewObject;
import view.map.Structure;
import view.player.Hitbox;
import view.player.Character;
import view.player.Player;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Playing implements StateMethods {

    private Map<Integer, ViewObject> objects = new HashMap<>();
    private Map<Integer, Structure> structures = new HashMap<>();
    private Map<Integer, Character> characters = new HashMap<>();
    private Map<Integer, Hitbox> hitboxs = new HashMap<>();

    private Player player;

    public Playing(Player player) {
        this.player = player;
    }

    public void addObject(ViewObject object) {
        objects.put(object.getId(), object);
        if (object instanceof Structure) {structures.put(object.getId(), (Structure) object);}
        if (object instanceof Character) {characters.put(object.getId(), (Character) object);}
        if (object instanceof Hitbox) {hitboxs.put(object.getId(), (Hitbox) object);}
    }

    public void removeObject(ViewObject object) {
        objects.remove(object.getId());
        if (object instanceof Structure) {structures.remove(object.getId());}
        if (object instanceof Character) {characters.remove(object.getId());}
        if (object instanceof Hitbox) {hitboxs.remove(object.getId());}
    }

    public void removeObject(Integer id) {
        objects.remove(id);
        structures.remove(id);
        characters.remove(id);
        hitboxs.remove(id);
    }

    @Override
    public void draw(Graphics g) {
        Game.log(characters.values().toString());
        for (ViewObject object : objects.values()) {
            object.draw(g);
        }
        List<ViewObject> toRemove = objects.values().stream().filter(vo -> vo.isDestroyed()).collect(Collectors.toList());
        for (ViewObject object : toRemove) {
            removeObject(object);
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

    public Map<Integer, ViewObject> getObjects() {
        return objects;
    }

    public Map<Integer, Character> getPlayers() {
        return characters;
    }

    public Map<Integer, Structure> getStructures() {
        return structures;
    }

    public Map<Integer, Hitbox> getHitboxs() {
        return hitboxs;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean exists(int id) {
        return objects.containsKey(id);
    }
}
