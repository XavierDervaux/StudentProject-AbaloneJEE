package be.abalone.model;

import java.util.List;
import be.abalone.database.AbstractDAOFactory;
import be.abalone.database.DAOFactory;
import be.abalone.dao.AchievJoueurDAO;

public class Achievement{
	private int id = 0;
	private String titre = null;
	private String nom = null;
	private String description = null;
	
	
// Constructeurs
//---------------------------------------------------	
	public Achievement(String titre, String nom, String description) {
		this.titre = titre;
		this.nom = nom;
		this.description = description;
	}
	public Achievement(int id, String titre, String nom, String description) {
		this.id = id;
		this.titre = titre;
		this.nom = nom;
		this.description = description;
	}
	

// Getter / Setter
//---------------------------------------------------	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


// Méthodes publiques
//---------------------------------------------------	
	public void createBDD() {
		DAOFactory adf = (DAOFactory) AbstractDAOFactory.getFactory(0);
		adf.getAchievementDAO().create(this);
	}
    
	public static List<Achievement> findAllBDD() {
		DAOFactory adf = (DAOFactory) AbstractDAOFactory.getFactory(0);
		List<Achievement> tmp = adf.getAchievementDAO().getAll();
		return tmp;	
	}
	
	
// Méthode statiques
//---------------------------------------------------	
	public static void ACV_FIRST_WIN(Joueur j){ //Gagner une partie
		DAOFactory adf = (DAOFactory) AbstractDAOFactory.getFactory(0);
		final int n = 1;
		List<Historique> listH = Historique.findAllBDD(j);
		int c = listH.size();
		
		if(c >= 100){ ACV_HUNDRED_WIN(j); }
		if(c >= 10 ){ ACV_TEN_WIN(j); }
		if(c >= 1  ){
			if( !(j.possedeAchievement(n)) ){ //S'il ne possède PAS le succes
				((AchievJoueurDAO) adf.getAchievJoueurDAO()).create(j.getId(), n); //On ajoute l'achievement au joueur
			}
		}
	}
	
	public static void ACV_TEN_WIN(Joueur j){ //Gagner 10 parties
		DAOFactory adf = (DAOFactory) AbstractDAOFactory.getFactory(0);
		final int n = 2;
		if( !(j.possedeAchievement(n)) ){ //On vérifie les conditions dans ACV_FIRST_WIN, il reste juste à vérifier qu'il ne l'a pas déjà
			((AchievJoueurDAO) adf.getAchievJoueurDAO()).create(j.getId(), n); //On ajoute l'achievement au joueur
		}
	}
	
	public static void ACV_HUNDRED_WIN(Joueur j){ //Gagner 100 parties
		DAOFactory adf = (DAOFactory) AbstractDAOFactory.getFactory(0);
		final int n = 3;
		if( !(j.possedeAchievement(n)) ){ //On vérifie les conditions dans ACV_FIRST_WIN, il reste juste à vérifier qu'il ne l'a pas déjà
			((AchievJoueurDAO) adf.getAchievJoueurDAO()).create(j.getId(), n); //On ajoute l'achievement au joueur
		}
	}
	
	public static void ACV_PERFECT(Joueur j, int s1, int s2){ //Gagner 6-0
		DAOFactory adf = (DAOFactory) AbstractDAOFactory.getFactory(0);
		final int n = 4;
		if( !(j.possedeAchievement(n)) ){ //S'il ne possède PAS le succes, on vérifie s'il remplis les conditions.
			if(s1 == 6 && s2 == 0){			
				((AchievJoueurDAO) adf.getAchievJoueurDAO()).create(j.getId(), n); //On ajoute l'achievement au joueur
			}
		}
	}
	
	public static void ACV_SIX_FIVE(Joueur j, int s1, int s2){ //Gagner 6-5
		DAOFactory adf = (DAOFactory) AbstractDAOFactory.getFactory(0);
		final int n = 5;
		if( !(j.possedeAchievement(n)) ){ //S'il ne possède PAS le succes, on vérifie s'il remplis les conditions.
			if(s1 == 6 && s2 == 5){			
				((AchievJoueurDAO) adf.getAchievJoueurDAO()).create(j.getId(), n); //On ajoute l'achievement au joueur
			}
		}
	}
	
	public static void ACV_SURRENDER(Joueur j){ //Gagner par abandon
		DAOFactory adf = (DAOFactory) AbstractDAOFactory.getFactory(0);
		final int n = 6;
		if( !(j.possedeAchievement(n)) ){ //N'est appellé qu'en cas d'abandon, rien d'autre à vérifier
			((AchievJoueurDAO) adf.getAchievJoueurDAO()).create(j.getId(), n); //On ajoute l'achievement au joueur
		}
	}
	
	public static void ACV_COMBO_2(Joueur j, int combo){ //Prendre 2 billes de suite 
		DAOFactory adf = (DAOFactory) AbstractDAOFactory.getFactory(0);
		final int n = 7;
		
		if(combo >= 4){ ACV_COMBO_4(j); }
		if(combo >= 3){ ACV_COMBO_3(j); }
		if(combo >= 2){
			if( !(j.possedeAchievement(n)) ){ //S'il ne possède PAS le succes
				((AchievJoueurDAO) adf.getAchievJoueurDAO()).create(j.getId(), n); //On ajoute l'achievement au joueur
			}
		}
	}
	
	public static void ACV_COMBO_3(Joueur j){ //Prendre 3 billes de suite
		DAOFactory adf = (DAOFactory) AbstractDAOFactory.getFactory(0);
		final int n = 8;
		if( !(j.possedeAchievement(n)) ){ //Conditions déjà gerée dans combo 2
			((AchievJoueurDAO) adf.getAchievJoueurDAO()).create(j.getId(), n); //On ajoute l'achievement au joueur
		}
	}
	
	public static void ACV_COMBO_4(Joueur j){ //Prendre 4 billes de suite
		DAOFactory adf = (DAOFactory) AbstractDAOFactory.getFactory(0);
		final int n = 9;
		if( !(j.possedeAchievement(n)) ){ ///Conditions déjà gerée dans combo 2
			((AchievJoueurDAO) adf.getAchievJoueurDAO()).create(j.getId(), n); //On ajoute l'achievement au joueur
		}
	}
	
	
// toString, hashCode, equals
//---------------------------------------------------	
	@Override
	public String toString() {
		return "Achievement [id=" + id + ", titre=" + titre + ", nom=" + nom + ", description=" + description + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((titre == null) ? 0 : titre.hashCode());
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
		Achievement other = (Achievement) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (titre == null) {
			if (other.titre != null)
				return false;
		} else if (!titre.equals(other.titre))
			return false;
		return true;
	}
	public int compareTo(Achievement a) {
		int res = 0;
		
		if(a.getId() > this.id){
			res = 1;
		} else if(a.getId() < this.id){
			res = -1;
		}
		return res;
	}
}
