package be.abalone.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.abalone.database.AbstractDAOFactory;
import be.abalone.database.DAOFactory;
import be.abalone.database.SQLRequest;
import be.abalone.model.*;

public class Index extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Index() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DAOFactory adf = (DAOFactory) AbstractDAOFactory.getFactory(0);
		PrintWriter out = response.getWriter();
		
		out.println(SQLRequest.getInstance()+"\n");
		out.println(adf.toString());
		
		Achievement test1 = new Achievement("ACV_TEST", "Un test réussi", "Réussir à se connecter a la bdd avec succès.");
		//Historique test2 = new Historique();
		//Joueur test3 = new Joueur();
		
		adf.getAchievementDAO().create(test1);
		
		/*Boolean estConnecte = false;
		if(estConnecte){ //On redirige vers le menu
    		response.sendRedirect("/Abalone/Menu.html");
    	} else { //N'est pas encore connecté, on affihce le formulaire de connexion/inscription
    		this.getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    	}*/
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
