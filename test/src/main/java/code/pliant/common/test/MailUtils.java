package code.pliant.common.test;

import java.io.IOException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.junit.Assert;
import org.jvnet.mock_javamail.Mailbox;


/**
 * Utility for testing email processing.
 * 
 * @author Daniel Rugg
 */
public class MailUtils {

	/**
	 * Clears out the email from a mailbox.
	 * 
	 * @param emailAddress The email address identifying the MailBox to clear out.
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
	 * Performs a basic check of the mailbox to ensure that a single message was received.  
	 * Declares assertions that there must be 1 new message, and only that 1 new message, in 
	 * the mailbox.
	 * 
	 * @param emailAddress The email address identifying the MailBox to check for message.
	 * @return The message that was received.
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
	 * Performs a basic check of the mailbox to ensure that a message was received.  Asserts 
	 * that the mailbox size must match the size given.
	 * 
	 * @param emailAddress The email address identifying the MailBox to check.
	 * @param count The number of messages that must be in the MailBox.
	 */
	public static void checkMessageCount(String emailAddress, int count){
		Mailbox mailbox = Mailbox.get(checkAddress(emailAddress));
		Assert.assertEquals(count, mailbox.size());
	}
	
	/**
	 * Performs a basic check of the mailbox to ensure that new messages exist in the MailBox.
	 * 
	 * @param emailAddress The email address identifying the MailBox to check.
	 * @return The first Message in the MailBox.
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
	 * 
	 * @param address The address to check.
	 * @return If the address is valid, the {@link InternetAddress} representation is returned, else an 
	 * assertion error is thrown.
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
	 * Checks the subject and content of a message, asserting that the message subject equals subject provided, 
	 * and that the message content contains the content parts provided.
	 * 
	 * @param message The message to inspect.
	 * @param subject The subject value to match against.
	 * @param contentParts The content parts to look for in the message.
	 * @throws MessagingException Throws from standard inspection of the Message.
	 * @throws IOExceptionThrows from standard inspection of the Message.
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
	 * Checks the subject and content of a message, asserting that the message subject equals subject provided, 
	 * and that the message content contains the content parts provided.
	 * 
	 * @param address The email address whose MailBox to inspect for a message.
	 * @param subject The subject value to match against.
	 * @param contentParts The content parts to look for in the message.
	 * @throws MessagingException Throws from standard inspection of the Message.
	 * @throws IOExceptionThrows from standard inspection of the Message.
	 */
	public static void check(String address, String subject, String... contentParts) throws MessagingException, IOException{
		checkMessageContent(checkForMessage(address), subject, contentParts);
	}
	
	/**
	 * Checks the subject and content of a message, asserting that the message subject equals subject provided, 
	 * and that the message content contains the content parts provided.
	 * 
	 * @param address The email address whose MailBox to inspect for a messages.
	 * @param subject The subject value to match against.
	 * @param contentParts The content parts to look for in the message.
	 * @throws MessagingException Throws from standard inspection of the Message.
	 * @throws IOExceptionThrows from standard inspection of the Message.
	 */
	public static void checks(String address, String subject, String... contentParts) throws MessagingException, IOException{
		checkMessageContent(checkForMessages(address), subject, contentParts);
	}
}
