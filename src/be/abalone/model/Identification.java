package be.abalone.model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import be.abalone.dao.JoueurDAO;
import be.abalone.database.AbstractDAOFactory;
import be.abalone.database.DAOFactory;
import be.abalone.utilitaire.Utilitaire;

public class Identification {
	public static int connexion(Joueur joueur) {
		int rmail, rmdp, res = 0;
		String tmpMdp, tmpMail;
		rmail = validationEmail(joueur.getEmail());
		rmdp = validationMdp(joueur.getMdp());

		if (rmail != 0) { // Le mail est incorrect
			res = rmail;
		} else if (rmdp != 0) { // Le mdp est incorrect
			res = rmdp;
		} else { // Tout s'est bien passé
			tmpMdp = joueur.getMdp();
			tmpMail = joueur.getEmail();
			
			if(joueur.findBDD(tmpMail)) {  //Si on arrive à trouver un joueur correspondant en bdd
				if (joueur.checkPassword(tmpMdp)){ //Si le mdp fourni est correct
					res = 1; 
				}
			}
		}
		return res;
	}
	
	public static int inscription(Joueur joueur) {
		int rmail, rmdp, res = 0;
		String tmpMdp, tmpMail;
		rmail = validationEmail(joueur.getEmail());
		rmdp = validationMdp(joueur.getMdp());

		if (rmail != 0) { //Le mail est incorrect
			res = rmail;
		} else if (rmdp != 0) { //Le mdp est incorrect
			res = rmdp;
		} else { //Les duex sont bon
			tmpMdp = joueur.getMdp();
			tmpMail = joueur.getEmail();
			if(joueur.findBDD(tmpMail)) {  //Si on arrive à trouver un joueur correspondant en bdd ça veut dire que l'email existe déjà
				res = 5; 
			} else { //Tout est ok
				joueur.setMdp(Utilitaire.cryptPassword(tmpMdp)); //On crypte le mot de passe
				joueur.createBDD(); //Il est désormais enregistré dans la db
				res = 1;
			}
		}
		return res;
	}

	public static boolean estConnecte(HttpSession sessions, Cookie[] cookies) {
		DAOFactory adf = (DAOFactory) AbstractDAOFactory.getFactory(0);
		Cookie connect = null;
		Object oRes, oJoueur;
		boolean res = false;
		Joueur joueur = null;
		
		if( sessions != null ){
			oRes = sessions.getAttribute("connected"); //Si la session existe, alors ça veut dire qu'on est connecté, pas besoin de rentrer dans les conditions
			oJoueur = sessions.getAttribute("joueur");
			if(oRes != null) { res = (boolean) oRes;}
			if(oJoueur != null) { joueur = (Joueur) oJoueur;}
		}
		
		if (res == false || joueur == null) { // Si les sessions n'existent pas, on vérifie si un cookie existe
			if( cookies != null ){
				for (Cookie cki : cookies) {
					if (cki.getName().equals("user_email")) {
						connect = cki; //Si on trouve un cookie avecl'email dedans ça veut dire que l'user a demandé à rester connecté.
					}
				}
			}
			if (connect != null) { // SI les cookies existent
				joueur = ((JoueurDAO) adf.getJoueurDAO()).find(connect.getValue()); //On récupère l'user correspondant au mail
				sessions.setAttribute("connected", true);
				sessions.setAttribute("joueur", joueur); //On crée nos sessions, il est désormais connecté.
				res = true;
			}
		}
		return res;
	}

	public static int validationEmail(String email) {
		int res = 0;

		if (email != null && !email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
			res = 2;
		}
		return res;
	}

	public static int validationMdp(String mdp) {
		int res = 0;

		if (mdp != null) {
			if (mdp.length() < 8)
				res = 3;
			if (mdp.length() > 16)
				res = 4;
		}
		return res;
	}

}
