package code.pliant.common.xml.bind;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.DateMidnight;

/**
 * An XMLAdapter to marshal and unmarshal between a Joda DateMidnight and a String.  To use, the adapter would 
 * need to be declared in a JAXB binding (XJB) file under global bindings, or through annotations.
 * </p>
 * 
 * Binding Example:
 <pre>
&lt;jaxb:globalBindings &gt;
	&lt;jaxb:javaType name=&quot;org.joda.time.DateMidnight&quot; xmlType=&quot;xsd:date&quot; 
			parseMethod=&quot;code.pliant.common.xml.bind.DateMidnightAdapter.parseDateMidnight&quot; 
			printMethod=&quot;code.pliant.common.xml.bind.DateMidnightAdapter.printDateMidnight&quot;/&gt;
&lt;/jaxb:globalBindings&gt;
 </pre>
 * 
 * Annotation Example:
<pre>
&#64;XmlJavaTypeAdapters({
    &#64;XmlJavaTypeAdapter(type=org.joda.time.DateMidnight.class, 
        value=code.pliant.common.xml.bind.DateMidnightAdapter.class))
})
</pre>
 * 
 * @author Daniel Rugg
 */
public class DateMidnightAdapter extends XmlAdapter<String, DateMidnight>{

	/**
	 * The singleton instance of the DateMidnightAdapter.
	 */
	private static DateMidnightAdapter instance = new DateMidnightAdapter();
	
	/**
	 * Gets the singleton instance of the DateMidnightAdapter.
	 * @return The singleton instance of this adapter.
	 */
	public static DateMidnightAdapter getInstance(){return instance;}
	
	/* (non-Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
	 */
	@Override
	public String marshal(DateMidnight value) throws Exception {
		return value.toString();
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
	 */
	@Override
	public DateMidnight unmarshal(String value) throws Exception {
		return new DateMidnight(value);
	}

	/**
	 * Static parse method for turning Strings into {@link DateMidnight}s.  For use in XBJ global binding type 
	 * declarations.
	 * @param value The String to parse into a {@link DateMidnight}.
	 * @return A {@link DateMidnight}
	 */
	public static DateMidnight parseDateMidnight(String value){
		try {
			return instance.unmarshal(value);
		}
		catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * Static print method for turning {@link DateMidnight}s into Strings.  For use in XBJ global binding type 
	 * declarations.
	 * @param value The {@link DateMidnight} to print into a String.
	 * @return A String
	 */
	public static String printDateMidnight(DateMidnight value){
		try {
			return instance.marshal(value);
		}
		catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
}
