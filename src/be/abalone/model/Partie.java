package be.abalone.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import be.abalone.bean.bMove;

public class Partie {
	public static List<Partie> listParties = new ArrayList<>(); //Les parties en cours.
	private static int idManuel = 1;
	
	private int uid;
	private int[][] plateau = null;
	private int tour = 1; //1=Tour de noir    -1=Tour de blanc
	private boolean peutBouger = true;
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
	public int[][] getPlateau() {
		return plateau;
	}
	public void setPlateau(int[][] plateau) {
		this.plateau = plateau;
	}
	public int getTour() {
		return tour;
	}
	public void setTour(int tour) {
		this.tour = tour;
	}
	public boolean isPeutBouger() {
		return peutBouger;
	}
	public void setPeutBouger(boolean peutBouger) {
		this.peutBouger = peutBouger;
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
	public int gestionMouvement(int couleur, bMove moves){
		int tmp, res = -1;
		
		if(this.peutBouger){ //Si on a déjà bougé une fois ce tour ci on ne peut plus le faire
			res = mouvementEstCorrect(moves); //-1 si non   2 si oui    3 si oui et score +1
			
			if(res == 3){ //Une bille a  été prise
				tmp = augmenterScore(couleur); //Si le score atteinds 6, la partie se finit ici.
				if(tmp >= 0){ //Situation de victoire
					res = tmp;
				} else { res = 2; }
			}
			
			if(res == 2){ this.peutBouger = false; } //Un seul déplacement par tour.
		}
		return res; //-1=non-autorise    0=VictoireNoir    1=VictoireBlanc     2=autorisé
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
	public boolean estSonTour(int couleur) {
		boolean res = false;
		
		if( (couleur == 0 && this.tour == 1)   ||   (couleur == 1 && this.tour == -1) ){
			res = true;
		}
		return res;
	}
	
// Méthode privées
//---------------------------------------------------	
	private void initPlateau(){ //Génère le plateau de base, dût à sa forme hexagonale beaucoup de chose sont écrites manuelement.
		int i;
		this.plateau = new int[17][9]; //Est nul par défaut //Voir dossier sur le pourquoi 17*9
		Arrays.fill(this.plateau, -99); //Les cases non parcourables sont -99
		
		/*Ligne 1*/ for(i=4; i<=12; i=i+2) {    this.plateau[0][i] =  1;	}//Sur la première ligne toutes les billes sont blanches
		/*Ligne 2*/	for(i=3; i<=13; i=i+2) {    this.plateau[1][i] =  1;    }//Sur la deuxième aussi.
		/*Ligne 3*/ for(i=3; i<=13; i=i+2) {    this.plateau[2][i] =  1;    }//Les autres cases sont blanches
					this.plateau[2][2]  = -1;
					this.plateau[2][14] = -1; //Ce sont les deux seules cases vides de la ligne 3
		/*Ligne 4*/ for(i=1; i<=15; i=i+2) {    this.plateau[3][i] = -1;    }//Aucune bille ici
		/*Ligne 5*/ for(i=0; i<=16; i=i+2) {    this.plateau[4][i] = -1;    }//Aucune bille ici
		/*Ligne 6*/ for(i=1; i<=15; i=i+2) {    this.plateau[5][i] = -1;    }//Aucune bille ici
		/*Ligne 7*/ this.plateau[6][2]  = -1; //Ce sont les deux seules cases vides de la ligne 7
					this.plateau[6][14] = -1; 
					for(i=3; i<=13; i=i+2) {    this.plateau[6][i] =  0;    }//Les autres cases sont noires
		/*Ligne 8*/	for(i=3; i<=13; i=i+2) {    this.plateau[7][i] =  0;    }//Toutes les billes de la ligne 8 sont noires
		/*Ligne 9*/ for(i=4; i<=12; i=i+2) {    this.plateau[8][i] =  0;	}//Pareil pour la 9
		
		//-99 = case invallide    -1 = Aucune bille     0 = Bille noire     1=Bille blanche
	}

	private int mouvementEstCorrect(bMove moves) {
		int res = -1;
//TODO
		
		if(res >=2 ) {  updatePlateau();  } //Si un déplacement à été autorisé on MaJ le plateau
		return res;//-1 si non    2 si oui    3 si oui et score +1
	}
	
	private void updatePlateau(){
//TODO		
	}
	
	private int augmenterScore(int i){ //0=noir+1   1=blanc+1
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
	
	
// toString, hashCode, equals
//---------------------------------------------------	
	@Override
	public String toString() {
		return "Partie [uid=" + uid + ", plateau=" + Arrays.toString(plateau) + ", tour=" + tour + ", peutBouger="
				+ peutBouger + ", scoreNoir=" + scoreNoir + ", scoreBlanc=" + scoreBlanc + ", comboNoir=" + comboNoir
				+ ", comboBlanc=" + comboBlanc + ", blanc=" + blanc + ", noir=" + noir + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((blanc == null) ? 0 : blanc.hashCode());
		result = prime * result + comboBlanc;
		result = prime * result + comboNoir;
		result = prime * result + ((noir == null) ? 0 : noir.hashCode());
		result = prime * result + (peutBouger ? 1231 : 1237);
		result = prime * result + Arrays.deepHashCode(plateau);
		result = prime * result + scoreBlanc;
		result = prime * result + scoreNoir;
		result = prime * result + tour;
		result = prime * result + uid;
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
		if (comboBlanc != other.comboBlanc)
			return false;
		if (comboNoir != other.comboNoir)
			return false;
		if (noir == null) {
			if (other.noir != null)
				return false;
		} else if (!noir.equals(other.noir))
			return false;
		if (peutBouger != other.peutBouger)
			return false;
		if (!Arrays.deepEquals(plateau, other.plateau))
			return false;
		if (scoreBlanc != other.scoreBlanc)
			return false;
		if (scoreNoir != other.scoreNoir)
			return false;
		if (tour != other.tour)
			return false;
		if (uid != other.uid)
			return false;
		return true;
	}
}
