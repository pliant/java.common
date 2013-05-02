package code.pliant.common.xml.bind;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.LocalDateTime;

/**
 * An XMLAdapter to marshal and unmarshal between a Joda LocalDateTime and a String.  To use, the adapter would 
 * need to be declared in a JAXB binding (XJB) file under global bindings, or through annotations.
 * </p>
 * 
 * Binding Example:
 <pre>
&lt;jaxb:globalBindings &gt;
	&lt;jaxb:javaType name=&quot;org.joda.time.LocalDateTime&quot; xmlType=&quot;xsd:dateTime&quot; 
			parseMethod=&quot;code.pliant.common.xml.bind.LocalDateTimeAdapter.parseLocalDateTime&quot; 
			printMethod=&quot;code.pliant.common.xml.bind.LocalDateTimeAdapter.printLocalDateTime&quot;/&gt;
&lt;/jaxb:globalBindings&gt;
 </pre>
 * 
 * Annotation Example:
<pre>
&#64;XmlJavaTypeAdapters({
    &#64;XmlJavaTypeAdapter(type=org.joda.time.LocalDateTime.class, 
        value=code.pliant.common.xml.bind.LocalDateTimeAdapter.class))
})
</pre>
 * 
 * @author Daniel Rugg
 */
public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime>{

	/**
	 * The singleton instance of the LocalDateTimeAdapter.
	 */
	private static LocalDateTimeAdapter instance = new LocalDateTimeAdapter();
	
	/**
	 * Gets the singleton instance of the LocalDateTimeAdapter.
	 * @return The singleton instance of this adapter.
	 */
	public static LocalDateTimeAdapter getInstance(){return instance;}
	
	/* (non-Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
	 */
	@Override
	public String marshal(LocalDateTime value) throws Exception {
		return value.toString();
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
	 */
	@Override
	public LocalDateTime unmarshal(String value) throws Exception {
		return new LocalDateTime(value);
	}

	/**
	 * Static parse method for turning Strings into {@link LocalDateTime}s.  For use in XBJ global binding type 
	 * declarations.
	 * @param value The String to parse into a {@link LocalDateTime}.
	 * @return A {@link LocalDateTime}
	 */
	public static LocalDateTime parseLocalDateTime(String value){
		try {
			return instance.unmarshal(value);
		}
		catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * Static print method for turning {@link LocalDateTime}s into Strings.  For use in XBJ global binding type 
	 * declarations.
	 * @param value The {@link LocalDateTime} to print into a String.
	 * @return A String
	 */
	public static String printLocalDateTime(LocalDateTime value){
		try {
			return instance.marshal(value);
		}
		catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
}
