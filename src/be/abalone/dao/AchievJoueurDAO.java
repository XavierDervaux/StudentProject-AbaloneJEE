package be.abalone.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import be.abalone.database.AbstractDAOFactory;
import be.abalone.database.DAOFactory;
import be.abalone.model.Achievement;

public class AchievJoueurDAO extends DAO<List<Achievement>>{
	public AchievJoueurDAO(Connection conn){
		super(conn);
	}
	
	public boolean create(List<Achievement> obj){		
		return false;
	}
	
	public boolean create(int id_joueur, int id_achiev){
		boolean res = true;

		try {
			String sql = "{call pkg_achievement.createAchievJoueur(?,?)}";
			CallableStatement statement = connect.prepareCall(sql); 
			statement.setInt(1,id_joueur); 
			statement.setInt(2,id_achiev); 
			statement.execute(); 
			//Le SGBD retournera une erreur sila combinaison des id existe déjà , a cause de la contrainte d'unicité des identifiants.
			//Cela signifie que la clé existe déjà , donc pas besoin de l'insérer et cela n'influe pas sur le reste du déroulement.
		} catch (Exception e) {
			e.printStackTrace();
			res = false;
		}

		return res;
	}
	
	public boolean delete(List<Achievement> obj){
		return false; 
	}
	
	public boolean update(List<Achievement> obj){
		return false; 
	}
	
	public List<Achievement> find(int id_joueur){
		List<Achievement> res = null;
		DAOFactory adf = (DAOFactory) AbstractDAOFactory.getFactory(0);
		
		try {
			Statement requete = connect.createStatement();
			String sql = "SELECT * FROM fait WHERE id_joueur='" + id_joueur + "'";
			ResultSet rs = requete.executeQuery(sql);
			
			if(rs != null){ 
				res = new ArrayList<Achievement>();
				
				while(rs.next()){
					res.add( adf.getAchievementDAO().find(rs.getInt("id_achievement")) ); 
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	/*public List<Joueur> find(int id_achiev, final int Joueur){ //Param int Joueur inutile, sert à faire un polymorphisme statique.
		List<Joueur> res = null;
		DAOFactory adf = (DAOFactory) AbstractDAOFactory.getFactory(0);
		
		try {
			Statement requete = connect.createStatement();
			String sql = "SELECT * FROM fait WHERE id_achievement='" + id_achiev + "'";
			ResultSet rs = requete.executeQuery(sql);
			
			if(rs != null){ 
				res = new ArrayList<Joueur>();
				
				while(rs.next()){
					res.add(adf.getJoueurDAO().find(rs.getInt("id_joueur") )); 
				}
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
		return res;
	}*/
	
	public List<List<Achievement>> getAll(){
		return null;
	}
}
