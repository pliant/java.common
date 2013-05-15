package code.pliant.common.core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 * Helper class for working with java.sql classes.
 * 
 * @author Daniel Rugg
 */
public class SQL {
	
	/**
	 * Performs a close on a Connection, catching any exceptions thrown.
	 * @param connection The Connection to close.
	 */
	public static void close(Connection connection){
		try {
			connection.close();
		}
		catch (Exception e) {}
	}
	
	/**
	 * Performs a close on a Statement, catching any exceptions thrown.
	 * @param statement The Statement to close.
	 */
	public static void close(Statement statement){
		try {
			statement.close();
		}
		catch (Exception e) {}
	}
	
	/**
	 * Performs a close on a ResultSet, catching any exceptions thrown.
	 * @param resultSet The ResultSet to close.
	 */
	public static void close(ResultSet resultSet){
		try {
			resultSet.close();
		}
		catch (Exception e) {}
	}
}
