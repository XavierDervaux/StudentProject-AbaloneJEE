package be.abalone.websocket;

public class bJoueur {
	private int id;
	private int joueur_id;
	private String joueur_pseudo;
	private String joueur_email;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getJoueur_id() {
		return joueur_id;
	}
	public void setJoueur_id(int joueur_id) {
		this.joueur_id = joueur_id;
	}
	public String getJoueur_pseudo() {
		return joueur_pseudo;
	}
	public void setJoueur_pseudo(String joueur_pseudo) {
		this.joueur_pseudo = joueur_pseudo;
	}
	public String getJoueur_email() {
		return joueur_email;
	}
	public void setJoueur_email(String joueur_email) {
		this.joueur_email = joueur_email;
	}	
}
