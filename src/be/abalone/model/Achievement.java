package be.abalone.model;

public class Achievement {
	private int id = -1;
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


// M�thodes publiques
//---------------------------------------------------	
	
	
// M�thode priv�es
//---------------------------------------------------	
	
	
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
}
