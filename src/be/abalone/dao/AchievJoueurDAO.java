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
		return false; //Utilisation impossible sans l'id du moniteur
	}
	
	public boolean create(int id_moniteur, int id_acreditation){		
		boolean res = true;

		try {
			Statement requete = connect.createStatement();
			String sql = "INSERT INTO `moniteurAcred` (id,id_acreditation) "
					   + "VALUES (" + id_moniteur + ",'" + id_acreditation + "');";

			requete.executeUpdate(sql);
			requete.close();				
		} catch (SQLException sqlE) {
			//Le SGBD retournera une erreur sila combinaison id_acred/id_moniteur existe déjà, a cause de la contrainte d'unicité des identifiants.
			//Cela signifie que la clé existe déjà, donc pas besoin de l'insérer et cela n'influe pas sur le reste du déroulement.
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			res = false;
		}

		return res;
	}
	
	public boolean delete(List<Achievement> obj){
		return false; //Les achievements ne sont jamais supprim�s d'un compte, ils sont gagn�s � vie
	}
	
	public boolean update(List<Achievement> obj){
		return false; //Utilisation impossible dans ce contexte
	}
	
	public List<Achievement> find(int id_moniteur){
		List<Achievement> res = null;
		
		try {
			Statement requete = connect.createStatement();
			String sql = "SELECT * FROM `moniteurAcred` WHERE id='" + id_moniteur + "';";
			ResultSet rs = requete.executeQuery(sql);
			
			if(rs != null){ 
				res = new ArrayList<Achievement>();
				
				while(rs.next()){
					res.add(new Achievement(rs.getInt("id"), rs.getString("id_acreditation") ) ); 
				}
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
		return res;
	}
	
	public List<Joueur> find(int id_achiev, final int Joueur){ //Param int Joueur inutile, sert � faire un polymorphisme statique.
		List<Joueur> res = null;
		DAOFactory adf = (DAOFactory) AbstractDAOFactory.getFactory(0);
		
		try {
			Statement requete = connect.createStatement();
			String sql = "SELECT * FROM `moniteurAcred` WHERE id_acreditation='" + id_achiev + "';";
			ResultSet rs = requete.executeQuery(sql);
			
			if(rs != null){ 
				res = new ArrayList<Joueur>();
				
				while(rs.next()){
					res.add(adf.getMoniteurDAO().find(rs.getInt("id") )); 
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
