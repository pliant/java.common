package code.pliant.common.jms;

import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

/**
 * Helper utility for working with JMS destinations.
 * 
 * @author Daniel Rugg, OBIS
 */
public class Destinations {
	
	/**
	 * Verifies that the list of queue names provided exists within a JMS server.  If they do not exist, they are created.
	 * @param factory The JMS ConnectionFactory used to connect to the JMS server.
	 * @param queueNames The list of queue names to validate against.
	 * @throws JMSException
	 */
	public static void verifyOrCreateQueues(ConnectionFactory factory, List<String> queueNames) throws JMSException{
		Connection connection = null;
		try {
			connection = factory.createConnection();
			verifyOrCreateQueues(connection, queueNames);
		}
		catch (JMSException e) {
			throw e;
		}
		finally{
			try {
				connection.close();
			}
			catch (JMSException e) {}
		}
	}

	/**
	 * Verifies that the list of queue names provided exists within a JMS server.  If they do not exist, they are created.
	 * @param connection The JMS Connection used to connect to the JMS server.
	 * @param queueNames The list of queue names to validate against.
	 * @throws JMSException
	 */
	public static void verifyOrCreateQueues(Connection connection, List<String> queueNames) throws JMSException{
		Session session = null;
		try {
			session = connection.createSession(true, 0);
			session.commit();
		}
		catch (JMSException e) {
			session.rollback();
			throw e;
		}
		finally{
			session.close();
		}
	}
	/**
	 * Verifies that the list of queue names provided exists within a JMS server.  If they do not exist, they are created.
	 * @param session The JMS Session used to perform validation in.
	 * @param queueNames The list of queue names to validate against.
	 * @throws JMSException
	 */
	public static void verifyOrCreateQueues(Session session, List<String> queueNames) throws JMSException{
		for(String queueName : queueNames){
			try {
				// TODO: session.createQueue is not portable...need to figure out alternative.
				session.createQueue(queueName);
			}
			catch (JMSException e) {
				throw e;
			}
		}
	}
}
