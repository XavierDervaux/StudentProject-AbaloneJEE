package be.abalone.model;

public class Bille {
	private int id = 0;
	private int couleur = -1; //0 Noir, 1 Blanc
	
	
// Constructeurs
//---------------------------------------------------	
	public Bille(int id, int couleur) {
		this.id = id;
		this.couleur = couleur;
	}
	

// Getter / Setter
//---------------------------------------------------	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCouleur() {
		return couleur;
	}
	public void setCouleur(int couleur) {
		this.couleur = couleur;
	}


// Méthodes publiques
//---------------------------------------------------	
	
	
// Méthode privées
//---------------------------------------------------	
	
	
// toString, hashCode, equals
//---------------------------------------------------	
	@Override
	public String toString() {
		return "Bille [id=" + id + ", couleur=" + couleur + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + couleur;
		result = prime * result + id;
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
		Bille other = (Bille) obj;
		if (couleur != other.couleur)
			return false;
		if (id != other.id)
			return false;
		return true;
	}
}
