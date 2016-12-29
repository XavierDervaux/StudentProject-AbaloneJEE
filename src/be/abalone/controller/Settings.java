package be.abalone.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.abalone.model.Identification;
import be.abalone.model.Joueur;
import utilitaire.Utilitaire;

public class Settings  extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public Settings() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean estConnecte = Identification.estConnecte(request.getSession(), request.getCookies()); 
		
		if(estConnecte){ //On charge la page param�tre
			this.getServletContext().getRequestDispatcher("/WEB-INF/settings.jsp").forward(request, response);
    	} else { //N'est pas encore connect�, on affihce le formulaire de connexion/inscription
    		response.sendRedirect("/Abalone/index.html");
    	}
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sessions = request.getSession();
		int res = -1;
    	String output = null;
    	String mail   = Utilitaire.getValeurChamp(request, "emailSetting");
    	String mdp    = Utilitaire.getValeurChamp(request, "passwordSetting");
    	Joueur actuel = (Joueur) sessions.getAttribute("joueur"); //On est sur que le joueur existe car on v�rifie estConnect� avant d'arriver ici
		
    	res = changementMail(mail, actuel);
    	if(res <=0){ res = changementMdp(mdp, actuel); } //Si on a pas d�j� trouv� une erreur avec le mail, alors on v�rifie le mdp.
		
		if(res == 0) { //Au moins une op�ration s'est bien pass�
			actuel.updateBDD();
			sessions.setAttribute("joueur", actuel); //On met � jour la session
			response.sendRedirect("/Abalone/menu.html");return;
		} else { //Au moins une op�ration a �chou�, on retourne l'erreur
			output = affichageSettings(res);
			request.setAttribute("erreur", output);
			doGet(request, response);
		}
		
    }

	private int changementMdp(String mdp, Joueur actuel) {
		int res = -1;
		
		if(mdp != null && res<=0) { //changement de mdp  et si on n'a pas d�j� trouv� une erreur dans le mail
			res = Identification.validationMdp(mdp);
			if(res == 0) //Le mdp est valide
				actuel.setMdp(Utilitaire.cryptPassword(mdp));
		}
		return res;
	}

	private int changementMail(String mail, Joueur actuel) {
		int res = -1;

		if(mail != null    &&    !(actuel.getEmail().equals(mail)) ){ //Si l'utilisateur a entr� un nouvel email, diff�rent de celui qu'il utilise d�j� 
			res = Identification.validationEmail(mail);
			if(res == 0){//Le mail est valide
				if(actuel.findBDD(mail)) //Si on arrive � trouver un joueur correspondant en bdd �a veut dire que l'email existe d�j�
					res = 5; 
				else 
					actuel.setEmail(mail); 
			}
		}
		return res;
	}

	private String affichageSettings(int res) {
		String output = null;
		switch (res) { // Affiche du message de sortie
			case 2: output = "Merci de saisir une adresse mail valide."; break;
			case 3: output = "Le mot de passe doit contenir au moins 8 caract�res."; break;
			case 4: output = "Le mot de passe doit contenir moins de 16 caract�res."; break;
			case 5: output = "L'adresse E-mail saisie est d�j� enregistr�e."; break;
		}
		return output;
	}
}
