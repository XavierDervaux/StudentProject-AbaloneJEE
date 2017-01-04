package be.abalone.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import be.abalone.model.Achievement;

public class AchievementDAO extends DAO<Achievement>{
	public AchievementDAO(Connection conn){
		super(conn);
	}
	
	public boolean create(Achievement obj){		
		/*int id;
		boolean res = true;
		
		try {
			Statement requete = connect.createStatement();			
			String sql = "INSERT INTO achievement (id,titre,nom,description) "
					   + "VALUES ('','" + obj.getTitre() + "','" + obj.getNom() + "','" + obj.getDescription() + "')";
			requete.executeUpdate(sql);
			
			Statement fetchId = connect.createStatement();
			String sql2 = "{? = call last_inserted_rowid(?)}";
			CallableStatement statement = connect.prepareCall(sql2); 
			statement.registerOutParameter(1,Types.INTEGER); 
			statement.setInt(2,1); 
			statement.execute(); 
			id = statement.getInt(1);
			
			if(id != 0){
				obj.setId(id);
			}
			
			fetchId.close();
			requete.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			res = false;
		}
		return res;*/
		return false;
	}
	
	public boolean delete(Achievement obj){
		/*boolean res = false;
		
		if(obj.getId() == 0){ //L'objet vient d'etre créé et ne sort pas de la DB
			System.err.println("Erreur, vous ne pouvez pas supprimer un enregistrement sur base de cet objet.");
			System.err.println("Réessayez avec un objet provenant de la BDD.\n");
		}else{
			try {
				Statement requete = connect.createStatement();
				String sql = "DELETE FROM achievement WHERE id='" + obj.getId() + "'";
	
				requete.executeUpdate(sql);
				requete.close();
				res = true;
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
		}
		return res;*/
		return false;
	}
	
	public boolean update(Achievement obj){
		/*boolean res = false;
		
		if(obj.getId() == 0){ //L'objet vient d'etre crÃ©e et ne sort pas de la DB
			System.err.println("Erreur, vous ne pouvez pas mettre un enregistrement à jour sur base de cet objet.");
			System.err.println("Réessayez avec un objet provenant de la BDD.\n");
		}else{
			try {
				Statement requete = connect.createStatement();
				String sql = "UPDATE achievement titre='" + obj.getTitre() + "', nom='" + obj.getNom() + "', decription='" + obj.getDescription() + "' " 
						   + "WHERE id='" + obj.getId() + "'";
	
				requete.executeUpdate(sql);
				requete.close();
				res = true;
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
		}
		return res;*/
		return false;
	}
	
	public Achievement find(int id){
		Achievement res = null;

		try {
			Statement requete = connect.createStatement();
			String sql = "SELECT * FROM achievement WHERE id='" + id + "'";
			ResultSet rs = requete.executeQuery(sql);
			
			if(rs != null){
				while(rs.next()){ 
					res = new Achievement(rs.getInt("id"), rs.getString("titre"), rs.getString("nom"), rs.getString("description")); 
				}
			}
			
			requete.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public List<Achievement> getAll(){
		List<Achievement> res = null;

		try {
			Statement requete = connect.createStatement();
			String sql = "SELECT * FROM achievement";
			ResultSet rs = requete.executeQuery(sql);
			
			if(rs != null){
				res = new ArrayList<Achievement>();
				
				while(rs.next()){
					res.add(new Achievement(rs.getInt("id"), rs.getString("titre"), rs.getString("nom"), rs.getString("description"))); 
				}
			}
			
			requete.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
}