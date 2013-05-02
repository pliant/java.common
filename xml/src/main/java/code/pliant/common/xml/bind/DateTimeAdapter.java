package code.pliant.common.xml.bind;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.DateTime;

/**
 * An XMLAdapter to marshal and unmarshal between a Joda DateTime and a String.  To use, the adapter would 
 * need to be declared in a JAXB binding (XJB) file under global bindings, or through annotations.
 * </p>
 * 
 * Binding Example:
 <pre>
&lt;jaxb:globalBindings &gt;
	&lt;jaxb:javaType name=&quot;org.joda.time.DateTime&quot; xmlType=&quot;xsd:dateTime&quot; 
			parseMethod=&quot;code.pliant.common.xml.bind.DateTimeAdapter.parseDateTime&quot; 
			printMethod=&quot;code.pliant.common.xml.bind.DateTimeAdapter.printDateTime&quot;/&gt;
&lt;/jaxb:globalBindings&gt;
 </pre>
 * 
 * Annotation Example:
<pre>
&#64;XmlJavaTypeAdapters({
    &#64;XmlJavaTypeAdapter(type=org.joda.time.DateTime.class, 
        value=code.pliant.common.xml.bind.DateTimeAdapter.class))
})
</pre>
 * 
 * @author Daniel Rugg
 */
public class DateTimeAdapter extends XmlAdapter<String, DateTime>{

	/**
	 * The singleton instance of the DateTimeAdapter.
	 */
	private static DateTimeAdapter instance = new DateTimeAdapter();
	
	/**
	 * Gets the singleton instance of the DateTimeAdapter.
	 * @return The singleton instance of this adapter.
	 */
	public static DateTimeAdapter getInstance(){return instance;}
	
	/* (non-Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
	 */
	@Override
	public String marshal(DateTime value) throws Exception {
		return value.toString();
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
	 */
	@Override
	public DateTime unmarshal(String value) throws Exception {
		return new DateTime(value);
	}

	/**
	 * Static parse method for turning Strings into {@link DateTime}s.  For use in XBJ global binding type 
	 * declarations.
	 * @param value The String to parse into a {@link DateTime}.
	 * @return A {@link DateTime}
	 */
	public static DateTime parseDateTime(String value){
		try {
			return instance.unmarshal(value);
		}
		catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * Static print method for turning {@link DateTime}s into Strings.  For use in XBJ global binding type 
	 * declarations.
	 * @param value The {@link DateTime} to print into a String.
	 * @return A String
	 */
	public static String printDateTime(DateTime value){
		try {
			return instance.marshal(value);
		}
		catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
}
