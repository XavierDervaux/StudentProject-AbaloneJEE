package be.abalone.websocket;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;
import be.abalone.model.Joueur;

@ApplicationScoped
public class JoueurSessionHandler {
    private int joueurId = 0;
    private final Set<Session> sessions = new HashSet<>();
    private final Set<Joueur> joueurs = new HashSet<>();
    
    public void addSession(Session session) {
        sessions.add(session);
        for (Joueur joueur : joueurs) {
            JsonObject addMessage = createAddMessage(joueur);
            sendToSession(session, addMessage);
        }
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }
    
    public List<Joueur> getJoueurs() {
        return new ArrayList<>(joueurs);
    }

    public void addJoueur(Joueur joueur) {
        joueur.setId(joueurId);
        joueurs.add(joueur);
        joueurId++;
        JsonObject addMessage = createAddMessage(joueur);
        sendToAllConnectedSessions(addMessage);
    }

    public void removeJoueur(int id) {
        Joueur joueur = getJoueurById(id);
        if (joueur != null) {
            joueurs.remove(joueur);
            JsonProvider provider = JsonProvider.provider();
            JsonObject removeMessage = provider.createObjectBuilder()
                    .add("action", "remove")
                    .add("id", id)
                    .build();
            sendToAllConnectedSessions(removeMessage);
        }
    }

    public void toggleJoueur(int id) {
        JsonProvider provider = JsonProvider.provider();
        Joueur joueur = getJoueurById(id);
        if (joueur != null) {
            if ("On".equals(joueur.getStatus())) {
                joueur.setStatus("Off");
            } else {
                joueur.setStatus("On");
            }
            JsonObject updateDevMessage = provider.createObjectBuilder()
                    .add("action", "toggle")
                    .add("id", joueur.getId())
                    .add("status", joueur.getStatus())
                    .build();
            sendToAllConnectedSessions(updateDevMessage);
        }
    }

    private Joueur getJoueurById(int id) {
        for (Joueur joueur : joueurs) {
            if (joueur.getId() == id) {
                return joueur;
            }
        }
        return null;
    }

    private JsonObject createAddMessage(Joueur joueur) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "add")
                .add("id", joueur.getId())
                .add("name", joueur.getName())
                .add("type", joueur.getType())
                .add("status", joueur.getStatus())
                .add("description", joueur.getDescription())
                .build();
        return addMessage;
    }

    private void sendToAllConnectedSessions(JsonObject message) {
        for (Session session : sessions) {
            sendToSession(session, message);
        }
    }

    private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            sessions.remove(session);
            Logger.getLogger(JoueurSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
}