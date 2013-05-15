package code.pliant.common.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;


/**
 * Facade object to use for creating messages that will be logged to a system log or 
 * wrapped within exceptions. Provides a centralized object to determine how data 
 * structures should be represented in logs, etc.
 * 
 * @author Daniel Rugg
 */
public class Message {
	
	/**
	 * Generic formatter used for date based values.  Format value is {@code yyyy-MM-dd HH:mm:ss z}.
	 */
	public static final DateFormat DATE_FORMATTER = 
		new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");

	private StringBuilder message = new StringBuilder();
	
	private String messageString = null;
	
	private boolean changed = true;
	
	/**
	 * Printed value used for nulls.  Value is {@code <null>}
	 */
	public static final String NULL = "<null>";
	

	/**
	 * Printed value used for separating key/value pairs.  Value is {@code ;}
	 */
	public static final char SEPORATOR = ';';

	/**
	 * Printed value used for equating keys to their values.  Value is {@code =}
	 */
	public static final char EQUALS = '=';

	/**
	 * Printed value used for starting a data structure.  Value is <code>{</code>
	 */
	public static final char CLOSURE_START = '{';
	
	/**
	 * Printed value used for ending a data structure.  Value is <code>}</code>
	 */
	public static final char CLOSURE_END = '}';
	
	/**
	 * Printed value used for starting an array of values.  Value is {@code [ }
	 */
	public static final char ARRAY_START = '[';
	
	/**
	 * Printed value used for ending an array of values.  Value is {@code [ }
	 */
	public static final char ARRAY_END = ']';
	
	/**
	 * Printed value used for quoting.  Value is {@code " }
	 */
	public static final char QUOTE = '"';
	
	/**
	 * Printed value used for mixed quoting.  Value is {@code ' }
	 */
	public static final char TICK = '\'';
	
	/**
	 * Newline character.  Value is {@code \n }
	 */
	public static final char NEWLINE = '\n';
	
	/**
	 * Tab character.  Value is {@code \n }
	 */
	public static final char TAB = '\t';
	
	/**
	 * Space character.  Value is {@code \n }
	 */
	public static final char SPACE = ' ';
	
	/**
	 * Comma character.  Value is {@code \n }
	 */
	public static final char COMMA = ',';
	
	/**
	 * Empty Constructor.
	 */
	public Message() {
		super();
	}
	
	/**
	 * Constructor with the start of a message.
	 */
	public Message(String start) {
		super();
		message.append(start);
	}

	/**
	 * Adds a character to the message without any formatting.
	 * @param text character to add.
	 * @return this instance.
	 */
	public Message add(char text){
		message.append(text);
		changed = true;
		return this;
	}

	/**
	 * Adds a text to the message without any formatting.
	 * @param text text to add.
	 * @return this instance.
	 */
	public Message add(String text){
		message.append(Message.toString(text));
		changed = true;
		return this;
	}

	/**
	 * Adds a value to the message without any formatting.
	 * @param value value to add.
	 * @return this instance.
	 */
	public Message add(Object value){
		message.append(Message.toString(value));
		changed = true;
		return this;
	}

	/**
	 * Adds a named value to the message.
	 * @param name The name of the value to add.
	 * @param value The value to add.
	 * @return this instance.
	 */
	public Message add(String name, Object value){
		addTabs();
		message.append(name).append(EQUALS).append(Message.toString(value))
			.append(SEPORATOR).append(NEWLINE);
		changed = true;
		return this;
	}

	/**
	 * Adds a named array of values to the message. 
	 * @param name The name of the value to add.
	 * @param values The array of values to add.
	 * @return this instance.
	 */
	public Message add(String name, Object... values){
		addTabs();
		message.append(name).append(EQUALS)
			.append(ARRAY_START);
		
		if(values == null){
			message.append(NULL);
			return this;
		}
		
		for(Object value : values){
			message.append(Message.toString(value)).append(COMMA);
		}
		message.append(ARRAY_END).append(SEPORATOR).append(NEWLINE);
		changed = true;
		return this;
	}


	/**
	 * Adds a named array of Strings to the message. 
	 * @param name The name of the value to add.
	 * @param values The array of values to add.
	 * @return this instance.
	 */
	public Message add(String name, String... values){
		addTabs();
		message.append(name).append(EQUALS)
			.append(ARRAY_START);
		
		if(values == null){
			message.append(NULL);
			return this;
		}
		
		for(String value : values){
			message.append(value).append(COMMA);
		}
		message.append(ARRAY_END).append(SEPORATOR).append(NEWLINE);
		changed = true;
		return this;
	}

	/**
	 * Adds a named Collection of values to the message. 
	 * @param name The name of the value to add.
	 * @param values The Collection of values to add.
	 * @return this instance.
	 */
	public Message add(String name, Collection<?> values){
		addTabs();
		message.append(name).append(EQUALS)
			.append(ARRAY_START);
		
		if(values == null){
			message.append(NULL);
			return this;
		}
		
		for(Object value : values){
			message.append(Message.toString(value)).append(COMMA);
		}
		message.append(ARRAY_END).append(SEPORATOR).append(NEWLINE);
		changed = true;
		return this;
	}

	private int tabs = 0;
	
	/**
	 * Increases the number of tabs that are used at the beginning of new lines in a message.
	 * @return this instance.
	 */
	public Message indent(){
		tabs++;
		return this;
	}
	
	/**
	 * Decreases the number of tabs that are used at the beginning of new lines in a message.
	 * @return this instance.
	 */
	public Message outdent(){
		if(tabs > 0)
			tabs--;
		return this;
	}
	
	/**
	 * Adds the tabs onto the message.
	 * @return this instance.
	 */
	public Message addTabs(){
		for(int i = 0; i < tabs; i++){
			message.append(TAB);
		}
		changed = true;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if(messageString == null || changed){
			messageString = message.toString();
			changed = false;
		}
		return messageString;
	}
	
	/**
	 * Returns the value the passed parameter represents.
	 * @param value The value to convert to a String, as interpreted by the Message.
	 * @return The String value of the object passed, as the Message would interpret that value.
	 */
	public static String toString(Object value){
		if(value != null){
			if(Calendar.class.isAssignableFrom(value.getClass())){
				return DATE_FORMATTER.format(((Calendar)value).getTime());
			}
			if(Date.class.isAssignableFrom(value.getClass())){
				return DATE_FORMATTER.format((Date)value);
			}
			if(TimeZone.class.isAssignableFrom(value.getClass())){
				return ((TimeZone)value).getID();
			}
			return value.toString();
		}
		return NULL;
	}
}
