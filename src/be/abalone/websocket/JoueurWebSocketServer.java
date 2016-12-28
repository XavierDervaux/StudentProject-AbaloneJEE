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
    
@ServerEndpoint("/joueurSocket")
public class JoueurWebSocketServer {
	@Inject
	private JoueurSessionHandler sessionHandler;
	
	@OnOpen
	public void open(Session session) { //Ouverture de session, un nouveau client vient de se connecter
		this.sessionHandler.addSession(session);
		System.out.println("On re�oit");
	}
	
    @OnClose
    public void close(Session session) { //Un client s'est d�connect�
    	this.sessionHandler.removeSession(session);
		System.out.println("D�co");
    }

    @OnError
    public void onError(Throwable error) { //Un truc � explos�, on le logg
        Logger.getLogger(JoueurWebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }

    @OnMessage
    public void handleMessage(String message, Session session) { //On a re�u un message
		System.out.println("messageentrant");
        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject(); //R�cup�ration du message

            if ("add".equals(jsonMessage.getString("action"))) { //On fait un truc, ici c'et l'exemple mais �a peut etre ni'mporte qiuoi d'autre
                bJoueur bean = new bJoueur();
                bean.setSession(session);
                bean.setJoueur_pseudo(jsonMessage.getString("pseudo"));
                bean.setJoueur_email(jsonMessage.getString("email"));
 
                this.sessionHandler.addJoueur(bean);
            }
            
            if("demande".equals(jsonMessage.getString("action"))) {
                int id = (int) jsonMessage.getInt("destinataire");
                this.sessionHandler.sendDemande(id, session);
            }
            
            if("reponse".equals(jsonMessage.getString("action"))) {
                int id = (int) jsonMessage.getInt("destinataire");
                boolean confirm = (boolean) jsonMessage.getBoolean("confirm");
                this.sessionHandler.sendConfirmation(id, confirm,  session);
            }
        } catch (Exception e){
        	System.out.println(e);
        }
    }
}  
