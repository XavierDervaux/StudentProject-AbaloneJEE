package be.abalone.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import be.abalone.database.AbstractDAOFactory;
import be.abalone.database.DAOFactory;
import be.abalone.model.*;

public class AchievJoueurDAO extends DAO<List<Achievement>>{
	public AchievJoueurDAO(Connection conn){
		super(conn);
	}
	
	public boolean create(List<Achievement> obj){		
		return false; //Utilisation impossible sans l'id du joueur
	}
	
	public boolean create(int id_joueur, int id_achiev){		
		boolean res = true;

		try {
			Statement requete = connect.createStatement();
			String sql = "INSERT INTO `fait` (id_joueur,id_achiev) "
					   + "VALUES (" + id_joueur + ",'" + id_achiev + "');";

			requete.executeUpdate(sql);
			requete.close();				
		} catch (SQLException sqlE) {
			//Le SGBD retournera une erreur sila combinaison des id existe déjà , a cause de la contrainte d'unicité des identifiants.
			//Cela signifie que la clé existe déjà , donc pas besoin de l'insérer et cela n'influe pas sur le reste du déroulement.
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			res = false;
		}

		return res;
	}
	
	public boolean delete(List<Achievement> obj){
		return false; //Les achievements ne sont jamais supprimés d'un compte, ils sont gagnés à vie
	}
	
	public boolean update(List<Achievement> obj){
		return false; //Utilisation impossible dans ce contexte
	}
	
	public List<Achievement> find(int id_joueur){
		List<Achievement> res = null;
		
		try {
			Statement requete = connect.createStatement();
			String sql = "SELECT * FROM `fait` WHERE id_joueur='" + id_joueur + "';";
			ResultSet rs = requete.executeQuery(sql);
			
			if(rs != null){ 
				res = new ArrayList<Achievement>();
				
				while(rs.next()){
					res.add(new Achievement(rs.getInt("id"), rs.getString("titre"), rs.getString("nom"), rs.getString("description")) ); 
				}
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
		return res;
	}
	
	public List<Joueur> find(int id_achiev, final int Joueur){ //Param int Joueur inutile, sert à faire un polymorphisme statique.
		List<Joueur> res = null;
		DAOFactory adf = (DAOFactory) AbstractDAOFactory.getFactory(0);
		
		try {
			Statement requete = connect.createStatement();
			String sql = "SELECT * FROM `fait` WHERE id_achivement='" + id_achiev + "';";
			ResultSet rs = requete.executeQuery(sql);
			
			if(rs != null){ 
				res = new ArrayList<Joueur>();
				
				while(rs.next()){
					res.add(adf.getJoueurDAO().find(rs.getInt("id") )); 
				}
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
		return res;
	}
	
	public List<List<Achievement>> getAll(){
		return null; //Usage inutile
	}
}
