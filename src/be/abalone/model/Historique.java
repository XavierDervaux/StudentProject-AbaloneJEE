package be.abalone.model;

import java.util.Date;
import java.util.List;
import be.abalone.dao.HistoriqueDAO;
import be.abalone.database.AbstractDAOFactory;
import be.abalone.database.DAOFactory;

public class Historique {
	private int id = 0;
	private Date date = null;
	private int scoreGagnant = 0;
	private int scorePerdant = 0;
	private Boolean estForfait = null;
	private Joueur gagnant = null;
	private Joueur perdant = null;
	
	
// Constructeurs
//---------------------------------------------------	
	public Historique(Date date, int scoreGagnant, int scorePerdant, Boolean estForfait, Joueur gagnant, Joueur perdant) {
		this.date = date;
		this.scoreGagnant = scoreGagnant;
		this.scorePerdant = scorePerdant;
		this.estForfait = estForfait;
		this.gagnant = gagnant;
		this.perdant = perdant;
	}
	public Historique(int id, Date date, int scoreGagnant, int scorePerdant, Boolean estForfait, Joueur gagnant, Joueur perdant) {
		this.id = id;
		this.date = date;
		this.scoreGagnant = scoreGagnant;
		this.scorePerdant = scorePerdant;
		this.estForfait = estForfait;
		this.gagnant = gagnant;
		this.perdant = perdant;
	}
	

// Getter / Setter
//---------------------------------------------------	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getScoreGagnant() {
		return scoreGagnant;
	}
	public void setScoreGagnant(int scoreGagnant) {
		this.scoreGagnant = scoreGagnant;
	}
	public int getScorePerdant() {
		return scorePerdant;
	}
	public void setScorePerdant(int scorePerdant) {
		this.scorePerdant = scorePerdant;
	}
	public Joueur getGagnant() {
		return gagnant;
	}
	public void setGagnant(Joueur gagnant) {
		this.gagnant = gagnant;
	}
	public Joueur getPerdant() {
		return perdant;
	}
	public void setPerdant(Joueur perdant) {
		this.perdant = perdant;
	}
	public Boolean getEstForfait() {
		return estForfait;
	}
	public void setEstForfait(Boolean estForfait) {
		this.estForfait = estForfait;
	}


// Méthodes publiques
//---------------------------------------------------	
	public void createBDD() {
		DAOFactory adf = (DAOFactory) AbstractDAOFactory.getFactory(0);
		adf.getHistoriqueDAO().create(this);
	}
    
	public static List<Historique> findAllBDD(Joueur joueur) {
		DAOFactory adf = (DAOFactory) AbstractDAOFactory.getFactory(0);
		List<Historique> tmp = ((HistoriqueDAO) adf.getHistoriqueDAO()).getAll(joueur);
		return tmp;	
	}
	
	
// Méthode privées
//---------------------------------------------------	
	
	
// toString, hashCode, equals
//---------------------------------------------------		
	@Override
	public String toString() {
		return "Historique [id=" + id + ", date=" + date + ", scoreGagnant=" + scoreGagnant + ", scorePerdant="
				+ scorePerdant + ", estForfait=" + estForfait + ", gagnant=" + gagnant + ", perdant=" + perdant + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((estForfait == null) ? 0 : estForfait.hashCode());
		result = prime * result + ((gagnant == null) ? 0 : gagnant.hashCode());
		result = prime * result + id;
		result = prime * result + ((perdant == null) ? 0 : perdant.hashCode());
		result = prime * result + scoreGagnant;
		result = prime * result + scorePerdant;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Historique other = (Historique) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (estForfait == null) {
			if (other.estForfait != null)
				return false;
		} else if (!estForfait.equals(other.estForfait))
			return false;
		if (gagnant == null) {
			if (other.gagnant != null)
				return false;
		} else if (!gagnant.equals(other.gagnant))
			return false;
		if (id != other.id)
			return false;
		if (perdant == null) {
			if (other.perdant != null)
				return false;
		} else if (!perdant.equals(other.perdant))
			return false;
		if (scoreGagnant != other.scoreGagnant)
			return false;
		if (scorePerdant != other.scorePerdant)
			return false;
		return true;
	}
}
