package be.abalone.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.abalone.model.Identification;

@WebServlet("/Matchmaking")
public class Matchmaking extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Matchmaking() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean estConnecte = Identification.estConnecte(request.getSession(), request.getCookies()); 
		
		if(estConnecte){ //S'il est connecté on peut affiche le menu principal
    		this.getServletContext().getRequestDispatcher("/WEB-INF/matchmaking.jsp").forward(request, response);
    	} else { //Sinon on le redirige vers la connexion
    		response.sendRedirect("/Abalone/index.html");
    	}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
