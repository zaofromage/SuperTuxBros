package server.game.player;

import server.game.main.GameObject;

public class Player extends GameObject {

    protected String name;
    protected String character;
    protected Integer characterId;

    public Player(String character) {
        this.character = character;
    }

    @Override
    public String getInfo() {
        return "update;player;" + getId() + ";" + name;
    }

    public Character createCharacter(double x, double y) {
        switch (character) {
            case "tux":
                Character p = new Tux(x, y);
                characterId = p.getId();
                return p;
            default:
                p = new Tux(x, y);
                characterId = p.getId();
                return p;
        }
    }

    public String getName() {
        return name;
    }

    public Integer getCharacterId() {
        return characterId;
    }
}
