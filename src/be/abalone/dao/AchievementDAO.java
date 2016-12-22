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
			String sql = "INSERT INTO `achievement` (id,titre,nom,description) "
					   + "VALUES (" + null + ",'" + obj.getTitre() + ",'" + obj.getNom() + ",'" + obj.getDescription() + "');";
			requete.executeUpdate(sql);
			
			/*Statement fetchId = connect.createStatement();
			String sql2 = "SELECT last_insert_rowid();";
			ResultSet rs = fetchId.executeQuery(sql2);
			
			if(rs != null){
				obj.setId(rs.getInt("last_insert_rowid()"));
			}
			
			fetchId.close();*/
			requete.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			res = false;
		}

		return res;
	}
	
	public boolean delete(Achievement obj){
		boolean res = false;
		
		if(obj.getId() == 0){ //L'objet vient d'etre cr�� et ne sort pas de la DB
			System.err.println("Erreur, vous ne pouvez pas supprimer un enregistrement sur base de cet objet.");
			System.err.println("R�essayez avec un objet provenant de la BDD.\n");
		}else{
			try {
				Statement requete = connect.createStatement();
				String sql = "DELETE FROM `achievement` WHERE id='" + obj.getId() + "';";
	
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
			System.err.println("Erreur, vous ne pouvez pas mettre un enregistrement � jour sur base de cet objet.");
			System.err.println("R�essayez avec un objet provenant de la BDD.\n");
		}else{
			try {
				Statement requete = connect.createStatement();
				String sql = "UPDATE `achievement` titre='" + obj.getTitre() + "', nom='" + obj.getNom() + "', decription='" + obj.getDescription() + "' " 
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
	
	public Achievement find(int id){
		Achievement res = null;

		try {
			Statement requete = connect.createStatement();
			String sql = "SELECT * FROM `achievement` WHERE id='" + id + "';";
			ResultSet rs = requete.executeQuery(sql);
			
			if(rs != null){
				res = new Achievement(rs.getInt("id"), rs.getString("titre"), rs.getString("nom"), rs.getString("description")); 
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
			String sql = "SELECT * FROM `achievement`;";
			ResultSet rs = requete.executeQuery(sql);
			
			if(rs != null){
				res = new ArrayList<Achievement>();
				
				while(rs.next()){
					res.add(new Achievement(rs.getInt("id"), rs.getString("titre"), rs.getString("nom"), rs.getString("description"))); 
				}
			}
			
			requete.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
		return res;
	}
}