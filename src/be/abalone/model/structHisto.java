package be.abalone.model;

//Une simple structure, toute les valeurs sont en public, pas de constructeurs et pas de m�thodes. 
//Cette structure est appell� une seule fois, dans Histo.java pour faire le lien entre la servlet et la jsp.
//En effet la jsp � besoin d'une liste pour afficher les infos, et la liste doit �tre d�finie sur un type. D'ou cette structure.
public class structHisto {
	public int status;//0 = gagn�e, 1 = perdue, 2 = forfait
	public String adversaire;
	public String score;
}
