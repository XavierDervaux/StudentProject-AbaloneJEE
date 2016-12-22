package be.abalone.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Menu extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Menu() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Boolean estConnecte = true;
		if(estConnecte){ //S'il est connect� on peut affiche le menu principal
    		this.getServletContext().getRequestDispatcher("/WEB-INF/menu.jsp").forward(request, response);
    	} else { //Sinon on le redirige vers la connexion
    		response.sendRedirect("/Abalone/Index.html");
    	}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}