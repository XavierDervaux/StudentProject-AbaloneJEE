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

@ApplicationScoped
public class JoueurSessionHandler {
    private int joueurId = 0;
    private final Set<Session> sessions = new HashSet<>();
    private final Set<bJoueur> joueurs = new HashSet<>();
    
    public void addSession(Session session) { //On ajoute l'objet a notre session et on le renvoie au client pour qu'il puisse l'afficher a son tour
        this.sessions.add(session);
        for (bJoueur bean : this.joueurs) {
            JsonObject addMessage = createAddMessage(bean);
            sendToSession(session, addMessage);
        }
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }
    
    public List<bJoueur> getJoueurs() {
        return new ArrayList<>(this.joueurs);
    }

    public void addJoueur(bJoueur bean) { //On ajoute un bJoueur et on le fait savoir à chaque client
        bean.setId(this.joueurId);
        this.joueurs.add(bean);
        this.joueurId++;
        JsonObject addMessage = createAddMessage(bean);
        sendToAllConnectedSessions(addMessage);
    }

    public void removeJoueur(int id) {
        bJoueur bean = getJoueurById(id);
        if (bean != null) {
        	this.joueurs.remove(bean);
            JsonProvider provider = JsonProvider.provider();
            JsonObject removeMessage = provider.createObjectBuilder()
                    .add("action", "remove")
                    .add("id", id)
                    .build();
            sendToAllConnectedSessions(removeMessage);
        }
    }

    private bJoueur getJoueurById(int id) {
        for (bJoueur bean : this.joueurs) {
            if (bean.getId() == id) {
                return bean;
            }
        }
        return null;
    }

    private JsonObject createAddMessage(bJoueur bean) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "add")
                .add("id", bean.getId())
                .add("joueur_id", bean.getJoueur_id())
                .add("joueur_pseudo", bean.getJoueur_pseudo())
                .add("joueur_email", bean.getJoueur_email())
                .build();
        return addMessage;
    }

    private void sendToAllConnectedSessions(JsonObject message) {
        for (Session session : this.sessions) {
            sendToSession(session, message);
        }
    }

    private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
        	this.sessions.remove(session);
            Logger.getLogger(JoueurSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
}