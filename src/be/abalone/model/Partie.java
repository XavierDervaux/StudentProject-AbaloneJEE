package be.abalone.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Partie {
	public static List<Partie> listParties = new ArrayList<>(); //Les parties en cours.
	private static int idManuel = 1;
	
	private int uid;
	private Bille[][] plateau = null;
	private int scoreNoir = 0;
	private int scoreBlanc = 0;
	private int comboNoir = 0; //Pour l'achievement combo 2 3 4
	private int comboBlanc = 0;//Pour l'achievement combo 2 3 4
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
	@SuppressWarnings("unused")
	public int gestionMouvement(int couleur){
		int tmp, res = -1;
		
		//mouvement
		
		if(false){ //Une bille a  été prise
			tmp = augmenterScore(couleur);
			if(tmp >= 0){ //Situation de victoire
				res = tmp;
			}
		}
		return res; //-1=non-autorise    0=VictoireNoir    1=VictoireBlanc     2=autorisé
	}
	
	public int augmenterScore(int i){ //0=noir+1   1=blanc+1
		int res = -1;
		
		if(i == 0){
			this.scoreNoir++;
			this.comboNoir++;
			this.comboBlanc = 0;
			Achievement.ACV_COMBO_2(this.noir, this.comboNoir); //Gere tous les autres combos
			if(this.scoreNoir  >= 6){  fin(0, false); res=0;  }
		} else {
			this.scoreBlanc++;
			this.comboBlanc++;
			this.comboNoir = 0;
			Achievement.ACV_COMBO_2(this.blanc, this.comboBlanc); //Gere tous les autres combos
			if(this.scoreBlanc >= 6){  fin(1, false); res=1;}
		}
		return res; //0=noirGagne   1=BlancGagne
	}

	public void fin(int couleurGagnant, boolean estForfait) {		
		Historique fin = null;
		
		if(couleurGagnant == 0){ //Noir a gagné
			fin = new Historique(new Date(), this.scoreNoir, this.scoreBlanc, estForfait, this.noir, this.blanc);
			fin.createBDD();
			
			Achievement.ACV_FIRST_WIN(this.noir); //Trigger aussi les 10 et 100
			Achievement.ACV_PERFECT(this.noir, this.scoreNoir, this.scoreBlanc);
			Achievement.ACV_SIX_FIVE(this.noir, this.scoreNoir, this.scoreBlanc);
			if(estForfait) { Achievement.ACV_SURRENDER(this.noir); }
		} else { //Blanc a gagné
			fin = new Historique(new Date(), this.scoreBlanc, this.scoreNoir, estForfait, this.blanc, this.noir);
			fin.createBDD();
			
			Achievement.ACV_FIRST_WIN(this.blanc); //Trigger aussi les 10 et 100
			Achievement.ACV_PERFECT(this.blanc, this.scoreBlanc, this.scoreNoir);
			Achievement.ACV_SIX_FIVE(this.blanc, this.scoreBlanc, this.scoreNoir);
			if(estForfait) { Achievement.ACV_SURRENDER(this.blanc); }
		}
		listParties.remove(this); //The End
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
