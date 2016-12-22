package be.abalone.database;

import java.sql.*;

public class SQLRequest {
	private static Connection connect = null;
	
	public static Connection getInstance(){
		if(connect == null){
			try{
				String adress = "char-oracle11.condorcet.be:1521";
				String username = "exa7";
				String password = "AbaloneJEE23#42@12";
				connect = DriverManager.getConnection(
						"jdbc:oracle:thin:@" + adress + ":orcl, " + username + ", " + password);
			}catch (SQLException e){
				System.err.println(e.getMessage());
			}
		}		
		return connect;	
	}
	
	public static void close() {
		try{
			if (!connect.isClosed()){
				connect.close();
			}
		}catch (SQLException e){
			System.err.println(e.getMessage());
		}
	}
}