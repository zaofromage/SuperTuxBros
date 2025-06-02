package server.game.main;

import java.util.ArrayList;
import java.util.List;

public abstract class GameObject {

    public interface Listener {
        public void onChange(GameObject gameObject);
        public void onChangeUDP(GameObject gameObject);
        public void onDestroy(GameObject gameObject);
    }

    private List<Listener> listeners = new ArrayList<>();

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    protected void fireChange() {
        for (Listener listener : listeners) {
            listener.onChange(this);
        }
    }

    protected void fireDestroy() {
        for (Listener listener : listeners) {
            listener.onDestroy(this);
        }
    }

    protected void fireChangeUDP() {
        for (Listener listener : listeners) {
            listener.onChangeUDP(this);
        }
    }

    private boolean destroyed = false;

    public void destroy() {
        destroyed = true;
        fireDestroy();
    }

    public boolean isDestroyed() {
        return destroyed;
    }


    private static int counter = 0;

    private int id;

    public GameObject() {
        this.id = counter++;
    }

    public void update() {

    }

    /**
     * Get all information about the object for a global update
     * @return a packet with all info of the object
     */
    public abstract String getInfo();

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GameObject) {
            GameObject gameObject = (GameObject) obj;
            return this.id == gameObject.id;
        }
        return false;
    }
}
