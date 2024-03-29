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

import be.abalone.bean.bJoueur;
import be.abalone.websocket.JoueurHandler;
    
@ServerEndpoint("/joueurSocket") //D�finis l'adresse que les clients doivent donner pour se connecter � la WebSocket
public class JoueurWebSocketServer {
	@Inject
	private JoueurHandler sessionHandler;
	
	@OnOpen
	public void open(Session session) { //Ouverture de session, un nouveau client vient de se connecter
		this.sessionHandler.addSession(session);
	}
	
    @OnClose
    public void close(Session session) { //Un client s'est d�connect�
    	this.sessionHandler.removeSession(session);
    }

    @OnError
    public void onError(Throwable error) { //Un truc � explos�, on le logg
        Logger.getLogger(JoueurWebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }

    @OnMessage
    public void OnMessage(String message, Session session) { //On a re�u un message
        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject(); //R�cup�ration du message

            if ("add".equals(jsonMessage.getString("action"))) { 
                bJoueur bean = new bJoueur();
                bean.setSession(session);
                bean.setJoueur_pseudo(jsonMessage.getString("pseudo"));
                bean.setJoueur_email(jsonMessage.getString("email"));
                this.sessionHandler.gestionDoublon(bean);
            }
            
            if("demande".equals(jsonMessage.getString("action"))) {
                int destId = (int) jsonMessage.getInt("destinataire");
                this.sessionHandler.gestionDemande(destId, session);
            }
            
            if("reponse".equals(jsonMessage.getString("action"))) {
        		int destId = (int) jsonMessage.getInt("destinataire");
            	boolean confirm = (boolean) jsonMessage.getBoolean("confirm");
            	this.sessionHandler.gestionConfirmation(destId, confirm, session);
            }
        } catch (Exception e){
			e.printStackTrace();
        }
    }
}  
