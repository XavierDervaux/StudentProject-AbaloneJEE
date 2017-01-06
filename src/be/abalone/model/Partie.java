package be.abalone.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import be.abalone.bean.bMove;
import be.abalone.bean.bMoveResp;

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
	private int billeColission = -1;
	private int couleur;
	private boolean possiblePoint = false;
	
	
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


// M�thodes publiques
//---------------------------------------------------	
	public int gestionMouvement(int couleur, bMove moves, bMoveResp retour){
		int tmp, res = -1;
		this.couleur = couleur;
		
		if(this.peutBouger){ //Si on a d�j� boug� une fois ce tour ci on ne peut plus le faire
			res = validationMouvement(moves, retour); //-1 si non   2 si oui    3 si oui et score +1
			
			if(res == 3){ //Une bille a  �t� prise
				tmp = augmenterScore(couleur); //Si le score atteinds 6, la partie se finit ici.
				if(tmp >= 0){ //Situation de victoire
					res = tmp;
				} else { res = 2; }
			}
			
			if(res == 2){ this.peutBouger = false; } //Un seul d�placement par tour.
		}
		return res; //-1=non-autorise    0=VictoireNoir    1=VictoireBlanc     2=autoris�
	}

	public void fin(int couleurGagnant, boolean estForfait) {		
		Historique fin = null;
		
		if(couleurGagnant == 0){ //Noir a gagn�
			fin = new Historique(new Date(), this.scoreNoir, this.scoreBlanc, estForfait, this.noir, this.blanc);
			fin.createBDD();
			
			Achievement.ACV_FIRST_WIN(this.noir); //Trigger aussi les 10 et 100
			Achievement.ACV_PERFECT(this.noir, this.scoreNoir, this.scoreBlanc);
			Achievement.ACV_SIX_FIVE(this.noir, this.scoreNoir, this.scoreBlanc);
			if(estForfait) { Achievement.ACV_SURRENDER(this.noir); }
		} else { //Blanc a gagn�
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
				res = tmp; break;//On ne maitrise pas l'ordre des joueurs, il faut donc v�rifier les deux possibilit�s d'ordre.
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
	
// M�thode priv�es
//---------------------------------------------------	
	private void initPlateau(){ //G�n�re le plateau de base, d�t � sa forme hexagonale beaucoup de chose sont �crites manuelement.
		int i, j;
		this.plateau = new int[9][17]; //Est nul par d�faut //Voir dossier sur le pourquoi 17*9
		for(i=0; i<9; i++){
			for(j=0; j<17; j++){
				this.plateau[i][j] = -99; //Les cases non parcourables sont -99
			}
		}
		
		/*Ligne 1*/ for(i=4; i<=12; i=i+2) {    this.plateau[0][i] =  1;	} //Sur la premi�re ligne toutes les billes sont blanches
		/*Ligne 2*/ for(i=3; i<=13; i=i+2) {    this.plateau[1][i] =  1;    } //Sur la deuxi�me aussi.
		/*Ligne 3*/ for(i=6; i<=10; i=i+2) {    this.plateau[2][i] =  1;    } //Les autres cases sont blanches
					this.plateau[2][2]  = -1; 
					this.plateau[2][4]  = -1; 
					this.plateau[2][12] = -1; 
					this.plateau[2][14] = -1; //Ce sont les 4 seules cases vides de la ligne 3
		/*Ligne 4*/ for(i=1; i<=15; i=i+2) {    this.plateau[3][i] = -1;    } //Aucune bille ici
		/*Ligne 5*/ for(i=0; i<=16; i=i+2) {    this.plateau[4][i] = -1;    } //Aucune bille ici
		/*Ligne 6*/ for(i=1; i<=15; i=i+2) {    this.plateau[5][i] = -1;    } //Aucune bille ici
		/*Ligne 7*/ this.plateau[6][2]  = -1; //Ce sont les 4 seules cases vides de la ligne 7
					this.plateau[6][4]  = -1; 
					this.plateau[6][12] = -1; 
					this.plateau[6][14] = -1; 
					for(i=6; i<=10; i=i+2) {    this.plateau[6][i] =  0;    } //Les autres cases sont noires
		/*Ligne 8*/ for(i=3; i<=13; i=i+2) {    this.plateau[7][i] =  0;    } //Toutes les billes de la ligne 8 sont noires
		/*Ligne 9*/ for(i=4; i<=12; i=i+2) {    this.plateau[8][i] =  0;	} //Pareil pour la 9
		
		//-99 = case invallide    -1 = Aucune bille     0 = Bille noire     1=Bille blanche
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
	
	private int validationMouvement(bMove moves, bMoveResp r){ 
		System.out.println("Coucou c'est moi dans validationMouvement() !");
		System.out.println("ori_x1: " + moves.ox1() + ", ori_y1: " + moves.oy1());
		System.out.println("ori_x2: " + moves.ox2() + ", ori_y2: " + moves.oy2());
		System.out.println("ori_x3: " + moves.ox3() + ", ori_y3: " + moves.oy3());
		System.out.println("des_x1: " + moves.dx1() + ", des_y3: " + moves.dy1());
		System.out.println("des_x2: " + moves.dx2() + ", des_y3: " + moves.dy2());
		System.out.println("des_x3: " + moves.dx3() + ", des_y3: " + moves.dy3());
		int res = -1;
		int nbrBille = 0, tmp;
		int xDirection, yDirection, nbrBillesEnnemie = 0;
		
		if(isColission(moves)){ //On rencontre une bille ennemie sur notre chemin
			
			nbrBille = nbrBilles(moves); //Nombre de bille avec moi
			//tmp = billeColission+1%nbrBille; //Je prend une autre bille en d�placement pour comparaison
			System.out.println("Nombre de bille" + nbrBille);
			if(nbrBille > 1){
				if(isVerticalRight(moves.ox1(), moves.oy1(), moves.ox2(), moves.oy2())){ //Verticale droite
					System.out.println("Je suis une rang� de bille verticale droite["+getBilleOX(moves,this.billeColission)+"]["+ getBilleDX(moves, this.billeColission)+"]");
					if(getBilleOX(moves,this.billeColission) > getBilleDX(moves, this.billeColission)){ //je remonte sur le plateau
						xDirection = -1;
						yDirection = 1;
					} else{ //je descend
						xDirection = 1;
						yDirection = -1;
					}
				} else if(isVerticalLeft(moves.ox1(), moves.oy1(), moves.ox2(), moves.oy2())){//Verticale gauche 
					System.out.println("Je suis une rang� de bille verticale gauche["+getBilleOX(moves,this.billeColission)+"]["+ getBilleDX(moves, this.billeColission)+"]");
					if(getBilleOX(moves,this.billeColission) > getBilleDX(moves, this.billeColission)){ //je remonte sur le plateau
						xDirection = -1;
						yDirection = -1;
					} else{ //je descend
						xDirection = 1;
						yDirection = 1;
					}
				}else{ //Hozirontale
					System.out.println("Je suis une rang� de bille horizontale");
					if(getBilleOY(moves,this.billeColission) < getBilleDY(moves, this.billeColission)){ //je vais vers � la droite
						System.out.println("Je vais vers la droite");
						xDirection = 0;
						yDirection = 2;
					} else{ //je vais vers la gauche
						System.out.println("Je vais vers la gauche");
						xDirection = 0;
						yDirection = -2;
					}
				}
				
				//Suite du code
				nbrBillesEnnemie = nbrBillesEnnemie(moves,xDirection, yDirection); //nombre de bille ennemie sur le chemin
				System.out.println("Nombre de bille � moi : " + nbrBille);
				if( nbrBillesEnnemie < nbrBille){ //si elle sont en inf�riorit� num�rique je pousse
					setCoordonneeResp(moves, r, nbrBillesEnnemie, xDirection, yDirection);
					if(this.possiblePoint){ //Je gagne un point
						res = 3;
					}else{
						res = 2;
					}
				} else{ //Je peux pas pousser, envoyer le packet de refus
					res = -1;
				}
			} else{
				res = -1;
			}
		}else{
			r.setM(moves);
			res = 2;
			updateBillePlateau(moves);
		}
		
		this.billeColission = 0;
		return res;
	}
	
	private void setCoordonneeResp (bMove moves, bMoveResp r, int nbrBille ,int xDirection, int yDirection){ //D�place les billes pouss�es
		int i, tmpX, tmpY;
		
		r.setM(moves);
		tmpX = getBilleDX(moves, this.billeColission);
		tmpY = getBilleDY(moves, this.billeColission);
		
		for(i=0; i < nbrBille; i++){
			tmpX += xDirection;
			tmpY += yDirection;
			if((tmpX > -1 && tmpX < 9) && (tmpY > -1 && tmpX < 17)){
				if(this.plateau[tmpX][tmpY] != -99){
					this.plateau[tmpX][tmpY] = (this.couleur+1)%2;
					if(i == 0){
						r.setDes_x4(tmpX);
						r.setDes_y4(tmpY);
					}else{
						r.setDes_x5(tmpX);
						r.setDes_y5(tmpY);
					}
				}
			}
		}
		//Met � jour le reste du plateau
		updateBillePlateau(moves);
	}
	private void updateBillePlateau(bMove moves){ 
		if(moves.ox1() > -1 && moves.oy1() > -1){
			this.plateau[moves.ox1()][moves.oy1()] = -1;
		}
		if(moves.ox2() > -1 && moves.oy2() > -1){
			this.plateau[moves.ox2()][moves.oy2()] = -1;
		}
		if(moves.ox3() > -1 && moves.oy3() > -1){
			this.plateau[moves.ox3()][moves.oy3()] = -1;
		}
		
		if(moves.dx1() > -1 && moves.dy1() > -1){
			this.plateau[moves.dx1()][moves.dy1()] = this.couleur;
		}
		if(moves.dx2() > -1 && moves.dy2() > -1){
			this.plateau[moves.dx2()][moves.dy2()] = this.couleur;
		}
		if(moves.dx3() > -1 && moves.dy3() > -1){
			this.plateau[moves.dx3()][moves.dy3()] = this.couleur;
		}
	}
	
	private int nbrBillesEnnemie(bMove moves, int xDirection, int yDirection){ 
		int i, j, nbr = 1;
		boolean stop = false;
		
		i = getBilleDX(moves, this.billeColission);
		j = getBilleDY(moves, this.billeColission);
		System.out.println("Avant boucle while1 :" + nbr +" - [" + i +"][" + j +"]");
		i += xDirection;
		j += yDirection;
		System.out.println("Avant boucle while2 :" + nbr +" - [" + i +"][" + j +"]");
		
		while(nbr < 3 && i > -1 &&  i < 9 && j > -1 && j < 17 && !stop && this.plateau[i][j] != -1){
			if(this.plateau[i][j] == -99){
				stop = true;
			} else if(this.plateau[i][j] == this.couleur){
				nbr = 10;
			} else if(this.plateau[i][j] == (this.couleur+1)%2){
				nbr++;
			}
			i += xDirection;
			j += yDirection;
			
		}
		if(!stop && (i < 0 || i > 8 || j < 0 || j > 16)){
			possiblePoint = true;
		}
		System.out.println("Nombre de bille ennemie : " + nbr);
		this.possiblePoint = stop;
		
		return nbr;
	}
	
	private int nbrBilles(bMove moves){ 
		int nbr = 0;
		if(moves.ox1() > -1 && moves.oy1() > -1){
			nbr++;
		}
		if(moves.ox2() > -1 && moves.oy2() > -1){
			nbr++;
		}
		if(moves.ox3() > -1 && moves.oy3() > -1){
			nbr++;
		}
		return nbr;
	}
	private int getBilleDX(bMove moves, int bille){ 
		int x;
		if(bille == 1){
			x = moves.dx1();
		} else if(bille == 2){
			x = moves.dx2();
		} else{
			x = moves.dx3();
		}
		return x;
	}
	private int getBilleDY(bMove moves, int bille){ 
		int y;
		if(bille == 1){
			y = moves.dy1();
		} else if(bille == 2){
			y = moves.dy2();
		} else{
			y = moves.dy3();
		}
		return y;
	}
	
	private int getBilleOY(bMove moves, int bille){ 
		int y;
		if(bille == 1){
			y = moves.oy1();
		} else if(bille == 2){
			y = moves.oy2();
		} else{
			y = moves.oy3();
		}
		return y;
	}
	
	private int getBilleOX(bMove moves, int bille){ 
		int x;
		if(bille == 1){
			x = moves.ox1();
		} else if(bille == 2){
			x = moves.ox2();
		} else{
			x = moves.ox3();
		}
		return x;
	}
	private boolean isColission(bMove moves){ 
		boolean colission = false;
		
		if(isColissionBille(moves.dx1(),moves.dy1())){
			this.billeColission = 1;
			colission = true;
		} else if(isColissionBille(moves.dx2(),moves.dy2())){
			this.billeColission = 2;
			colission = true;
		} else if(isColissionBille(moves.dx3(),moves.dy3())){
			this.billeColission = 3;
			colission = true;
		}
		System.out.println("Ma colission est ma bille : " + this.billeColission);
		return colission;
	}
	private boolean isColissionBille(int x, int y){
		boolean colission = false;
		if(x > -1 && y > -1){
			if(this.plateau[x][y] == (this.couleur+1)%2){ 
				colission = true;
			}
		}
		return colission;
	}
	private boolean equalsCoordinate(int x1, int y1, int x2, int y2){
		return (equalsX(x1,x2) && equalsY(y1, y2));
	}
	private boolean equalsY(int y1, int y2){
		return y1 == y2;
	}
	private boolean equalsX(int x1, int x2){
		return x1 == x2;
	}
	private boolean isVerticalRight(int x1, int y1, int x2, int y2){
		return(equalsCoordinate(x1, y1, x2-1, y2+1) || equalsCoordinate(x1, y1, x2+1, y2-1));
	}
	private boolean isVerticalLeft(int x1, int y1, int x2, int y2){
		return(equalsCoordinate(x1, y1, x2+1, y2+1) || equalsCoordinate(x1, y1, x2-1, y2-1));
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
