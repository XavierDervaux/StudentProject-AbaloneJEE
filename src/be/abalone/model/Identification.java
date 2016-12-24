package be.abalone.model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import be.abalone.dao.JoueurDAO;
import be.abalone.database.AbstractDAOFactory;
import be.abalone.database.DAOFactory;

public class Identification {

// Méthode publiques
// ---------------------------------------------------
	public static int connexion(Joueur joueur) {
		int rmail, rmdp, res = 0;
		rmail = validationEmail(joueur);
		rmdp = validationMdp(joueur);

		if (rmail != 0) { // Le mail est incorrect
			res = rmail;
		} else if (rmdp != 0) { // Le mdp est incorrect
			res = rmdp;
		} else { // Tout s'est bien passé
			joueur.setMdp(Utilitaire.cryptPassword(joueur.getMdp())); // On crypte le mot de passe
			if(joueur.findBDD()) { res = 1; } //Si on arrive à trouver un joueur correspondant en bdd
		}
		return res;
	}
	
	public static int inscription(Joueur joueur) {
		int rmail, rmdp, res = 0;
		rmail = validationEmail(joueur);
		rmdp = validationMdp(joueur);

		if (rmail != 0) { //Le mail est incorrect
			res = rmail;
		} else if (rmdp != 0) { //Le mdp est incorrect
			res = rmdp;
		} else { //Tout est ok, on enregistre en bdd
			joueur.setMdp(Utilitaire.cryptPassword(joueur.getMdp())); //On crypte le mot de passe
			joueur.createBDD(); //Il est désormais enregistré dans la db
			res = 1;
		}
		return res;
	}

	public static boolean estConnecte(HttpSession sessions, Cookie[] cookies) {
		DAOFactory adf = (DAOFactory) AbstractDAOFactory.getFactory(0);
		Cookie connect = null;
		boolean res = (boolean) sessions.getAttribute("connected");
		Joueur joueur = (Joueur) sessions.getAttribute("joueur");

		if (!(res && joueur != null)) { // Si les sessions n'existent pas
			for (Cookie cki : cookies) {
				if (cki.getName().equals("user_email")) {
					connect = cki;
				}
			}
			if (connect != null) { // SI les cookies existent
				joueur = ((JoueurDAO) adf.getJoueurDAO()).find(connect.getValue());
				sessions.setAttribute("connected", true);
				sessions.setAttribute("joueur", joueur);
				res = true;
			}
		}
		return res;
	}

// Méthode privées
// ---------------------------------------------------
	private static int validationEmail(Joueur joueur) {
		int res = 0;

		if (joueur.getEmail() != null && !joueur.getEmail().matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
			res = 2;
		}
		return res;
	}

	private static int validationMdp(Joueur joueur) {
		int res = 0;

		if (joueur.getMdp() != null) {
			if (joueur.getMdp().length() < 8)
				res = 3;
			if (joueur.getMdp().length() > 16)
				res = 4;
		}
		return res;
	}

}
