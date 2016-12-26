package be.abalone.model;

//Une simple structure, toute les valeurs sont en public, pas de constructeurs et pas de méthodes. 
//Cette structure est appellé une seule fois, dans Histo.java pour faire le lien entre la servlet et la jsp.
//En effet la jsp à besoin d'une liste pour afficher les infos, et la liste doit étre définie sur un type. D'ou cette structure.
public class structHisto {
	public int status;//0 = gagnée, 1 = perdue, 2 = forfait
	public String adversaire;
	public String score;
}
