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
public class JNDIHelper {

	/**
	 * The default builder
	 */
	private static SimpleNamingContextBuilder builder = null;
	
	/**
	 * 
	 * @return
	 * @throws IllegalStateException
	 * @throws NamingException
	 */
	private static SimpleNamingContextBuilder getBuilder() throws IllegalStateException, NamingException{
		if(builder == null){
			builder = new SimpleNamingContextBuilder();
			builder.activate();
		}
		return builder;
	}
	
	/**
	 * 
	 * @param name
	 * @param value
	 * @throws IllegalStateException
	 * @throws NamingException
	 */
	public static void register(String name, Object value) throws IllegalStateException, NamingException{
		SimpleNamingContextBuilder builder = getBuilder();
		builder.bind(name, value);
	}
	
	/**
	 * 
	 * @param map
	 * @throws IllegalStateException
	 * @throws NamingException
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
	
	private static boolean jtaTransactionManagerLoaded = false;
	
	/**
	 * Registers the TestUserTransaction under the name java:comp/UserTransaction
	 * @throws NamingException 
	 * @throws IllegalStateException 
	 */
	public static void registerJTATransactionManager() throws IllegalStateException, NamingException{
		if(!jtaTransactionManagerLoaded){
			register("java:comp/UserTransaction", new PsuedoUserTransaction());
			jtaTransactionManagerLoaded = true;
		}
	}
	
	/**
	 * Shuts down the JNDI engine.
	 */
	public static void shutdown(){
		builder.deactivate();
		builder = null;
	}
	
	private static ArrayList<String> registeredDatabases = new ArrayList<String>();
	
	/**
	 * Used to register a HSQL database.  Simplifies the creation/registration so you only need to provide 
	 * the name of the database and the JNDI lookup string.
	 * @param jndiName The JNDI name to register the connection under.
	 * @param dbName The name to give the database.
	 */
	public static void registerDatabase(String jndiName, String dbName){
		registerDatabase(jndiName, jdbcDriver.class.getName(), "jdbc:hsqldb:mem:" + dbName, "sa", "");
	}
	
	/**
	 * Used to register a HSQL database.  Simplifies the creation/registration so you only need to provide 
	 * the name of the database and the JNDI lookup string.
	 * @param jndiName The JNDI name to register the connection under.
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
	 * Registers a JDBC driver and connection credentials with a datasource that is made availabe through 
	 * the test JNDI Naming Context.
	 * @param jndiName
	 * @param driverClassName
	 * @param url
	 * @param userName
	 * @param password
	 */
	public static void registerDatabase(String jndiName, String driverClassName, String url, String userName, String password){
		if(!registeredDatabases.contains(jndiName)){
			DriverManagerDataSource ds = new DriverManagerDataSource(url, userName, password);
			ds.setDriverClassName(driverClassName);
			try {
				JNDIHelper.register(jndiName, ds);
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
	 * Registers An Oracle Database Connection With A JNDI Name
	 * @param jndiName
	 * @param url
	 * @param userName
	 * @param password
	 */
	public static void registerOracleDatabase(String jndiName, String url, String userName, String password){
		final String oracleDriverClassName = "oracle.jdbc.driver.OracleDriver";
		registerDatabase(jndiName, oracleDriverClassName, url, userName, password);
	}
}
