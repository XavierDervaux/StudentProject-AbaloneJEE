package be.abalone.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.abalone.model.Identification;

public class Matchmaking extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Matchmaking() {
        super();
    }
    ///Cette servlet fonctionne avec un WebSocket, elle n'effectue aucun test, tout est geré par le socket. (JoueurWebSocketServer)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean estConnecte = Identification.estConnecte(request.getSession(), request.getCookies()); 
		
		if(estConnecte){ //S'il est connecté on peut affiche le marchmaking
    		this.getServletContext().getRequestDispatcher("/WEB-INF/matchmaking.jsp").forward(request, response);
    	} else { //Sinon on le redirige vers la connexion
    		response.sendRedirect("/Abalone/index.html");
    	}
	}
}
