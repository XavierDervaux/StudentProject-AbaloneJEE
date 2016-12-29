package be.abalone.websocket;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;

import be.abalone.bean.bJoueur;

@ApplicationScoped
public class JoueurSessionHandler {
    private int joueurId = 1;
    private final Set<Session> sessions = new HashSet<>();
    private final Set<bJoueur> joueurs = new HashSet<>();


// Méthodes publiques
//---------------------------------------------------
    //---------------------------------------------------
    //Gestion des sessions
    public void addSession(Session session) { //On ajoute l'objet a notre session et on le renvoie au client pour qu'il puisse l'afficher a son tour
        this.sessions.add(session);
        for (bJoueur bean : this.joueurs) {
            JsonProvider provider = JsonProvider.provider();
            JsonObject addMessage = provider.createObjectBuilder()
                    .add("action", "add")
                    .add("id", bean.getId())
                    .add("pseudo", bean.getJoueur_pseudo())
                    .add("email", bean.getJoueur_email())
                    .build();
            sendToSession(session, addMessage);
        }
    }
    public void removeSession(Session session) {
    	sessions.remove(session);
    	removeJoueur(session);
    }

    //---------------------------------------------------
    //Gestion d'envois
	public void checkDoublon(bJoueur bean) {
		boolean dejaConnect = false;
		
		for(bJoueur tmp :  joueurs){
			if( bean.getJoueur_email().equals( tmp.getJoueur_email() ) ){ //le mail est unique, si on en trouve un identique c'est quec'est un doublon -> LE joueur est déjà connecté.
				dejaConnect = true; break; //Inutile de continuer a parcourir si on a déjà trouvé ce qu'on cherchait.
			}
		}
		
		if(dejaConnect){
	        JsonProvider provider = JsonProvider.provider();
	        JsonObject addMessage = provider.createObjectBuilder()
	                .add("action", "dejaConnect")
	                .add("pseudo", bean.getJoueur_pseudo())
	                .build();
	        sendToSession(bean.getSession(), addMessage);
		} else {
			addJoueur(bean);
		}
	}

	public void sendDemande(int id, Session session) {
    	bJoueur source = getJoueurBySession(session);
    	bJoueur destin = getJoueurById(id);
    	
		JsonProvider provider = JsonProvider.provider();
        JsonObject demande = provider.createObjectBuilder()
                .add("action", "demande")
                .add("id_source", source.getId())
                .add("pseudo_source", source.getJoueur_pseudo())
                .build();
        sendToSession(destin.getSession(), demande);
	}  

	public void sendConfirmation(int id, boolean confirm, Session session) {
    	bJoueur source = getJoueurBySession(session);
    	bJoueur destin = getJoueurById(id);
    	
		JsonProvider provider = JsonProvider.provider();
        JsonObject reponse = provider.createObjectBuilder()
                .add("action", "reponse")
                .add("source", source.getId())
                .add("pseudo_source", source.getJoueur_pseudo())
                .add("confirm", confirm)
                .build();
        sendToSession(destin.getSession(), reponse);
	} 


// Méthodes privées
//---------------------------------------------------	
    private void addJoueur(bJoueur bean) { //On ajoute un bJoueur et on le fait savoir à chaque client
        bean.setId(this.joueurId); //On définit l'id dont on se sert pour l'identification
        this.joueurs.add(bean);
        this.joueurId++;
        
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "add")
                .add("id", bean.getId())
                .add("pseudo", bean.getJoueur_pseudo())
                .add("email", bean.getJoueur_email())
                .build();
        sendToAllConnectedSessions(addMessage);
    }
    
    private void removeJoueur(Session session) {
    	bJoueur del = getJoueurBySession(session);
    	if(del != null){
			this.joueurs.remove(del);
			JsonProvider provider = JsonProvider.provider();
	        JsonObject removeMessage = provider.createObjectBuilder()
	                .add("action", "remove")
	                .add("id", del.getId())
	                .build();
	        sendToAllConnectedSessions(removeMessage);
        }
    }
    
    private bJoueur getJoueurById(int id) {
    	bJoueur res = null;
        for (bJoueur bean : this.joueurs) {
            if (bean.getId() == id ){
                res = bean;
                break; //Inutile de parcourir le reste de la liste si on a trouvé ce qu'on cherchait
            }
        }
        return res;
    }
    
    private bJoueur getJoueurBySession(Session session) {
    	bJoueur res = null;
        for (bJoueur bean : this.joueurs) {
            if (bean.getSession().equals(session) ){
                res = bean;
                break; //Inutile de parcourir le reste de la liste si on a trouvé ce qu'on cherchait
            }
        }
        return res;
    }

    private void sendToAllConnectedSessions(JsonObject message) {
        for (Session session : this.sessions) {
            sendToSession(session, message);
        }
    }

    private void sendToSession(Session session, JsonObject message) {
        try {
        	System.out.println("Envoi - " + message.getString("action") + "  " + message.toString());
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
        	this.sessions.remove(session);
            Logger.getLogger(JoueurSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}