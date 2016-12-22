package be.abalone.model;

public class Historique {
	private int id = -1;
	private int scoreGagnant = -1;
	private int scorePerdant = -1;
	private Joueur gagnant = null;
	private Joueur perdant = null;
	private Boolean estForfait = null;
	
	
// Constructeurs
//---------------------------------------------------	
	public Historique(int scoreGagnant, int scorePerdant, Joueur gagnant, Joueur perdant, Boolean estForfait) {
		this.scoreGagnant = scoreGagnant;
		this.scorePerdant = scorePerdant;
		this.gagnant = gagnant;
		this.perdant = perdant;
		this.estForfait = estForfait;
	}
	public Historique(int id, int scoreGagnant, int scorePerdant, Joueur gagnant, Joueur perdant, Boolean estForfait) {
		this.id = id;
		this.scoreGagnant = scoreGagnant;
		this.scorePerdant = scorePerdant;
		this.gagnant = gagnant;
		this.perdant = perdant;
		this.estForfait = estForfait;
	}
	

// Getter / Setter
//---------------------------------------------------	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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


// M�thodes publiques
//---------------------------------------------------	
	
	
// M�thode priv�es
//---------------------------------------------------	
	
	
// toString, hashCode, equals
//---------------------------------------------------	
	@Override
	public String toString() {
		return "Historique [id=" + id + ", scoreGagnant=" + scoreGagnant + ", scorePerdant=" + scorePerdant
				+ ", gagnant=" + gagnant + ", perdant=" + perdant + ", estForfait=" + estForfait + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
