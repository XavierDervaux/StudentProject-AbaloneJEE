package be.abalone.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Partie {
	public static List<Partie> listParties = new ArrayList<>(); //Les parties en cours.
	private static int idManuel = 1;
	
	private int uid;
	private Bille[][] plateau = null;
	private int scoreNoir = 0;
	private int scoreBlanc = 0;
	private Joueur blanc = null;
	private Joueur noir = null;
	
	
// Constructeurs
//---------------------------------------------------	
	public Partie(Joueur j1, Joueur j2) {
		this.uid = idManuel;
		idManuel++;
		this.noir = j1;
		this.blanc = j2;
		initPlateau();
	}
	

// Getter / Setter
//---------------------------------------------------	
	public int getUid() {
		return uid;
	}
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

	public void finForfait(int i) { //0 = noir forfait, 1 = blanc forfait
		// TODO Auto-generated method stub
		
	}
	
	public void fin(int i) { //0 = noir forfait, 1 = blanc forfait
		// TODO Auto-generated method stub
		
	}
	
	public static Partie trouverPartie(Joueur joueur1, Joueur joueur2) {
		Partie res = null;
		
		for(Partie tmp : listParties){
			if( (tmp.noir.equals(joueur1) && tmp.blanc.equals(joueur2))   ||   (tmp.noir.equals(joueur2) && tmp.blanc.equals(joueur1)) ){
				res = tmp; break;//On ne maitrise pas l'ordre des joueurs, il faut donc vérifier les deux possibilités d'ordre.
			}
		}
		return res;
	}
	public static Partie trouverPartie(int uid) {
		Partie res = null;
		
		for(Partie tmp : listParties){
			if(tmp.getUid() == uid){
				res = tmp; break;
			}
		}
		return res;
	}
	
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
