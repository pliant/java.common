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
	 * @param target
	 */
	public static void close(Connection target){
		try {
			target.close();
		}
		catch (Exception e) {}
	}
	
	/**
	 * Performs a close on a Statement, catching any exceptions thrown.
	 * @param target
	 */
	public static void close(Statement target){
		try {
			target.close();
		}
		catch (Exception e) {}
	}
	
	/**
	 * Performs a close on a ResultSet, catching any exceptions thrown.
	 * @param target
	 */
	public static void close(ResultSet target){
		try {
			target.close();
		}
		catch (Exception e) {}
	}
}
