package code.pliant.common.xml.bind;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.LocalTime;

/**
 * An XMLAdapter to marshal and unmarshal between a Joda LocalTime and a String.  To use, the adapter would 
 * need to be declared in a JAXB binding (XJB) file under global bindings, or through annotations.
 * </p>
 * 
 * Binding Example:
 <pre>
&lt;jaxb:globalBindings &gt;
	&lt;jaxb:javaType name=&quot;org.joda.time.LocalTime&quot; xmlType=&quot;xsd:time&quot; 
			parseMethod=&quot;code.pliant.common.xml.bind.LocalTimeAdapter.parseLocalTime&quot; 
			printMethod=&quot;code.pliant.common.xml.bind.LocalTimeAdapter.printLocalTime&quot;/&gt;
&lt;/jaxb:globalBindings&gt;
 </pre>
 * 
 * Annotation Example:
<pre>
&#64;XmlJavaTypeAdapters({
    &#64;XmlJavaTypeAdapter(type=org.joda.time.LocalTime.class, 
        value=code.pliant.common.xml.bind.LocalTimeAdapter.class))
})
</pre>
 * 
 * @author Daniel Rugg
 */
public class LocalTimeAdapter extends XmlAdapter<String, LocalTime>{

	/**
	 * The singleton instance of the LocalTimeAdapter.
	 */
	private static LocalTimeAdapter instance = new LocalTimeAdapter();
	
	/**
	 * Gets the singleton instance of the LocalTimeAdapter.
	 * @return The singleton instance of this adapter.
	 */
	public static LocalTimeAdapter getInstance(){return instance;}
	
	/* (non-Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
	 */
	@Override
	public String marshal(LocalTime value) throws Exception {
		return value.toString();
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
	 */
	@Override
	public LocalTime unmarshal(String value) throws Exception {
		return new LocalTime(value);
	}

	/**
	 * Static parse method for turning Strings into {@link LocalTime}s.  For use in XBJ global binding type 
	 * declarations.
	 * @param value The String to parse into a {@link LocalTime}.
	 * @return A {@link LocalTime}
	 */
	public static LocalTime parseLocalTime(String value){
		try {
			return instance.unmarshal(value);
		}
		catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * Static print method for turning {@link LocalTime}s into Strings.  For use in XBJ global binding type 
	 * declarations.
	 * @param value The {@link LocalTime} to print into a String.
	 * @return A String
	 */
	public static String printLocalTime(LocalTime value){
		try {
			return instance.marshal(value);
		}
		catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
}
