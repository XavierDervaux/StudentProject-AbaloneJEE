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

import be.abalone.bean.bMove;
import be.abalone.bean.bPartie;
import be.abalone.model.Partie;

@ApplicationScoped
public class PartieHandler {
    private Set<bPartie> parties  = new HashSet<>();


// Méthodes publiques
//---------------------------------------------------
    public void gestionOuverture(Session session, int uid, int couleur) {
		boolean partieExiste = false;
		
		bPartie partie = getPartieByUid(uid);
		if(partie != null){
			partieExiste = true;
			if(couleur == 0) {  partie.setSession_noir(session);  }
			else {  partie.setSession_blanc(session);  }
			sendReady(partie); 
		}
		if(partieExiste == false){
			bPartie bean = new bPartie();
			bean.setUid_partie(uid);
			
			if(couleur == 0) {  bean.setSession_noir(session);  }
			else {  bean.setSession_blanc(session);  }
			this.parties.add(bean);
		}
	}

	public void gestionFermeture(Session session) {
		gestionAbandon(session, true); //Se déconnecter en partie revient a une défaite par abandon. S'il n'y avait pas de partie en cours (déjà finie) il ne se passera rien
	}
	
	public void gestionAbandon(Session session, boolean estTimeOut) {
		int couleur = getCouleurBySession(session);
		bPartie bean = getPartieBySession(session);
		
		if(bean != null){ //La partie est toujours en cours
			Partie actuelle = Partie.trouverPartie(bean.getUid_partie());
			
			if(couleur == 0){ //C'est noir qui abandonne
				actuelle.fin(1, true);
				if(estTimeOut){ sendTimeOut(bean.getSession_blanc()); }//On prévient blanc
				else          { sendSurrend(bean.getSession_blanc()); }
			} else { //C'est blanc qui s'est déconnecté
				actuelle.fin(0, true);
				if(estTimeOut){ sendTimeOut(bean.getSession_noir()); } //On prévient noir
				else          { sendSurrend(bean.getSession_noir()); }
			}
			parties.remove(actuelle); //La partie est finie, pas de raison de la garder
		}
	}

	public void gestionMouvement(Session session, bMove moves) {
		int res, couleur = getCouleurBySession(session);
		bPartie bean = getPartieBySession(session);

		if(bean != null){ //La partie est toujours en cours
			Partie actuelle = Partie.trouverPartie(bean.getUid_partie());
			
			if(actuelle.estSonTour(couleur) && actuelle.isPeutBouger()){
				res = actuelle.gestionMouvement(couleur, moves);
				switch(res){
					case -1: sendUnallowed(bean);  break; 
					case 0 : sendVictory(bean, 0); break; //noir
					case 1 : sendVictory(bean, 1); break; //blanc
					case 2 : sendAllowed(bean, actuelle.getScoreNoir(), actuelle.getScoreBlanc());    break; 
				}
			} //Sinon on l'ignore simplement
		}
	}
	
	public void gestionFinTour(Session session) {
		int couleur = getCouleurBySession(session);
		bPartie bean = getPartieBySession(session);

		if(bean != null){ //La partie est toujours en cours
			Partie actuelle = Partie.trouverPartie(bean.getUid_partie());
			
			if(actuelle.estSonTour(couleur)){ //Si ce n'est pas son tour ça ne posera pas de réel problème mais ça sera une nuisance graphique.
				if(couleur == 0){ sendBeginTurn(bean.getSession_blanc()); }
				else { sendBeginTurn(bean.getSession_blanc()); }
				actuelle.setTour( actuelle.getTour() * -1 );
				actuelle.setPeutBouger(true);
			}
		}
	}
	

// Méthodes privées
//---------------------------------------------------	
	private void sendReady(bPartie bean) {
		JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
                .add("action", "pret")
                .build();
        sendToSession(bean.getSession_noir(), message);
        sendToSession(bean.getSession_blanc(), message);
    }
    
	private void sendTimeOut(Session session) {
		JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
                .add("action", "timeout")
                .build();
        sendToSession(session, message);
	}

	private void sendSurrend(Session session) {
		JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
                .add("action", "surrend")
                .build();
        sendToSession(session,  message);
	}
	
	private void sendBeginTurn(Session session) {
		JsonProvider provider = JsonProvider.provider();
	    JsonObject message = provider.createObjectBuilder()
	            .add("action", "beginTurn")
	            .build();
	    sendToSession(session, message);
	}
	
	private void sendAllowed(bPartie bean, int sNoir, int sBlanc) {
		JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
                .add("action", "allowed")
        		.add("pNoir", sNoir)
                .add("pBlanc",sBlanc)
                .build();
        sendToSession(bean.getSession_noir(),  message);
        sendToSession(bean.getSession_blanc(), message);
	}
	
	private void sendUnallowed(bPartie bean) {
		JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
                .add("action", "unallowed")
                .build();
        sendToSession(bean.getSession_noir(),  message);
        sendToSession(bean.getSession_blanc(), message);
	}

	private void sendVictory(bPartie bean, int couleur) {
		JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
                .add("action", "victoire")
                .add("gagnant", couleur) 
                .build();
        sendToSession(bean.getSession_noir(),  message);
        sendToSession(bean.getSession_blanc(), message);
	}
	
	private int getCouleurBySession(Session session){
		int res = 0;
		bPartie actuelle = getPartieBySession(session);
		
		if(actuelle.getSession_blanc().equals(session)){ 
			res = 1;
		}
		return res;
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
            Logger.getLogger(JoueurHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}