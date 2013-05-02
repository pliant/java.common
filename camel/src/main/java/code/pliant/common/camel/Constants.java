package code.pliant.common.camel;


/**
 * Constants class that provide common values that are used throughout our applications.
 * 
 * 
 * @author Daniel Rugg
 */
public class Constants {
	
	/**
	 * Provides the prefix to all of the values.
	 * @param suffix
	 * @return
	 */
	private static String name(String suffix){
		final String prefix = "Hssc";
		return prefix + suffix;
	}
	
	/**
	 * The key used to store the transaction id value in the message header when it first enters our 
	 * system.  Value is <code>HsscTransactionID</code>
	 */
	public static final String TRANSACTION_ID = name("TransactionID");
	
	/**
	 * The key used to store the transaction id timestamp value in the message header when it first enters our 
	 * system.  Value is <code>HsscTransactionIDTimestamp</code>
	 */
	public static final String TRANSACTION_ID_TIMESTAMP = name("TransactionIDTimestamp");
	
	/**
	 * The key used to store the message value in the message header when it first enters our 
	 * system.  Value is <code>HsscSource</code>
	 */
	public static final String MESSAGE_SOURCE = name("Source");
	
	/**
	 * The key used to store the format of the message body in the message header when it first enters our 
	 * system.  Value is <code>HsscFormat</code>
	 */
	public static final String MESSAGE_FORMAT = name("Format");
	
	/**
	 * The key used to store the specific message feed in the message header when it first enters our 
	 * system.  Value is <code>HsscFeed</code>
	 */
	public static final String MESSAGE_FEED = name("Feed");
	
	/**
	 * The key used to store the specific event that triggered the message in the message header when it first enters our 
	 * system.  Value is <code>HsscEvent</code>
	 */
	public static final String MESSAGE_EVENT = name("Event");

	/**
	 * Value to indicate a message is in the format of HL7.
	 */
	public static final String MESSAGE_FORMAT_HL7 = "HL7";
	
	/**
	 * Value to indicate a message is in the format of XML.
	 */
	public static final String MESSAGE_FORMAT_XML = "XML";
	
	/**
	 * Value to indicate a message is in the format of JSON.
	 */
	public static final String MESSAGE_FORMAT_JSON = "JSON";

	/**
	 * The key used to store the exception class name stored in the Exchange when an error occurs. 
	 * Value is <code>HsscExchangeExceptionType</code>
	 */
	public static final String EXCHANGE_EXCEPTION_TYPE = name("ExchangeExceptionType");
	
	/**
	 * The key used to store the exception message stored in the Exchange when an error occurs. 
	 * Value is <code>HsscExchangeExceptionMessage</code>
	 */
	public static final String EXCHANGE_EXCEPTION_MESSAGE = name("ExchangeExceptionMessage");
	
	/**
	 * The key used to store the exception stack stored in the Exchange when an error occurs. 
	 * Value is <code>HsscExchangeExceptionStack</code>
	 */
	public static final String EXCHANGE_EXCEPTION_STACK = name("ExchangeExceptionStack");

	/**
	 * The key used to store the exception class name stored in the Message when an error occurs. 
	 * Value is <code>HsscMessageExceptionType</code>
	 */
	public static final String MESSAGE_EXCEPTION_TYPE = name("MessageExceptionType");
	
	/**
	 * The key used to store the exception message stored in the Message when an error occurs. 
	 * Value is <code>HsscMessageExceptionMessage</code>
	 */
	public static final String MESSAGE_EXCEPTION_MESSAGE = name("MessageExceptionMessage");
	
	/**
	 * The key used to store the exception stack stored in the Message when an error occurs. 
	 * Value is <code>HsscMessageExceptionStack</code>
	 */
	public static final String MESSAGE_EXCEPTION_STACK = name("MessageExceptionStack");
}
