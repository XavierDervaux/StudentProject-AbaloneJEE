package be.abalone.controller;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.abalone.model.Identification;
import be.abalone.model.Joueur;
import be.abalone.model.Partie;
import utilitaire.Utilitaire;

public class Jouer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private boolean estCorrect = false;
       
    public Jouer() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean estConnecte = Identification.estConnecte(request.getSession(), request.getCookies()); 
		
		if(estConnecte){  
			if(this.estCorrect){//On ne peut pas laisser l'user voir la page s'il n'a pas été validé par le post d'abord
				this.getServletContext().getRequestDispatcher("/WEB-INF/jouer.jsp").forward(request, response);
			} else { //Est arrivé ici par erreur
	    		response.sendRedirect("/Abalone/menu.html");return;
	    	}
    	} else { //N'est pas encore connecté, on affihce le formulaire de connexion/inscription
    		response.sendRedirect("/Abalone/index.html");
    	}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int nbrJ1, nbrJ2;
		Joueur joueur1, joueur2;
		Partie play = null;
		Random rand = new Random();
		String mJ1 = Utilitaire.getValeurChamp(request, "joueur1");
		String mJ2 = Utilitaire.getValeurChamp(request, "joueur2"); //On récupère le mail des 2 joueurs
		
		if(mJ1 != null && mJ2 != null && !(mJ1.equals(mJ2)) ){
			joueur1 = new Joueur("","",mJ1); joueur1.findBDD(mJ1);
			joueur2 = new Joueur("","",mJ2); joueur2.findBDD(mJ2); //On récupère les joueurs en BDD
			if(joueur1.getId() != 0 && joueur2.getId() != 0){ //Si tout est correct
				play = Partie.trouverPartie(joueur1, joueur2); 
				if(play == null) {//Si on ne peut trouver aucune partie existante pour ces deux joueurs, on la crée. Si on en trouve une ça veut dire que l'autre joueur est passé en premier, on la récupère
					nbrJ1 = rand.nextInt(100);
					nbrJ2 = rand.nextInt(100);
					
					if(nbrJ1 >= nbrJ2){  play = new Partie(joueur1, joueur2);  } //On décide aléatoirement qui sera blanc et qui sera noir
					else {  play = new Partie(joueur2, joueur1);  }
					Partie.listParties.add(play);
				}
				request.setAttribute("id_partie", play.getUid());
				request.setAttribute("noir", play.getNoir().getEmail());
				request.setAttribute("blanc", play.getBlanc().getEmail());
				this.estCorrect = true;
			}
		}
		doGet(request, response);
	}
}
