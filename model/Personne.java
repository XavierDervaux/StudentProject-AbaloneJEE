package be.abalone.model;

public class Personne {
	private String nom;
	private String prenom;
	private Boolean estNulEnAnglais;
	
	//---------------------------------------------------------------------------------------------------------	
	//---------------------------------------------------------------------------------------------------------	
	
	public Personne(String nom, String prenom, Boolean estNulEnAnglais) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.estNulEnAnglais = estNulEnAnglais;
	}
	
	//---------------------------------------------------------------------------------------------------------	
	//---------------------------------------------------------------------------------------------------------		
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public Boolean getEstNulEnAnglais() {
		return estNulEnAnglais;
	}
	public void setEstNulEnAnglais(Boolean estNulEnAnglais) {
		this.estNulEnAnglais = estNulEnAnglais;
	}
	
	//---------------------------------------------------------------------------------------------------------	
	//---------------------------------------------------------------------------------------------------------	

	//PAS de méthodes publiques
}
