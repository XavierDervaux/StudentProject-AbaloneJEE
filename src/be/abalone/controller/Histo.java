package be.abalone.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.abalone.model.Identification;

public class Histo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public Histo() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean estConnecte = Identification.estConnecte(request.getSession(), request.getCookies()); 
		
		if(estConnecte){ //On charge la page paramètre
			this.getServletContext().getRequestDispatcher("/WEB-INF/historique.jsp").forward(request, response);
    	} else { //N'est pas encore connecté, on affihce le formulaire de connexion/inscription
    		response.sendRedirect("/Abalone/index.html");
    	}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
