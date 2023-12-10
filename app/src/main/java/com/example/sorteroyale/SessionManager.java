package com.example.sorteroyale;
import java.util.HashMap;

public class SessionManager {
    private static SessionManager instance;
    private HashMap<String, String> SESSION;

    private SessionManager() {
        // Construtor privado para evitar instâncias múltiplas
        SESSION = new HashMap<>();
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public HashMap<String, String> getSession() {
        return SESSION;
    }

    public void setSession(HashMap<String, String> session) {
        this.SESSION = session;
    }

    public void clearSession() {
        SESSION.clear();
//        SessionManager.getInstance().clearSession();
    }
}
