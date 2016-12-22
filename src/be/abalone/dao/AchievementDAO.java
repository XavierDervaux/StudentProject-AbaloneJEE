package be.abalone.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import be.abalone.model.*;

public class AchievementDAO extends DAO<Achievement>{
	public AchievementDAO(Connection conn){
		super(conn);
	}
	
	public boolean create(Achievement obj){		
		boolean res = true;

		try {
			Statement requete = connect.createStatement();
			String sql = "INSERT INTO `acreditation` (id,nom) "
					   + "VALUES (" + null + ",'" + obj.getNom() + "');";
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
	
	public boolean delete(Achievement obj){
		boolean res = false;
		
		if(obj.getId() == 0){ //L'objet vient d'etre crée et ne sort pas de la DB
			System.err.println("Erreur, vous ne pouvez pas supprimer un enregistrement sur base de cet objet.");
			System.err.println("Réessayez avec un objet provenant de la BDD.\n");
		}else{
			try {
				Statement requete = connect.createStatement();
				String sql = "DELETE FROM `acreditation` WHERE id='" + obj.getId() + "';";
	
				requete.executeUpdate(sql);
				requete.close();
				res = true;
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
		}

		return res;
	}
	
	public boolean update(Achievement obj){
		boolean res = false;
		
		if(obj.getId() == 0){ //L'objet vient d'etre crée et ne sort pas de la DB
			System.err.println("Erreur, vous ne pouvez pas mettre un enregistrement à jour sur base de cet objet.");
			System.err.println("Réessayez avec un objet provenant de la BDD.\n");
		}else{
			try {
				Statement requete = connect.createStatement();
				String sql = "UPDATE `acreditation` SET nom='" + obj.getNom() + "'WHERE id='" + obj.getId() + "';";
	
				requete.executeUpdate(sql);
				requete.close();
				res = true;
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
		}

		return res;
	}
	
	public Achievement find(int id){
		Achievement res = null;

		try {
			Statement requete = connect.createStatement();
			String sql = "SELECT * FROM `acreditation` WHERE id='" + id + "';";
			ResultSet rs = requete.executeQuery(sql);
			
			if(rs != null){
				res = new Achievement(rs.getInt("id"), rs.getString("nom")); 
			}
			
			requete.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
		return res;
	}
	
	public List<Achievement> getAll(){
		List<Achievement> res = null;

		try {
			Statement requete = connect.createStatement();
			String sql = "SELECT * FROM `acreditation`;";
			ResultSet rs = requete.executeQuery(sql);
			
			if(rs != null){
				res = new ArrayList<Achievement>();
				
				while(rs.next()){
					res.add(new Achievement(rs.getInt("id"), rs.getString("nom")) ); 
				}
			}
			
			requete.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
		return res;
	}
}