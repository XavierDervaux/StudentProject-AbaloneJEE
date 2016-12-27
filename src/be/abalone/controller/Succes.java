package be.abalone.controller;

import java.io.IOException;
import java.util.Iterator;
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
		
		if(estConnecte){ //On charge la page param�tre
	    	getInfo(request); //Va parametrer request comme il faut pour pouvoir etre retourn� � la page
			this.getServletContext().getRequestDispatcher("/WEB-INF/succes.jsp").forward(request, response);
    	} else { //N'est pas encore connect�, on affihce le formulaire de connexion/inscription
    		response.sendRedirect("/Abalone/index.html");
    	}
	}

	private void getInfo(HttpServletRequest request){
		HttpSession sessions = request.getSession();
    	Joueur actuel = (Joueur) sessions.getAttribute("joueur");
		List<Achievement> listA = Achievement.findAllBDD(); //Ne sera jamais null
		List<Achievement> listB = actuel.getAchievs();
		
		listA.sort((Achievement a, Achievement b)->b.compareTo(a)); //On trie la liste pour que l'affichage se fasse de fa�on coh�rente
		if(listB != null) { listB.sort((Achievement a, Achievement b)->b.compareTo(a)); }
		Iterator<Achievement> iListA = listA.iterator(); //Comme on va modifier la liste alors qu'on la parcourt, on doit utiliser un it�rator et pas un foreach
		
		while(iListA.hasNext()) {
			Achievement nonFait = iListA.next(); // On r�cup�re l'�l�ment courant
			if(listB != null){
				for(Achievement fait : listB){
					if(nonFait.getId() == fait.getId()){ //Si au sein de la liste il existe un achievement d�j� effectu� par le joueur.
						iListA.remove(); //On le supprime
					}
				}
			}
		}
		
		request.setAttribute("accomplis", actuel.getAchievs() );
		request.setAttribute("pasEncoreAccomplis", listA );
	}

}
