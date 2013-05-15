package code.pliant.common.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hsqldb.jdbcDriver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

/**
 * Provides a simple way of creating a JNDI Context and registering named objects.
 * 
 * @author Daniel Rugg
 */
public class JNDIUtils {

	/**
	 * The default builder
	 */
	private static SimpleNamingContextBuilder builder = null;
	
	/**
	 * Creates and activates a {@link SimpleNamingContextBuilder} if one has not already been created 
	 * and activated.
	 * 
	 * @return The currently active {@link SimpleNamingContextBuilder}.
	 * @throws IllegalStateException Thrown out of the {@link SimpleNamingContextBuilder} when things go wrong.
	 * @throws NamingExceptionThrown out of the {@link SimpleNamingContextBuilder} when things go wrong.
	 */
	private static SimpleNamingContextBuilder getBuilder() throws IllegalStateException, NamingException{
		if(builder == null){
			builder = new SimpleNamingContextBuilder();
			builder.activate();
		}
		return builder;
	}
	
	/**
	 * Registers an instance of an Object on the {@link SimpleNamingContextBuilder} by the name provided in 
	 * order to allow access to that Object via JNDI.
	 * 
	 * @param name The name to register the instance under.
	 * @param value The instance to register.
	 * @throws IllegalStateException Thrown out of the {@link SimpleNamingContextBuilder} when things go wrong.
	 * @throws NamingExceptionThrown out of the {@link SimpleNamingContextBuilder} when things go wrong.
	 */
	public static void register(String name, Object value) throws IllegalStateException, NamingException{
		SimpleNamingContextBuilder builder = getBuilder();
		builder.bind(name, value);
	}
	
	/**
	 * Registers a map of name/value pairs with the {@link SimpleNamingContextBuilder}.
	 * 
	 * @param map The map of name/value pairs to register.
	 * @throws IllegalStateException Thrown out of the {@link SimpleNamingContextBuilder} when things go wrong.
	 * @throws NamingExceptionThrown out of the {@link SimpleNamingContextBuilder} when things go wrong.
	 */
	public static void register(Map<String, Object> map) throws IllegalStateException, NamingException{
		SimpleNamingContextBuilder builder = getBuilder();
		for(String name : map.keySet()){
			Object value = map.get(name);
			if(value != null){
				builder.bind(name, value);
			}
		}
	}
	
	/**
	 * Flag to indicate if a transaction manager has been loaded into the {@link SimpleNamingContextBuilder}.
	 */
	private static boolean jtaTransactionManagerLoaded = false;
	
	/**
	 * Registers the {@link PsuedoUserTransaction} under the name java:comp/UserTransaction with the
	 * {@link SimpleNamingContextBuilder}.
	 * 
	 * @throws IllegalStateException Thrown out of the {@link SimpleNamingContextBuilder} when things go wrong.
	 * @throws NamingExceptionThrown out of the {@link SimpleNamingContextBuilder} when things go wrong.
	 */
	public static void registerJTATransactionManager() throws IllegalStateException, NamingException{
		if(!jtaTransactionManagerLoaded){
			register("java:comp/UserTransaction", new PsuedoUserTransaction());
			jtaTransactionManagerLoaded = true;
		}
	}
	
	/**
	 * Deactivates and destroys the {@link SimpleNamingContextBuilder}.
	 */
	public static void shutdown(){
		builder.deactivate();
		builder = null;
	}
	
	/**
	 * Collection of database DataSources that have been registered.
	 */
	private static ArrayList<String> registeredDatabases = new ArrayList<String>();
	
	/**
	 * Used to register an in-memory HSQL database with the {@link SimpleNamingContextBuilder}.  
	 * Simplifies the creation/registration so you only need to provide the name of the database 
	 * and the JNDI lookup string.
	 * 
	 * @param jndiName The JNDI name to register the connection under, and to access the {@link DataSource} 
	 * with using JNDI.
	 * @param dbName The name to give the database.
	 */
	public static void registerDatabase(String jndiName, String dbName){
		registerDatabase(jndiName, jdbcDriver.class.getName(), "jdbc:hsqldb:mem:" + dbName, "sa", "");
	}
	
	/**
	 * Used to register an in-memory HSQL database with the {@link SimpleNamingContextBuilder}, creating any 
	 * number of schemas within that database on registration.  Simplifies the creation/registration so you 
	 * only need to provide the name of the database and the JNDI lookup string.
	 * 
	 * @param jndiName The JNDI name to register the connection under, and to access the {@link DataSource} 
	 * with using JNDI.
	 * @param dbName The name to give the database.
	 * @param schemas Schemas that must be found in the database.  If not found, will be created.
	 */
	public static void registerDatabaseWithSchemas(String jndiName, String dbName, String... schemas){
		registerDatabase(jndiName, jdbcDriver.class.getName(), "jdbc:hsqldb:mem:" + dbName, "sa", "");
		InitialContext context = null;
		DataSource dataSource = null;
		Connection connection = null;
		Statement statement = null;
		try {
			context = new InitialContext();
			dataSource = (DataSource)context.lookup(jndiName);
			connection = dataSource.getConnection();
			
			for(String schema : schemas){
				StringBuilder sql = new StringBuilder("CREATE SCHEMA ").append(schema).append(" AUTHORIZATION DBA;");
				statement = connection.createStatement();
				try {statement.execute(sql.toString());} catch (SQLException e) {}
				try {statement.close();} catch (SQLException e) {}
			}
		}
		catch (Exception e) {
			StringBuilder message = new StringBuilder("Failed to create schemas for HSQL database.  Schemas: [");
			for(String schema: schemas){
				message.append(schema).append(",");
			}
			message.append("]");
			throw new RuntimeException(message.toString(), e);
		}
		finally{
			try {connection.close();} catch (SQLException e) {}
			try {statement.close();} catch (SQLException e) {}
		}
	}
	
	/**
	 * Registers a JDBC driver and connection credentials, wrapping it in a {@link DataSource}, with the 
	 * {@link SimpleNamingContextBuilder}.
	 * 
	 * @param jndiName The JNDI name to register the connection under, and to access the {@link DataSource} 
	 * with using JNDI.
	 * @param driverClassName The name of the database driver class.
	 * @param url The connection URL to connect to the database with.
	 * @param userName The User ID used to connect to the database.
	 * @param password The Password used to connect to the database.
	 */
	public static void registerDatabase(String jndiName, String driverClassName, String url, String userName, String password){
		if(!registeredDatabases.contains(jndiName)){
			DriverManagerDataSource ds = new DriverManagerDataSource(url, userName, password);
			ds.setDriverClassName(driverClassName);
			try {
				JNDIUtils.register(jndiName, ds);
				registeredDatabases.add(jndiName);
			}
			catch (Exception e) {
				StringBuilder message = new StringBuilder("Failed to initialize a NamingContext for the jndi/test datasource.  All tests will fail.  Reason:")
					.append(e.getMessage());
				System.out.println(message.toString());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Registers an Oracle database connection with the {@link SimpleNamingContextBuilder}.
	 * @param jndiName The JNDI name to register the connection under, and to access the {@link DataSource} 
	 * with using JNDI.
	 * @param url The connection URL to connect to the database with.
	 * @param userName The User ID used to connect to the database.
	 * @param password The Password used to connect to the database.
	 */
	public static void registerOracleDatabase(String jndiName, String url, String userName, String password){
		final String oracleDriverClassName = "oracle.jdbc.driver.OracleDriver";
		registerDatabase(jndiName, oracleDriverClassName, url, userName, password);
	}
}
