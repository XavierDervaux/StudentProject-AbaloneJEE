package be.abalone.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import be.abalone.model.Historique;
import be.abalone.model.Identification;
import be.abalone.model.Joueur;

public class Histo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public Histo() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean estConnecte = Identification.estConnecte(request.getSession(), request.getCookies()); 
		
		if(estConnecte){
	    	getInfo(request); //Va parametrer request comme il faut pour pouvoir etre retourné à la page
			this.getServletContext().getRequestDispatcher("/WEB-INF/historique.jsp").forward(request, response);
    	} else { //N'est pas encore connecté, on affihce le formulaire de connexion/inscription
    		response.sendRedirect("/Abalone/index.html");
    	}
	}
	
	private void getInfo(HttpServletRequest request){
		HttpSession sessions = request.getSession();
		int jouees = 0, gagnees = 0, perdues = 0, forfait = 0;
    	Joueur actuel = (Joueur) sessions.getAttribute("joueur");
		List<Historique> listH = Historique.findAllBDD(actuel);
		
		if(listH != null){ 
			jouees = listH.size();
			for(Historique tmp : listH){
				if(tmp.getGagnant().getId() == actuel.getId())  {  gagnees++;  }
				else if(tmp.getEstForfait()) {  forfait++;  }
				else {  perdues++;  }
			}
		}
		
		request.setAttribute("jouees", jouees);
		request.setAttribute("gagnes", gagnees);
		request.setAttribute("perdues", perdues);
		request.setAttribute("forfait", forfait);
		request.setAttribute("liste", listH);		
	}
}
