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
		
		if(estConnecte){ //On charge la page param�tre
			this.getServletContext().getRequestDispatcher("/WEB-INF/settings.jsp").forward(request, response);
    	} else { //N'est pas encore connect�, on affihce le formulaire de connexion/inscription
    		response.sendRedirect("/Abalone/index.html");
    	}
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sessions = request.getSession();
		int res = -1;
    	String mail = null, mdp = null, output = null;
    	Joueur actuel = (Joueur) sessions.getAttribute("joueur"); //On est sur que le joueur existe car on v�rifie estConnect� avant d'arriver ici

		mail = Utilitaire.getValeurChamp(request, "emailSetting");
		mdp = Utilitaire.getValeurChamp(request, "passwordSetting");
		
		if(mail != null) { //Changement de mail 
			res = Identification.validationEmail(mail);
			if(res == 0){//Le mail est valide
				if( !( actuel.getEmail().equals(mail) ) && actuel.findBDD(mail)) {  //Si on arrive � trouver un joueur correspondant en bdd �a veut dire que l'email existe d�j�
					res = 5; 
				} else {
					actuel.setEmail(mail); 
				}
			}
		}
		if(mdp != null && res<=0) { //changement de mdp  et si on n'a pas d�j� trouv� une erreur dans le mail
			res = Identification.validationMdp(mdp);
			if(res == 0){//Le mdp est valide
				actuel.setMdp(Utilitaire.cryptPassword(mdp));
			}
		}
		
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
