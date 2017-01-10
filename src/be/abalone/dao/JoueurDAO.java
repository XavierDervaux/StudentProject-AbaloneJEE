package be.abalone.dao;

import java.util.List;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import be.abalone.database.AbstractDAOFactory;
import be.abalone.database.DAOFactory;
import be.abalone.model.*;

public class JoueurDAO extends DAO<Joueur>{
	public JoueurDAO(Connection conn){
		super(conn);
	}
	
	public boolean create(Joueur obj){		
		int id;
		boolean res = true;

		try {
			String sql = "{? = call pkg_joueur.createjoueur(?,?,?)}";
			CallableStatement statement = connect.prepareCall(sql); 
			statement.registerOutParameter(1,Types.INTEGER); 
			statement.setString(2,obj.getPseudo()); 
			statement.setString(3,obj.getMdp()); 
			statement.setString(4,obj.getEmail()); 
			statement.execute(); 
			id = statement.getInt(1);
			
			if(id != 0){
				obj.setId(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			res = false;
		}
		return res;
	}
	
	public boolean delete(Joueur obj){
		/*boolean res = false;
		
		if(obj.getId() == 0){ //L'objet vient d'etre cr�� et ne sort pas de la DB
			System.err.println("Erreur, vous ne pouvez pas supprimer un enregistrement sur base de cet objet.");
			System.err.println("R�essayez avec un objet provenant de la BDD.\n");
		}else{
			try {
				Statement requete = connect.createStatement();
				String sql = "DELETE FROM joueur WHERE id='" + obj.getId() + "'";
				//Le sgbd supprimera seul les enregistrements correspondants � la table interm�diaire pour respecter l'int�grit� r�f�rentielle.
	
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
	
	public boolean update(Joueur obj){
		boolean res = false;
		
		if(obj.getId() == 0){ //L'objet vient d'etre crée et ne sort pas de la DB
			System.err.println("Erreur, vous ne pouvez pas mettre un enregistrement � jour sur base de cet objet.");
			System.err.println("R�essayez avec un objet provenant de la BDD.\n");
		}else{
			try {
				String sql = "{call pkg_joueur.updateJoueur(?,?,?,?)}";
				CallableStatement statement = connect.prepareCall(sql); 
				statement.setString(1,obj.getPseudo()); 
				statement.setString(2,obj.getMdp()); 
				statement.setString(3,obj.getEmail()); 
				statement.setInt(4,obj.getId()); 
				statement.execute();
				res = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return res;
	}
	
	public Joueur find(int id){
		DAOFactory adf = (DAOFactory) AbstractDAOFactory.getFactory(0);
		Joueur res = null;

		try {
			Statement requete = connect.createStatement();
			String sql = "SELECT * FROM joueur WHERE id='" + id + "'";
			ResultSet rs = requete.executeQuery(sql);
			
			if(rs != null){
				while(rs.next()){ 
					int id2 = rs.getInt("id");
					res = new Joueur( id2, rs.getString("pseudo"), rs.getString("mdp"), rs.getString("email"), adf.getAchievJoueurDAO().find(id2) ); 
				}
			}
			
			requete.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	public Joueur find(String email){
		DAOFactory adf = (DAOFactory) AbstractDAOFactory.getFactory(0);
		Joueur res = null;

		try {
			Statement requete = connect.createStatement();
			String sql = "SELECT * FROM joueur WHERE email='" + email + "'";
			ResultSet rs = requete.executeQuery(sql);
			
			if(rs != null){
				while(rs.next()){ 
					int id2 = rs.getInt("id");
					res = new Joueur( id2, rs.getString("pseudo"), rs.getString("mdp"), rs.getString("email"), adf.getAchievJoueurDAO().find(id2) ); 
				}
			}
			
			requete.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	public List<Joueur> getAll(){
		/*DAOFactory adf = (DAOFactory) AbstractDAOFactory.getFactory(0);
		List<Joueur> res = null;

		try {
			Statement requete = connect.createStatement();
			String sql = "SELECT * FROM joueur";
			ResultSet rs = requete.executeQuery(sql);
			
			if(rs != null){
				res = new ArrayList<Joueur>();
				
				while(rs.next()){ 
					int id2 = rs.getInt("id");
					res.add(new Joueur( id2, rs.getString("pseudo"), rs.getString("mdp"), rs.getString("email"), adf.getAchievJoueurDAO().find(id2))); 
				}
			}
		
			requete.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
		return res;*/
		return null;
	}
}