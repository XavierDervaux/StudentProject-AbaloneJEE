package be.abalone.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import be.abalone.database.AbstractDAOFactory;
import be.abalone.database.DAOFactory;
import be.abalone.model.Historique;
import be.abalone.model.Joueur;
import be.abalone.utilitaire.Utilitaire;

public class HistoriqueDAO extends DAO<Historique>{
	public HistoriqueDAO(Connection conn){
		super(conn);
	}
	
	public boolean create(Historique obj){	
		int id;
		boolean res = true;
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		try {
			Statement requete = connect.createStatement();
			String sql = "INSERT INTO historique(id,date_partie,score_gagnant,score_perdant,est_forfait,id_gagnant,id_perdant) "
					   + "VALUES('','" + formatter.format(obj.getDate()) + "','" + obj.getScoreGagnant() + "','" + obj.getScorePerdant() + "','" 
					   				+ Utilitaire.boolToInt(obj.getEstForfait()) + "','" + obj.getGagnant().getId() + "','" + obj.getPerdant().getId() + "')";
			requete.executeUpdate(sql);
			
			Statement fetchId = connect.createStatement();
			String sql2 = "{? = call pkg_historique.last_id_historique}";
			CallableStatement statement = connect.prepareCall(sql2); 
			statement.registerOutParameter(1,Types.INTEGER); 
			statement.execute(); 
			id = statement.getInt(1);
			if(id != 0){  obj.setId(id);  }
			
			requete.close();
			fetchId.close();
		} catch (Exception e) {
			e.printStackTrace();
			res = false;
		}
		return res;
	}
	
	public boolean delete(Historique obj){
		/*boolean res = false;
		
		if(obj.getId() == 0){ //L'objet vient d'etre créé et ne sort pas de la DB
			System.err.println("Erreur, vous ne pouvez pas supprimer un enregistrement sur base de cet objet.");
			System.err.println("Réessayez avec un objet provenant de la BDD.\n");
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
		return res;*/
		return false;
	}
	
	public boolean update(Historique obj){
		/*boolean res = false;
		
		if(obj.getId() == 0){ //L'objet vient d'etre créé et ne sort pas de la DB
			System.err.println("Erreur, vous ne pouvez pas mettre un enregistrement à jour sur base de cet objet.");
			System.err.println("Réessayez avec un objet provenant de la BDD.\n");
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
		return res;*/
		return false;
	}
	
	public Historique find(int id){
		/*Historique res = null;
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
		return res;*/
		return null;
	}
	
	public List<Historique> getAll(){
		/*List<Historique> res = null;
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
		return res;*/
		return null;
	}
	
	public List<Historique> getAll(Joueur joueur){
		List<Historique> res = null;
		DAOFactory adf = (DAOFactory) AbstractDAOFactory.getFactory(0);

		try {
			Statement requete = connect.createStatement();
			String sql = "SELECT * FROM historique WHERE id_gagnant='" + joueur.getId() + "' OR id_perdant='" + joueur.getId() + "'";
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
			e.printStackTrace();
		}
		return res;
	}
}