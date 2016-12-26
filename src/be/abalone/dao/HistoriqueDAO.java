package be.abalone.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import be.abalone.database.AbstractDAOFactory;
import be.abalone.database.DAOFactory;
import be.abalone.model.*;

public class HistoriqueDAO extends DAO<Historique>{
	public HistoriqueDAO(Connection conn){
		super(conn);
	}
	
	public boolean create(Historique obj){	
		int id;
		boolean res = true;

		try {
			Statement requete = connect.createStatement();
			String sql = "INSERT INTO historique (id,date_partie,score_gagnant,score_perdant,est_forfait,id_gagnant,id_perdant) "
					   + "VALUES (','" + obj.getDate() + "','" + obj.getScoreGagnant() + "','" + obj.getScorePerdant() + "','" 
					   				+ Utilitaire.boolToInt(obj.getEstForfait()) + "','" + obj.getGagnant().getId() + "','" + obj.getPerdant().getId() + "')";
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
			
			requete.close();
			fetchId.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			res = false;
		}

		return res;
	}
	
	public boolean delete(Historique obj){
		boolean res = false;
		
		if(obj.getId() == 0){ //L'objet vient d'etre cr√©e et ne sort pas de la DB
			System.err.println("Erreur, vous ne pouvez pas supprimer un enregistrement sur base de cet objet.");
			System.err.println("RÈessayez avec un objet provenant de la BDD.\n");
		}else{
			try {
				Statement requete = connect.createStatement();
				String sql = "DELETE FROM historique WHERE id='" + obj.getId() + "'";
					
				requete.executeUpdate(sql);
				requete.close();
				res = true;
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
		}

		return res;
	}
	
	public boolean update(Historique obj){
		boolean res = false;
		
		if(obj.getId() == 0){ //L'objet vient d'etre cr√©e et ne sort pas de la DB
			System.err.println("Erreur, vous ne pouvez pas mettre un enregistrement ‡ jour sur base de cet objet.");
			System.err.println("RÈessayez avec un objet provenant de la BDD.\n");
		}else{
			try {
				Statement requete = connect.createStatement();
				String sql = "UPDATE historique " //(id,date_partie,score_gagnant,score_perdant,est_forfait,id_gagnant,id_perdant)
						   + "SET datePartie='" + obj.getDate() + "', score_gagnant='" + obj.getScoreGagnant() + "', score_perdant='" + obj.getScorePerdant() + "', est_forfait='" 
			   			   + Utilitaire.boolToInt(obj.getEstForfait()) + "', id_gagnant='" + obj.getGagnant().getId() + "', id_perdant'" + obj.getPerdant().getId() + "' " 
						   + "WHERE id='" + obj.getId() + "'";
	
				requete.executeUpdate(sql);
				requete.close();
				res = true;
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
		}

		return res;
	}
	
	public Historique find(int id){
		Historique res = null;
		DAOFactory adf = (DAOFactory) AbstractDAOFactory.getFactory(0);

		try {
			Statement requete = connect.createStatement();
			String sql = "SELECT * FROM historique WHERE id='" + id + "'";
			ResultSet rs = requete.executeQuery(sql);
			
			if(rs != null){ 
				while(rs.next()){ 
				res = new Historique(rs.getInt("id"), rs.getDate("date_partie"), rs.getInt("score_gagnant"), rs.getInt("score_perdant"), 
						        	 Utilitaire.intToBool(rs.getInt("est_forfait")), adf.getJoueurDAO().find(rs.getInt("id_gagnant")), 
						        	 adf.getJoueurDAO().find(rs.getInt("id_perdant")) ); 
				}
			}
			requete.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
		return res;
	}
	
	public List<Historique> getAll(){
		List<Historique> res = null;
		DAOFactory adf = (DAOFactory) AbstractDAOFactory.getFactory(0);

		try {
			Statement requete = connect.createStatement();
			String sql = "SELECT * FROM historique";
			ResultSet rs = requete.executeQuery(sql);
			
			if(rs != null){
				res = new ArrayList<Historique>();
				
				while(rs.next()){ //S'il existe des moniteurs
					res.add(new Historique(rs.getInt("id"), rs.getDate("date_partie"), rs.getInt("score_gagnant"), rs.getInt("score_perdant"), 
										   Utilitaire.intToBool(rs.getInt("est_forfait")), adf.getJoueurDAO().find(rs.getInt("id_gagnant")), 
							        	   adf.getJoueurDAO().find(rs.getInt("id_perdant")))); 
				}
			}
		
			requete.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
		return res;
	}
}