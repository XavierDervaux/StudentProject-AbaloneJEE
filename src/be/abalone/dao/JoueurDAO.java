package be.abalone.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import be.abalone.model.*;

public class JoueurDAO extends DAO<Joueur>{
	public JoueurDAO(Connection conn){
		super(conn);
	}
	
	public boolean create(Joueur obj){		
		int id;
		boolean res = true;

		try {
			Statement requete = connect.createStatement();
			String sql = "INSERT INTO joueur (id,pseudo,mdp,email) "
					   + "VALUES ('', '" + obj.getPseudo() + "','" + obj.getMdp() + "','" + obj.getEmail() + "')";
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

		return res;
	}
	
	public boolean delete(Joueur obj){
		boolean res = false;
		
		if(obj.getId() == 0){ //L'objet vient d'etre créé et ne sort pas de la DB
			System.err.println("Erreur, vous ne pouvez pas supprimer un enregistrement sur base de cet objet.");
			System.err.println("Réessayez avec un objet provenant de la BDD.\n");
		}else{
			try {
				Statement requete = connect.createStatement();
				String sql = "DELETE FROM joueur WHERE id='" + obj.getId() + "'";
				//Le sgbd supprimera seul les enregistrements correspondants à  la table intermédiaire pour respecter l'intégrité référentielle.
	
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
		
		if(obj.getId() == 0){ //L'objet vient d'etre crÃ©e et ne sort pas de la DB
			System.err.println("Erreur, vous ne pouvez pas mettre un enregistrement à jour sur base de cet objet.");
			System.err.println("Réessayez avec un objet provenant de la BDD.\n");
		}else{
			try {
				Statement requete = connect.createStatement();
				String sql = "UPDATE joueur "
						   + "SET pseudo='" + obj.getPseudo() + "', mdp='" + obj.getMdp() + "', email='" + obj.getEmail() + "' " 
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
	
	public Joueur find(int id){
		Joueur res = null;

		try {
			Statement requete = connect.createStatement();
			String sql = "SELECT * FROM joueur WHERE id='" + id + "'";
			ResultSet rs = requete.executeQuery(sql);
			
			if(rs != null){
				res = new Joueur( rs.getInt("id"), rs.getString("pseudo"), rs.getString("mdp"), rs.getString("email") ); 
			}
			
			requete.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
		return res;
	}
	
	public List<Joueur> getAll(){
		List<Joueur> res = null;

		try {
			Statement requete = connect.createStatement();
			String sql = "SELECT * FROM joueur";
			ResultSet rs = requete.executeQuery(sql);
			
			if(rs != null){
				res = new ArrayList<Joueur>();
				
				while(rs.next()){ 
					res.add(new Joueur( rs.getInt("id"), rs.getString("pseudo"), rs.getString("mdp"), rs.getString("email") )); 
				}
			}
		
			requete.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
		return res;
	}
}