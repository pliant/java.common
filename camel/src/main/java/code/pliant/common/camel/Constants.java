package code.pliant.common.camel;


/**
 * Constants class that provide common values that are used throughout our applications.
 * 
 * 
 * @author Daniel Rugg
 */
public class Constants {
	
	/**
	 * The key used to store the transaction id value in the message header when it first enters our 
	 * system.  Value is <code>HsscTransactionID</code>
	 */
	public static final String TRANSACTION_ID = "PliantTransactionID";
	
	/**
	 * The key used to store the transaction id timestamp value in the message header when it first enters our 
	 * system.  Value is <code>HsscTransactionIDTimestamp</code>
	 */
	public static final String TRANSACTION_ID_TIMESTAMP = "PliantTransactionIDTimestamp";
	
	/**
	 * The key used to store the message value in the message header when it first enters our 
	 * system.  Value is <code>HsscSource</code>
	 */
	public static final String MESSAGE_SOURCE = "PliantSource";
	
	/**
	 * The key used to store the format of the message body in the message header when it first enters our 
	 * system.  Value is <code>HsscFormat</code>
	 */
	public static final String MESSAGE_FORMAT = "PliantFormat";
	
	/**
	 * The key used to store the specific message feed in the message header when it first enters our 
	 * system.  Value is <code>HsscFeed</code>
	 */
	public static final String MESSAGE_FEED = "PliantFeed";
	
	/**
	 * The key used to store the specific event that triggered the message in the message header when it first enters our 
	 * system.  Value is <code>HsscEvent</code>
	 */
	public static final String MESSAGE_EVENT = "PliantEvent";

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
	public static final String EXCHANGE_EXCEPTION_TYPE = "PliantExchangeExceptionType";
	
	/**
	 * The key used to store the exception message stored in the Exchange when an error occurs. 
	 * Value is <code>HsscExchangeExceptionMessage</code>
	 */
	public static final String EXCHANGE_EXCEPTION_MESSAGE = "PliantExchangeExceptionMessage";
	
	/**
	 * The key used to store the exception stack stored in the Exchange when an error occurs. 
	 * Value is <code>HsscExchangeExceptionStack</code>
	 */
	public static final String EXCHANGE_EXCEPTION_STACK = "PliantExchangeExceptionStack";

	/**
	 * The key used to store the exception class name stored in the Message when an error occurs. 
	 * Value is <code>HsscMessageExceptionType</code>
	 */
	public static final String MESSAGE_EXCEPTION_TYPE = "PliantMessageExceptionType";
	
	/**
	 * The key used to store the exception message stored in the Message when an error occurs. 
	 * Value is <code>HsscMessageExceptionMessage</code>
	 */
	public static final String MESSAGE_EXCEPTION_MESSAGE = "PliantMessageExceptionMessage";
	
	/**
	 * The key used to store the exception stack stored in the Message when an error occurs. 
	 * Value is <code>HsscMessageExceptionStack</code>
	 */
	public static final String MESSAGE_EXCEPTION_STACK = "PliantMessageExceptionStack";
}
