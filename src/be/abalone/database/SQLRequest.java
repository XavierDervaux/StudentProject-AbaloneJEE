package be.abalone.database;

import java.sql.*;

public class SQLRequest {
	static Connection connect = null;
	
	public static Connection getInstance(){
		if(connect == null){
			try{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				connect = DriverManager.getConnection(
						"jdbc:oracle:thin:" + 
						"@char-oracle11.condorcet.be:1521", 
						"exa7",
						"AbaloneJEE23#42@12");
			}catch (Exception e){
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