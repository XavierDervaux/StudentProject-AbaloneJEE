package be.abalone.database;

import java.sql.*;
import java.util.List;
import be.abalone.dao.*;
import be.abalone.model.*;

public class DAOFactory extends AbstractDAOFactory{
	private static final Connection conn = SQLRequest.getInstance();

	public DAO<Achievement> getAchievementDAO(){
		return new AchievementDAO(conn);
	}
	
	public DAO<List<Achievement>> getAchievJoueurDAO(){
		return new AchievJoueurDAO(conn);
	}
	
	public DAO<Joueur> getJoueurDAO(){
		return new JoueurDAO(conn);
	}
	
	public DAO<Historique> getHistoriqueDAO(){
		return new HistoriqueDAO(conn);
	}
}