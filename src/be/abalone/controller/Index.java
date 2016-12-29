package be.abalone.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.abalone.model.*;
import utilitaire.Utilitaire;


public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Index() {
		super(); 
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean estConnecte = Identification.estConnecte(request.getSession(), request.getCookies()); 
		
		if(estConnecte){ //On redirige vers le menu 
			response.sendRedirect("/Abalone/menu.html"); 
		} else { //N'estpas encore connecté, on affichee le formulaire de connexion/inscription
			this.getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response); 
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		if(Utilitaire.getBoolCheckbox(request, "estConnexion"))
			connexion(request, response);
		else if(Utilitaire.getBoolCheckbox(request, "estInscription"))
			inscription(request);
		else
			deconnexion(request, response);	
		doGet(request, response); //doGet se chargera de l'erreur s'il y en a une ou fera la redirection s'il n'y en a pas.
	}
	
	private void connexion(HttpServletRequest request, HttpServletResponse response){	
		HttpSession sessions = request.getSession();	
		String output = null;
		String mdp    = Utilitaire.getValeurChamp(request, "passwordConnection");
		String email  = Utilitaire.getValeurChamp(request, "emailConnection");
		Joueur input  = new Joueur ("", mdp, email); 
		boolean resterConnecte = Utilitaire.getBoolCheckbox(request, "rememberConnection");	
		int res = Identification.connexion(input); // Si la connexion réussi, les propriétés de inputs seront modifiées pour obtenir un Joueur valide
		
		if(res == 1){ //Tout s'est bien passé, l'user est co, on a plus qu'a définir ses sessions et cookies
			sessions.setAttribute("connected", true);
			sessions.setAttribute("joueur", input);
			if (resterConnecte) { // Si l'utilisateur le souhaite, on lui crée un cookie pour ne pas qu'il se relogg a chaque fois
				Utilitaire.setCookie(response, "user_email", input.getEmail(), 60*60*24*365);
			}
		} else { //Une erreur est survenue, on défini le message d'erreur pour le retour sur la page.
			output = affichageConnexion(res);
			request.setAttribute("erreurConn", output);
		}
	}
	
	private void inscription(HttpServletRequest request){		
		HttpSession sessions = request.getSession();
		String output = null;
		String pseudo = Utilitaire.getValeurChamp(request, "pseudoInscription");
		String mdp    = Utilitaire.getValeurChamp(request, "passwordInscription");
		String email  = Utilitaire.getValeurChamp(request, "emailInscription");
		Joueur input  = new Joueur (pseudo, mdp, email); 
		int res = Identification.inscription(input);

		if(res == 1){ //L'inscription s'est bien passé, on défini les sessions.
			sessions.setAttribute("connected", true);
			sessions.setAttribute("joueur", input);
		} else { //Une erreur est survenue, on défini le message d'erreur pour le retour sur la page.
			output = affichageConnexion(res);
			request.setAttribute("erreurInscr", output);
		}
	}
	
	private void deconnexion(HttpServletRequest request, HttpServletResponse response){
		HttpSession sessions = request.getSession();
		sessions.removeAttribute("connected");
		sessions.removeAttribute("joueur"); //On supprime ses sessions
		Utilitaire.unsetCookie(response, "user_email"); //Et son cookie éventuel
	}
	
	private String affichageConnexion(int res) {
		String output = null;
		switch (res) { // Affiche du message de sortie
			case 0: output = "Le mot de passe ou l'adresse mail saisie est incorrecte."; break;
			case 2: output = "Merci de saisir une adresse mail valide."; break;
			case 3: output = "Le mot de passe doit contenir au moins 8 caractères."; break;
			case 4: output = "Le mot de passe doit contenir moins de 16 caractères."; break;
			case 5: output = "L'adresse E-mail saisie est déjà enregistrée."; break;
		}
		return output;
	}

}