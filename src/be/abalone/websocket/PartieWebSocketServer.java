package be.abalone.websocket;

import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/partieSocket")
public class PartieWebSocketServer {
	@Inject
	private PartieSessionHandler sessionHandler;
	
	@OnOpen
	public void open(Session session) { 
		//System.out.println("Connexion entrante.");
	}
	
    @OnClose
    public void close(Session session) { 
    	sessionHandler.gestionFermeture(session);
    }

    @OnError
    public void onError(Throwable error) { 
        Logger.getLogger(PartieWebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }

    @OnMessage
    public void OnMessage(String message, Session session) { 
        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject(); 

            if ("add".equals(jsonMessage.getString("action"))) { //uid, couleur
                int uid = jsonMessage.getInt("uid");
                String couleur = jsonMessage.getString("couleur");
 
                this.sessionHandler.gestionOuverture(session, uid, couleur);
            }
        } catch (Exception e){
        	System.out.println(e);
        }
    }
}  