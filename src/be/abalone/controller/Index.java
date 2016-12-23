package be.abalone.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Index() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean estConnecte = false; 
		if(estConnecte){ //On redirige vers le menu 
			response.sendRedirect("/Abalone/Menu.html"); 
		} else { //N'estpas encore connecté, on affihce le formulaire de connexion/inscription
			this.getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response); 
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
