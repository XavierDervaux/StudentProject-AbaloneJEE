package be.abalone.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import be.abalone.model.*;

public class HistoriqueDAO extends DAO<Historique>{
	public HistoriqueDAO(Connection conn){
		super(conn);
	}
	
	public boolean create(Historique obj){			
		boolean res = true;

		try {
			Statement requete = connect.createStatement();
			String sql = "INSERT INTO `semaine` (id,dateDebut,dateFin,descriptif,estCongeScolaire) "
					   + "VALUES (" + null + ", '" + obj.getDateDebut() + "', '" + obj.getDateFin() + "', '" + obj.getDescriptif() + "', '" 
					                + obj.isEstCongeScolaire() + "');";
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
	
	public boolean delete(Historique obj){
		boolean res = false;
		
		if(obj.getId() == 0){ //L'objet vient d'etre crée et ne sort pas de la DB
			System.err.println("Erreur, vous ne pouvez pas supprimer un enregistrement sur base de cet objet.");
			System.err.println("Réessayez avec un objet provenant de la BDD.\n");
		}else{
			try {
				Statement requete = connect.createStatement();
				String sql = "DELETE FROM `semaine` WHERE id='" + obj.getId() + "';";
					
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
		
		if(obj.getId() == 0){ //L'objet vient d'etre crée et ne sort pas de la DB
			System.err.println("Erreur, vous ne pouvez pas mettre un enregistrement à jour sur base de cet objet.");
			System.err.println("Réessayez avec un objet provenant de la BDD.\n");
		}else{
			try {
				Statement requete = connect.createStatement();
				String sql = "UPDATE `semaine` " //(id,dateDebut,dateFin,descriptif,estCongeScolaire)
						   + "SET dateDebut='" + obj.getDateDebut() + "', dateFin='" + obj.getDateFin() + "', descriptif='" + obj.getDescriptif() 
						   + "', estCongeScolaire='" + obj.isEstCongeScolaire() + "' " 
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
	
	public Historique find(int id){
		Historique res = null;

		try {
			Statement requete = connect.createStatement();
			String sql = "SELECT * FROM `semaine` WHERE id='" + id + "';";
			ResultSet rs = requete.executeQuery(sql);
			
			if(rs != null){ //S'il existe un moniteur correspondant à cet ID, on charge ses acréditations
				res = new Historique(rs.getInt("id"), rs.getTimestamp("dateDebut"), rs.getTimestamp("dateFin"), 
						        rs.getString("descriptif"), rs.getBoolean("estCongeScolaire")); 
			}
			
			requete.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
		return res;
	}
	
	public List<Historique> getAll(){
		List<Historique> res = null;

		try {
			Statement requete = connect.createStatement();
			String sql = "SELECT * FROM `semaine`;";
			ResultSet rs = requete.executeQuery(sql);
			
			if(rs != null){
				res = new ArrayList<Historique>();
				
				while(rs.next()){ //S'il existe des moniteurs
					res.add(new Historique(rs.getInt("id"), rs.getTimestamp("dateDebut"), rs.getTimestamp("dateFin"), 
					        rs.getString("descriptif"), rs.getBoolean("estCongeScolaire"))); 
				}
			}
		
			requete.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
		return res;
	}
}