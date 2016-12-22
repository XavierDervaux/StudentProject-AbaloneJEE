package be.abalone.model;

import java.util.List;

public class Joueur {
	private int id = 0;
	private String pseudo = null;
	private String mdp = null;
	private String email = null;
	private List<Achievement> achievs = null;
	
	
// Constructeurs
//---------------------------------------------------	
	public Joueur(String pseudo, String mdp, String email) {
		this.pseudo = pseudo;
		this.mdp = mdp;
		this.email = email;
	}
	public Joueur(int id, String pseudo, String mdp, String email) {
		this.id = id;
		this.pseudo = pseudo;
		this.mdp = mdp;
		this.email = email;
	}
	public Joueur(int id, String pseudo, String mdp, String email, List<Achievement> achievs) {
		this.id = id;
		this.pseudo = pseudo;
		this.mdp = mdp;
		this.email = email;
		this.achievs = achievs;
	}
	

// Getter / Setter
//---------------------------------------------------		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<Achievement> getAchievs() {
		return achievs;
	}
	public void setAchievs(List<Achievement> achievs) {
		this.achievs = achievs;
	}

	
// Méthodes publiques
//---------------------------------------------------	
	
	
// Méthode privées
//---------------------------------------------------	
	
	
// toString, hashCode, equals
//---------------------------------------------------	
	@Override
	public String toString() {
		return "Joueur [id=" + id + ", pseudo=" + pseudo + ", email=" + email + ", achievs=" + achievs + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((achievs == null) ? 0 : achievs.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + id;
		result = prime * result + ((pseudo == null) ? 0 : pseudo.hashCode());
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
		Joueur other = (Joueur) obj;
		if (achievs == null) {
			if (other.achievs != null)
				return false;
		} else if (!achievs.equals(other.achievs))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (pseudo == null) {
			if (other.pseudo != null)
				return false;
		} else if (!pseudo.equals(other.pseudo))
			return false;
		return true;
	}
}
