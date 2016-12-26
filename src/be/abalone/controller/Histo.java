package be.abalone.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.abalone.model.Historique;
import be.abalone.model.Identification;
import be.abalone.model.Joueur;
import be.abalone.model.structHisto;

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
    	Joueur actuel = (Joueur) sessions.getAttribute("joueur");
		List<Historique> listH = Historique.findAllBDD(actuel);
		
		request.setAttribute("jouees", getJouees(listH));
		request.setAttribute("gagnes", getGagnees(listH, actuel));
		request.setAttribute("perdues", getPerdues(listH, actuel));
		request.setAttribute("forfait", getAbandonnees(listH, actuel));
		request.setAttribute("liste", getListe(listH, actuel));
		
	}

	private int getJouees(List<Historique> listH) {
		return listH.size();
	}

	private int getGagnees(List<Historique> listH, Joueur actuel) {
		int gagnees = 0;
		
		for(Historique tmp : listH){
			if(tmp.getGagnant().getId() == actuel.getId()){
				gagnees++;
			}
		}
		
		return gagnees;
	}

	private int getPerdues(List<Historique> listH, Joueur actuel) {
		int perdues = 0;
		
		for(Historique tmp : listH){
			if(tmp.getPerdant().getId() == actuel.getId()){
				perdues++;
			}
		}
		
		return perdues;
	}

	private int getAbandonnees(List<Historique> listH, Joueur actuel) {
		int aband = 0;
		
		for(Historique tmp : listH){
			if(tmp.getPerdant().getId() == actuel.getId()){
				if(tmp.getEstForfait()){
					aband++;
				}
			}
		}
		
		return aband;
	}

	private Object getListe(List<Historique> listH, Joueur actuel) {
		structHisto tmpL;
		List<structHisto> res = new ArrayList<structHisto>();
		
		for(Historique tmp : listH){
			tmpL = new structHisto();
			if(tmp.getGagnant().getId() == actuel.getId()){
				tmpL.adversaire = tmp.getPerdant().getPseudo();
				tmpL.score = tmp.getScoreGagnant() + " - " + tmp.getScorePerdant();
				tmpL.status = 0;
			} else { //Est perdant
				tmpL.adversaire = tmp.getGagnant().getPseudo();
				tmpL.score = tmp.getScorePerdant() + " - " + tmp.getScoreGagnant();
				if(tmp.getEstForfait()){ tmpL.status = 2; }
				else{ tmpL.status = 1;}
			}
			res.add(tmpL);
		}
		return null;
	}
}
