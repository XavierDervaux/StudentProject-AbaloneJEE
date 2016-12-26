package be.abalone.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.abalone.model.*;


public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Index() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean estConnecte = Identification.estConnecte(request.getSession(), request.getCookies()); 
		
		if(estConnecte){ //On redirige vers le menu 
			response.sendRedirect("/Abalone/menu.html"); 
		} else { //N'estpas encore connect�, on affihce le formulaire de connexion/inscription
			this.getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response); 
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sessions = request.getSession();
		int res=-1, type=0;//Type:0 lol
		boolean resterConnecte = false;
		String output=null, pseudo, mdp, email;
		Joueur input=null;
		
		if(Utilitaire.getBoolCheckbox(request, "estConnexion")){ type = 1; }
		else if(Utilitaire.getBoolCheckbox(request, "estInscription")){ type = 2; }
		else if(Utilitaire.getBoolCheckbox(request, "estDeconnexion")){ type = 3; } //Deconnexion
		
		if(type == 1){ //Connexion		
			email = Utilitaire.getValeurChamp(request, "emailConnection");
			mdp = Utilitaire.getValeurChamp(request, "passwordConnection");
			resterConnecte = Utilitaire.getBoolCheckbox(request, "rememberConnection");	
			input = new Joueur ("", mdp, email); 
			
			res = Identification.connexion(input); // Si la connexion r�ussi, les propri�t�s de inputs seront modifi�es pour obtenir un Joueur valide
			output = affichageConnexion(res);
		} else if (type == 2) { //Inscription
			pseudo = Utilitaire.getValeurChamp(request, "pseudoInscription");
			email = Utilitaire.getValeurChamp(request, "emailInscription");
			mdp = Utilitaire.getValeurChamp(request, "passwordInscription");
			input = new Joueur (pseudo, mdp, email); 
			
			res = Identification.inscription(input);
			output = affichageConnexion(res);
		} else { //Deconnexion
			sessions.removeAttribute("connected");
			sessions.removeAttribute("joueur");
			Utilitaire.unsetCookie(response, "user_email");
		}
		
		if (res == 1) { // On d�finit les sessions et le cookie
			sessions.setAttribute("connected", true);
			sessions.setAttribute("joueur", input);
			if (resterConnecte) { // Si l'utilisateur le souhaite, on lui cr�e un cookie pour ne pas qu'il se relogg a chauqe fois
				Utilitaire.setCookie(response, "user_email", input.getEmail(), 60 * 60 * 24 * 365);
			}
			response.sendRedirect("/Abalone/menu.html"); // On est connect�, on redirige vers le menu.
		} else if (output != null) {// Ca ne s'est pas bien pass�, on recharge la page avec le message d'erreur...
			request.setAttribute("erreur", output);
			doGet(request, response);
		}
	}
	
	private String affichageConnexion(int res) {
		String output = null;
		switch (res) { // Affiche du message de sortie
			case 0: output = "Le mot de passe ou l'adresse mail saisie est incorrecte."; break;
			case 2: output = "Merci de saisir une adresse mail valide."; break;
			case 3: output = "Le mot de passe doit contenir au moins 8 caract�res."; break;
			case 4: output = "Le mot de passe doit contenir moins de 16 caract�res."; break;
			case 5: output = "L'adresse E-mail saisie est d�j� enregistr�e."; break;
		}
		return output;
	}

}
