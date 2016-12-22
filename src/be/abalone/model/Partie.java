package be.abalone.model;

import java.util.Arrays;

public class Partie {
	private Bille[][] plateau = null;
	private int scoreNoir = 0;
	private int scoreBlanc = 0;
	private Joueur blanc = null;
	private Joueur noir = null;
	
	
// Constructeurs
//---------------------------------------------------	
	public Partie(Joueur blanc, Joueur noir) {
		this.blanc = blanc;
		this.noir = noir;
		initPlateau();
	}
	

// Getter / Setter
//---------------------------------------------------	
	public Bille[][] getPlateau() {
		return plateau;
	}
	public void setPlateau(Bille[][] plateau) {
		this.plateau = plateau;
	}
	public int getScoreNoir() {
		return scoreNoir;
	}
	public void setScoreNoir(int scoreNoir) {
		this.scoreNoir = scoreNoir;
	}
	public int getScoreBlanc() {
		return scoreBlanc;
	}
	public void setScoreBlanc(int scoreBlanc) {
		this.scoreBlanc = scoreBlanc;
	}
	public Joueur getBlanc() {
		return blanc;
	}
	public void setBlanc(Joueur blanc) {
		this.blanc = blanc;
	}
	public Joueur getNoir() {
		return noir;
	}
	public void setNoir(Joueur noir) {
		this.noir = noir;
	}


// Méthodes publiques
//---------------------------------------------------	
	
	
// Méthode privées
//---------------------------------------------------	
	private void initPlateau(){
		
	}
	
	
// toString, hashCode, equals
//---------------------------------------------------	
	@Override
	public String toString() {
		return "Partie [plateau=" + Arrays.toString(plateau) + ", scoreNoir=" + scoreNoir + ", scoreBlanc=" + scoreBlanc
				+ ", blanc=" + blanc + ", noir=" + noir + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((blanc == null) ? 0 : blanc.hashCode());
		result = prime * result + ((noir == null) ? 0 : noir.hashCode());
		result = prime * result + Arrays.deepHashCode(plateau);
		result = prime * result + scoreBlanc;
		result = prime * result + scoreNoir;
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
		Partie other = (Partie) obj;
		if (blanc == null) {
			if (other.blanc != null)
				return false;
		} else if (!blanc.equals(other.blanc))
			return false;
		if (noir == null) {
			if (other.noir != null)
				return false;
		} else if (!noir.equals(other.noir))
			return false;
		if (!Arrays.deepEquals(plateau, other.plateau))
			return false;
		if (scoreBlanc != other.scoreBlanc)
			return false;
		if (scoreNoir != other.scoreNoir)
			return false;
		return true;
	}
}
