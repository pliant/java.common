package code.pliant.common.xml.bind;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.LocalDate;

/**
 * An XMLAdapter to marshal and unmarshal between a Joda LocalDate and a String.  To use, the adapter would 
 * need to be declared in a JAXB binding (XJB) file under global bindings, or through annotations.
 * </p>
 * 
 * Binding Example:
 <pre>
&lt;jaxb:globalBindings &gt;
	&lt;jaxb:javaType name=&quot;org.joda.time.LocalDate&quot; xmlType=&quot;xsd:date&quot; 
			parseMethod=&quot;code.pliant.common.xml.bind.LocalDateAdapter.parseLocalDate&quot; 
			printMethod=&quot;code.pliant.common.xml.bind.LocalDateAdapter.printLocalDate&quot;/&gt;
&lt;/jaxb:globalBindings&gt;
 </pre>
 * 
 * Annotation Example:
<pre>
&#64;XmlJavaTypeAdapters({
    &#64;XmlJavaTypeAdapter(type=org.joda.time.LocalDate.class, 
        value=code.pliant.common.xml.bind.LocalDateAdapter.class))
})
</pre>
 * 
 * @author Daniel Rugg
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate>{

	/**
	 * The singleton instance of the LocalDateAdapter.
	 */
	private static LocalDateAdapter instance = new LocalDateAdapter();
	
	/**
	 * Gets the singleton instance of the LocalDateAdapter.
	 * @return The singleton instance of this adapter.
	 */
	public static LocalDateAdapter getInstance(){return instance;}
	
	/* (non-Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
	 */
	@Override
	public String marshal(LocalDate value) throws Exception {
		return value.toString();
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
	 */
	@Override
	public LocalDate unmarshal(String value) throws Exception {
		return new LocalDate(value);
	}

	/**
	 * Static parse method for turning Strings into {@link LocalDate}s.  For use in XBJ global binding type 
	 * declarations.
	 * @param value The String to parse into a {@link LocalDate}.
	 * @return A {@link LocalDate}
	 */
	public static LocalDate parseLocalDate(String value){
		try {
			return instance.unmarshal(value);
		}
		catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * Static print method for turning {@link LocalDate}s into Strings.  For use in XBJ global binding type 
	 * declarations.
	 * @param value The {@link LocalDate} to print into a String.
	 * @return A String
	 */
	public static String printLocalDate(LocalDate value){
		try {
			return instance.marshal(value);
		}
		catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
}
