package be.abalone.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.abalone.model.Identification;
import be.abalone.model.Joueur;
import be.abalone.model.Utilitaire;

public class Settings  extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public Settings() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean estConnecte = Identification.estConnecte(request.getSession(), request.getCookies()); 
		
		if(estConnecte){ //On charge la page paramètre
			this.getServletContext().getRequestDispatcher("/WEB-INF/setting.jsp").forward(request, response);
    	} else { //N'est pas encore connecté, on affihce le formulaire de connexion/inscription
    		response.sendRedirect("/Abalone/index.html");
    	}
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sessions = request.getSession();
    	String mail = null, mdp = null;
    	Joueur actuel = (Joueur) sessions.getAttribute("joueur"); //On est sur que le joueur existe car on vérifie estConnecté avant d'arriver ici

		mail = Utilitaire.getValeurChamp(request, "emailInscription");
		mdp = Utilitaire.getValeurChamp(request, "passwordInscription");
		actuel.setEmail(mail);
		actuel.setMdp(Utilitaire.cryptPassword(mdp));
		
		actuel.updateBDD();
		sessions.setAttribute("joueur", actuel); //On met à jour la session
		response.sendRedirect("/Abalone/menu.html");
    }

}
