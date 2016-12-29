package be.abalone.websocket;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;

import be.abalone.bean.bPartie;
import be.abalone.model.Partie;

@ApplicationScoped
public class PartieSessionHandler {
    private final Set<bPartie> parties  = new HashSet<>();


// Méthodes publiques
//---------------------------------------------------
    public void gestionOuverture(Session session, int uid, String couleur) {
		boolean partieExiste = false;
		
		bPartie partie = getPartieByUid(uid);
		if(partie != null){
			partieExiste = true;
			if(couleur.equals("noir")) {  partie.setSession_noir(session);  }
			else {  partie.setSession_blanc(session);  }
			sendReady(partie); 
		}
		if(partieExiste == false){
			bPartie bean = new bPartie();
			bean.setUid_partie(uid);
			
			if(couleur.equals("noir")) {  bean.setSession_noir(session);  }
			else {  bean.setSession_blanc(session);  }
			this.parties.add(bean);
		}
	}

	public void gestionFermeture(Session session) {
		bPartie actuelle = getPartieBySession(session);
		
		if(actuelle != null){ 
			Partie estFinie = Partie.trouverPartie(actuelle.getUid_partie());
			if(estFinie != null){ //La connexion s'est coupée prématurement
				if(actuelle.getSession_noir().equals(session)){ //C'est noir qui s'est déconnecté
					sendTimeOut(actuelle.getSession_blanc()); //On prévient blanc
					estFinie.finForfait(0);
				} else { //C'est blanc qui s'est déconnecté
					sendTimeOut(actuelle.getSession_noir()); //On prévient noir
					estFinie.finForfait(1);
				}
			}//Sinon la partie est finie normalement
			parties.remove(actuelle);
		}
	}


// Méthodes privées
//---------------------------------------------------	
	private void sendReady(bPartie bean) {
		JsonProvider provider = JsonProvider.provider();
        JsonObject readyMessage = provider.createObjectBuilder()
                .add("action", "pret")
                .build();
        sendToSession(bean.getSession_noir(), readyMessage);
        sendToSession(bean.getSession_blanc(), readyMessage);
    }
    
	private void sendTimeOut(Session session) {
		JsonProvider provider = JsonProvider.provider();
        JsonObject readyMessage = provider.createObjectBuilder()
                .add("action", "timeout")
                .build();
        sendToSession(session, readyMessage);
	}
	
    private bPartie getPartieByUid(int uid) {
    	bPartie res = null;
        for (bPartie bean : this.parties) {
            if (bean.getUid_partie() == uid){
                res = bean;
                break; //Inutile de parcourir le reste de la liste si on a trouvé ce qu'on cherchait
            }
        }
        return res;
    }
    
    private bPartie getPartieBySession(Session session) {
    	bPartie res = null;
        for (bPartie bean : this.parties) {
            if (bean.getSession_noir().equals(session)  ||  bean.getSession_blanc().equals(session)){
                res = bean;
                break; //Inutile de parcourir le reste de la liste si on a trouvé ce qu'on cherchait
            }
        }
        return res;
    }

    private void sendToSession(Session session, JsonObject message) {
        try {
        	System.out.println("Envoi - " + message.getString("action") + "  " + message.toString());
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            Logger.getLogger(JoueurSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}