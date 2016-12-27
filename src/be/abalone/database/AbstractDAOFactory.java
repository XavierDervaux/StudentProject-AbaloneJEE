package be.abalone.database;

import java.util.List;
import be.abalone.dao.*;
import be.abalone.model.*;

public abstract class AbstractDAOFactory {
	public static final int ORACLE_DAO_FACTORY = 0;
	
	public abstract DAO<Achievement> getAchievementDAO();
	public abstract DAO<List<Achievement>> getAchievJoueurDAO();
	public abstract DAO<Joueur> getJoueurDAO();
	public abstract DAO<Historique> getHistoriqueDAO();
	
	public static AbstractDAOFactory getFactory(int type){
		switch(type){
			case ORACLE_DAO_FACTORY:
				return new DAOFactory();
			default:
				return null;
		}
	}
}