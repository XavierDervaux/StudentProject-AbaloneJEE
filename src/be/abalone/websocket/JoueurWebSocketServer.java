package be.abalone.websocket;

import java.io.StringReader;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.server.ServerEndpoint;
import be.abalone.websocket.JoueurSessionHandler;
import bean.bJoueur;
    
@ServerEndpoint("/joueurSocket")
public class JoueurWebSocketServer {
	@Inject
	private JoueurSessionHandler sessionHandler;
	
	@OnOpen
	public void open(Session session) { //Ouverture de session, un nouveau client vient de se connecter
		this.sessionHandler.addSession(session);
	}
	
    @OnClose
    public void close(Session session) { //Un client s'est déconnecté
    	this.sessionHandler.removeSession(session);
    }

    @OnError
    public void onError(Throwable error) { //Un truc à explosé, on le logg
        Logger.getLogger(JoueurWebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }

    @OnMessage
    public void OnMessage(String message, Session session) { //On a reçu un message
        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject(); //Récupération du message

            if ("add".equals(jsonMessage.getString("action"))) { 
                bJoueur bean = new bJoueur();
                bean.setSession(session);
                bean.setJoueur_pseudo(jsonMessage.getString("pseudo"));
                bean.setJoueur_email(jsonMessage.getString("email"));
 
                this.sessionHandler.checkDoublon(bean);
            }
            
            if("demande".equals(jsonMessage.getString("action"))) {
            	try{
	                int id = (int) jsonMessage.getInt("destinataire");
	                this.sessionHandler.sendDemande(id, session);
	        	} catch (Exception e) {
	        		e.printStackTrace();
	        	}
            }
            
            if("reponse".equals(jsonMessage.getString("action"))) {
            	try{ //L'erreur n'est pas supposée arriver mais si par malheur elle se produit elle fera planter le serveur
            		int id = (int) jsonMessage.getInt("destinataire");
                	boolean confirm = (boolean) jsonMessage.getBoolean("confirm");
                	this.sessionHandler.sendConfirmation(id, confirm,  session);
            	} catch (Exception e) {
            		e.printStackTrace();
            	}
            }
        } catch (Exception e){
        	System.out.println(e);
        }
    }
}  
