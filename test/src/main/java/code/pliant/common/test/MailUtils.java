package code.pliant.common.test;

import java.io.IOException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.junit.Assert;
import org.jvnet.mock_javamail.Mailbox;


/**
 * Utility for testing 
 * 
 * @author Daniel Rugg
 */
public class MailUtils {

	/**
	 * Clears Out The Email From A Mailbox.
	 * @param emailAddress
	 */
	public static void clear(String emailAddress){
		try {
			Mailbox mailbox = Mailbox.get(emailAddress);
			mailbox.clear();
		}
		catch (AddressException e) {
		}
	}
	
	/**
	 * Performs a basic check of the mailbox to ensure that a message was received.
	 * @return
	 */
	public static Message checkForMessage(String emailAddress){
		Mailbox mailbox = Mailbox.get(checkAddress(emailAddress));
		Assert.assertEquals(1, mailbox.size());
		Assert.assertEquals(1, mailbox.getNewMessageCount());
		Message message = mailbox.get(0);
		Assert.assertNotNull(message);
		return message;
	}
	
	/**
	 * Performs a basic check of the mailbox to ensure that a message was received.
	 * @return
	 */
	public static void checkMessageCount(String emailAddress, int count){
		Mailbox mailbox = Mailbox.get(checkAddress(emailAddress));
		Assert.assertEquals(count, mailbox.size());
	}
	
	/**
	 * Performs a basic check of the mailbox to ensure that messages were received.
	 * @return
	 */
	public static Message checkForMessages(String emailAddress){
		Mailbox mailbox = Mailbox.get(checkAddress(emailAddress));
		Assert.assertTrue(mailbox.size() > 0);
		Assert.assertTrue(mailbox.getNewMessageCount() > 0);
		Message message = mailbox.get(0);
		Assert.assertNotNull(message);
		return message;
	}
	
	/**
	 * Checks the validity of an email address.
	 * @param address
	 * @return
	 */
	public static InternetAddress checkAddress(String address){
		try {
			return new InternetAddress(address);
		}
		catch (AddressException e) {
			Assert.fail("Address '" + address + "' is not a valid email address.");
			return null;
		}
	}
	
	/**
	 * Checks the content of that message.
	 * @param message
	 * @param subject
	 * @param contentParts
	 * @throws MessagingException
	 * @throws IOException 
	 */
	public static void checkMessageContent(Message message, String subject, String... contentParts) throws MessagingException, IOException{
		if(subject != null){
			String messageSubject = message.getSubject();
			Assert.assertEquals(subject, messageSubject);	
		}
		
		String content = message.getContent().toString();
		for(String part : contentParts){
			Assert.assertTrue(part + " not found in " + content, -1 < content.indexOf(part));
		}
	}
	
	/**
	 * Performs the full check for an address and expected message.
	 * @param address
	 * @param subject
	 * @param contentParts
	 * @throws MessagingException
	 * @throws IOException 
	 */
	public static void check(String address, String subject, String... contentParts) throws MessagingException, IOException{
		checkMessageContent(checkForMessage(address), subject, contentParts);
	}
	
	/**
	 * Performs the full check for an address and expected messages.
	 * @param address
	 * @param subject
	 * @param contentParts
	 * @throws MessagingException
	 * @throws IOException 
	 */
	public static void checks(String address, String subject, String... contentParts) throws MessagingException, IOException{
		checkMessageContent(checkForMessages(address), subject, contentParts);
	}
}
