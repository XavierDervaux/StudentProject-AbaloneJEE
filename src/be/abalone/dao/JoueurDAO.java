package be.abalone.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import be.abalone.database.AbstractDAOFactory;
import be.abalone.database.DAOFactory;
import be.abalone.model.*;

public class JoueurDAO extends DAO<Joueur>{
	public JoueurDAO(Connection conn){
		super(conn);
	}
	
	public boolean create(Joueur obj){		
		boolean res = true;

		try {
			Statement requete = connect.createStatement();
			String sql = "INSERT INTO `moniteur` (id,nom,prenom,adresse) "
					   + "VALUES (" + null + ", '" + obj.getNom() + "', '" + obj.getPrenom() + "', '" + obj.getAdresse() + "');";
			requete.executeUpdate(sql);
			
			Statement fetchId = connect.createStatement();
			String sql2 = "SELECT last_insert_rowid();";
			ResultSet rs = fetchId.executeQuery(sql2);
			
			if(rs != null){
				obj.setId(rs.getInt("last_insert_rowid()"));
			}
			
			requete.close();
			fetchId.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			res = false;
		}

		return res;
	}
	
	public boolean delete(Joueur obj){
		boolean res = false;
		
		if(obj.getId() == 0){ //L'objet vient d'etre crée et ne sort pas de la DB
			System.err.println("Erreur, vous ne pouvez pas supprimer un enregistrement sur base de cet objet.");
			System.err.println("Réessayez avec un objet provenant de la BDD.\n");
		}else{
			try {
				Statement requete = connect.createStatement();
				String sql = "DELETE FROM `moniteur` WHERE id='" + obj.getId() + "';";
				//Le sgbd supprimera seul les enregistrements correspondants à la table intermédiaire pour respecter l'intégrité référentielle.
	
				requete.executeUpdate(sql);
				requete.close();
				res = true;
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
		}

		return res;
	}
	
	public boolean update(Joueur obj){
		boolean res = false;
		
		if(obj.getId() == 0){ //L'objet vient d'etre crée et ne sort pas de la DB
			System.err.println("Erreur, vous ne pouvez pas mettre un enregistrement à jour sur base de cet objet.");
			System.err.println("Réessayez avec un objet provenant de la BDD.\n");
		}else{
			try {
				Statement requete = connect.createStatement();
				String sql = "UPDATE `moniteur` "
						   + "SET nom='" + obj.getNom() + "', prenom='" + obj.getPrenom() + "', adresse='" + obj.getAdresse() + "' " 
						   + "WHERE id='" + obj.getId() + "';";
	
				requete.executeUpdate(sql);
				requete.close();
				res = true;
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
		}

		return res;
	}
	
	public Joueur find(int id){
		Joueur res = null;
		DAOFactory adf = (DAOFactory) AbstractDAOFactory.getFactory(0);

		try {
			Statement requete = connect.createStatement();
			String sql = "SELECT * FROM `moniteur` WHERE id='" + id + "' ;";
			ResultSet rs = requete.executeQuery(sql);
			
			if(rs != null){
				res = new Joueur( rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), 
						            rs.getString("adresse"), adf.getMoniteurAcredDAO().find(id) ); 
			}
			
			requete.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
		return res;
	}
	
	public List<Joueur> getAll(){
		List<Joueur> res = null;
		DAOFactory adf = (DAOFactory) AbstractDAOFactory.getFactory(0);

		try {
			Statement requete = connect.createStatement();
			String sql = "SELECT * FROM `moniteur`;";
			ResultSet rs = requete.executeQuery(sql);
			
			if(rs != null){
				res = new ArrayList<Joueur>();
				
				while(rs.next()){ //S'il existe des moniteurs
					int id = rs.getInt("id");
					res.add(new Joueur(id, rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"), adf.getMoniteurAcredDAO().find(id))); 
				}
			}
		
			requete.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
		return res;
	}
}