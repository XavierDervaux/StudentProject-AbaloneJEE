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
import be.abalone.bean.bMove;

@ServerEndpoint("/partieSocket")
public class PartieWebSocketServer {
	@Inject
	private PartieHandler sessionHandler;
	
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
                int couleur = jsonMessage.getInt("couleur");
                this.sessionHandler.gestionOuverture(session, uid, couleur);
            }
            
            if ("forfait".equals(jsonMessage.getString("action"))) {  
                this.sessionHandler.gestionAbandon(session, false);
            }
            
            if ("move".equals(jsonMessage.getString("action"))) {  
            	bMove bean = new bMove();
            	bean.setType(jsonMessage.getInt("type"));
            	bean.setOri_x1(jsonMessage.getInt("ori_x1")); //Jusqu'a 3 billes pour bouger simultanément, source et destination
            	bean.setOri_y1(jsonMessage.getInt("ori_y1"));
            	bean.setOri_x2(jsonMessage.getInt("ori_x2"));
            	bean.setOri_y2(jsonMessage.getInt("ori_y2"));
            	bean.setOri_x3(jsonMessage.getInt("ori_x3"));
            	bean.setOri_y3(jsonMessage.getInt("ori_y3"));        	
            	bean.setDes_x1(jsonMessage.getInt("des_x1")); 
            	bean.setDes_y1(jsonMessage.getInt("des_y1"));
            	bean.setDes_x2(jsonMessage.getInt("des_x2"));
            	bean.setDes_y2(jsonMessage.getInt("des_y2"));
            	bean.setDes_x3(jsonMessage.getInt("des_x3"));
            	bean.setDes_y3(jsonMessage.getInt("des_y3"));       
                this.sessionHandler.gestionMouvement(session, bean);
            }
            
            if("finTour".equals(jsonMessage.getString("action"))){
                this.sessionHandler.gestionFinTour(session);
            }
        } catch (Exception e){
        	System.out.println(e);
        }
    }
}