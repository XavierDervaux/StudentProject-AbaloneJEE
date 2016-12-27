package be.abalone.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import be.abalone.model.Achievement;
import be.abalone.model.Identification;
import be.abalone.model.Joueur;

public class Succes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Succes() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean estConnecte = Identification.estConnecte(request.getSession(), request.getCookies()); 
		
		if(estConnecte){ //On charge la page paramètre
	    	getInfo(request); //Va parametrer request comme il faut pour pouvoir etre retourné à la page
			this.getServletContext().getRequestDispatcher("/WEB-INF/succes.jsp").forward(request, response);
    	} else { //N'est pas encore connecté, on affihce le formulaire de connexion/inscription
    		response.sendRedirect("/Abalone/index.html");
    	}
	}

	private void getInfo(HttpServletRequest request){
		HttpSession sessions = request.getSession();
    	Joueur actuel = (Joueur) sessions.getAttribute("joueur");
		List<Achievement> listA = Achievement.findAllBDD(); //Ne sera jamais null
		
		for(Achievement tmp1 : listA){
			if(actuel.getAchievs() != null){
				for(Achievement tmp2 : actuel.getAchievs()){
					if(tmp1.getId() == tmp2.getId()){ //Si au sein de la liste il existe un achievement déjà effectué par le joueur.
						listA.remove(tmp1);//On le supprime
					}
				}
			}
		}
		
		request.setAttribute("accomplis", actuel.getAchievs() );
		request.setAttribute("pasEncoreAccomplis", listA );
	}

}
