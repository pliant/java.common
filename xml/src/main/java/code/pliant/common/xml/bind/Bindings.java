/**
 * 
 */
package code.pliant.common.xml.bind;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import code.pliant.common.core.Strings;


/**
 * Utility class to help with Domain operations.
 * 
 * @author Daniel Rugg
 */
public class Bindings {

	
	/**
	 * Used by JAXB to turn a {@link TimeZone} into it's XML based value.
	 * @param timeZone
	 * @return The string representation of a {@link TimeZone}.
	 */
	public static String printTimeZone(TimeZone timeZone){
		if(timeZone == null){
			return "";
		}
		return timeZone.getID();
	}

	/**
	 * Used by JAXB to turn a {@link Locale} into it's XML based value.
	 * @param locale The {@link Locale} to transform.
	 * @return The string representation of a {@link Locale}.
	 */
	public static String printLocale(Locale locale){
		if(locale == null){
			return "";
		}
		return locale.getLanguage();
	}


	/**
	 * Used by JAXB to turn a string value into a {@link Locale}.
	 * @param value The two letter language code of the {@link Locale} to use.
	 * @return A {@link Locale}.
	 */
	public static Locale parseLocale(String value){
		if(Strings.isValid(value)){
			return Locale.getDefault();
		}
		return new Locale(value);
	}

	/**
	 * Transforms an Object into an XML representation of that Object, returning the XML as a String.
	 * @param vo The Object to transform into XML
	 * @return The XML string representation of the Object
	 * @throws JAXBException If the Object is unable to be transformed to XML.
	 */
	public static String marshal(Object vo) throws JAXBException{
		StringWriter writer = new StringWriter();
		marshal(vo, writer);
		return writer.toString();
	}
	
	/**
	 * Transforms an Object into an XML representation of that Object, writing the XML to the provided {@link OutputStream}.
	 * @param vo The Object to transform.
	 * @param out The {@link OutputStream} to write to.
	 * @throws JAXBException If the Object is unable to be transformed to XML or written to the {@link OutputStream}.
	 */
	public static void marshal(Object vo, OutputStream out) throws JAXBException{
		marshal(vo, new OutputStreamWriter(out));
	}
	
	/**
	 * Transforms an Object into an XML representation of that Object, writing the XML to the provided {@link Writer}.
	 * @param vo The Object to transform.
	 * @param writer The {@link Writer} to write to.
	 * @throws JAXBException If the Object is unable to be transformed to XML or written to the {@link OutputStream}.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void marshal(Object vo, Writer writer) throws JAXBException{
		try {
			// Process Any Object with the @XMLRootElement annotation.
			Marshaller marshaller = getJAXBContext().createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(vo, writer);
		}
		catch (JAXBException e) {
			// No Annotation - Attempt to marshall using a wrapper.
			try{
				Marshaller marshaller = getJAXBContext().createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				JAXBElement element = new JAXBElement(getQName(vo.getClass()), vo.getClass(), vo);
				marshaller.marshal(element, writer);
			}
			catch(JAXBException e1){
				// Throw original exception
				throw e;
			}
		}
	}

	/**
	 * Transforms an XML-formatted String into an Object.
	 * @param klass The type of Class to unmarshal the XML into.
	 * @param xml The XML string to unmarshal
	 * @return An instance of provided class.
	 * @throws JAXBException If the XML is unable to be transformed to an instance of the class indicated.
	 */
	public static <T> T unmarshal(Class<T> klass, String xml) throws JAXBException{
		return unmarshal(klass, new StreamSource(new StringReader(xml)));
	}

	/**
	 * Transforms an XML from an {@link InputStream} into an Object.
	 * @param klass The type of Class to unmarshal the XML into.
	 * @param in The {@link InputStream} with the XML content to unmarshal
	 * @return An instance of provided class.
	 * @throws JAXBException If the XML is unable to be transformed to an instance of the class indicated.
	 */
	public static <T> T unmarshal(Class<T> klass, InputStream in) throws JAXBException{
		return unmarshal(klass, new StreamSource(in));
	}

	/**
	 * Transforms an XML from an {@link Source} into an Object.
	 * @param klass The type of Class to unmarshal the XML into.
	 * @param source The {@link Source} with the XML content to unmarshal
	 * @return An instance of provided class.
	 * @throws JAXBException If the XML is unable to be transformed to an instance of the class indicated.
	 */
	public static <T> T unmarshal(Class<T> klass, Source source) throws JAXBException{
		try {
			return getJAXBContext().createUnmarshaller().unmarshal(source, klass).getValue();
		}
		catch (JAXBException e) {
			throw new JAXBException(e);
		}
	}


	/**
	 * Transforms an XML-formatted String into an Object.
	 * @param xml The XML string to unmarshal
	 * @return An Object of the appropriate type that the XML was unmarshalled into.
	 * @throws JAXBException If the XML is unable to be transformed to an Object.
	 */
	public static Object unmarshal(String xml) throws JAXBException{
		return unmarshal(new StreamSource(new StringReader(xml)));
	}

	/**
	 * Transforms an XML from an {@link InputStream} into an Object.
	 * @param in The {@link InputStream} with the XML content to unmarshal
	 * @return An Object of the appropriate type that the XML was unmarshalled into.
	 * @throws JAXBException If the XML is unable to be transformed to an Object.
	 */
	public static Object unmarshal(InputStream in) throws JAXBException{
		return unmarshal(new StreamSource(in));
	}

	/**
	 * Transforms an XML from an {@link Source} into an Object.
	 * @param source The {@link Source} with the XML content to unmarshal
	 * @return An Object of the appropriate type that the XML was unmarshalled into.
	 * @throws JAXBException If the XML is unable to be transformed to an Object.
	 */
	@SuppressWarnings("rawtypes")
	public static Object unmarshal(Source source) throws JAXBException{
		try {
			Object value = getJAXBContext().createUnmarshaller().unmarshal(source);
			if(value instanceof JAXBElement){
				value = ((JAXBElement)value).getValue();
			}
			return value;
		}
		catch (JAXBException e) {
			throw new JAXBException(e);
		}
	}
	private static JAXBContext jaxbContext = null;

	/**
	 * Obtains the {@link JAXBContext} to use for marshalling and unmarshalling between XML and Java VO objects.
	 * @return The JAXBContext created.
	 * @throws JAXBException If a {@link JAXBContext} is unable to be found or created.
	 */
	public static JAXBContext getJAXBContext() throws JAXBException{
		if(jaxbContext == null){
			jaxbContext = JAXBContext.newInstance(getJAXBContextPath());
		}
		return jaxbContext;
	}

	private static Collection<String> jaxbPaths = null;
	
	/**
	 * Adds package paths to used to find JAXB Value objects.
	 * @param paths A vararg of Strings representing classpaths to find JAXB annotated classes on.
	 */
	public static int addJaxbPaths(String... paths){
		int count = 0;
		if(jaxbPaths == null){
			jaxbPaths = new ArrayList<String>();
		}
		
		boolean changed = false;
		for(String path : paths){
			if(!jaxbPaths.contains(path)){
				jaxbPaths.add(path);
				changed = true;
				count++;
			}
		}
		
		// Ensure The Context Is Regenerated If Path Is Changed.
		if(changed){
			jaxbContext = null;
		}
		return count;
	}
	
	/**
	 * Resets the package paths used for JAXB processing to nothing so the path can be rebuilt.
	 */
	public static void resetJaxbPaths(){
		jaxbPaths = null;
	}
	
	/**
	 * Attempts to find all of the sun-jaxb.episode files to find the packages the VO's
	 * @return A collection of paths to any sun-jaxb.episode on the classpath.
	 */
	public static Collection<String> searchForJAXBPackages(){
		ArrayList<String> paths = new ArrayList<String>();
		try {
			Enumeration<URL> resources = Bindings.class.getClassLoader().getResources("META-INF/sun-jaxb.episode");
			while(resources.hasMoreElements()){
				// Get The XML Context From Each File
				URL url = resources.nextElement();
				
				// Find The Classes
				InputSource source = new InputSource(url.openConnection().getInputStream());
				XPath xpath = XPathFactory.newInstance().newXPath();
				XPathExpression expression = xpath.compile("//@ref");
				NodeList nodes = (NodeList)expression.evaluate(source, XPathConstants.NODESET);
				for(int i = 0; i < nodes.getLength(); i++){
					String className = nodes.item(i).getNodeValue();
					int index = className.lastIndexOf('.');
					if(index > 0){
						className = className.substring(0, index);
					}
					if(!paths.contains(className)){
						paths.add(className);
					}
				}
			}
		}
		catch (IOException e) {
			// Unable to locate resources
			e.printStackTrace();
		}
		catch (XPathExpressionException e) {
			// Unable to create XPath Expression
			e.printStackTrace();
		}
		finally{
			
		}
		return paths;
	}
	
	/**
	 * Builds and returns a string containg all of the packages that have JAXB annotated classes in it.
	 * @return A colon separated string of path URLs.
	 */
	public static String getJAXBContextPath(){
		if(jaxbPaths == null){
			jaxbPaths = searchForJAXBPackages();
		}
		final String sep = ":";
		StringBuilder path = new StringBuilder();
		for(String p : jaxbPaths){
			path.append(p).append(sep);
		}
		return path.toString();
	}
	
	/**
	 * Map of already searched Class namespaces.
	 */
	private static Map<String, String> classNamespaces = new HashMap<String, String>();
	
	private static boolean parsedEpisodes = false;
	
	/**
	 * Looks up the namespace URI to use for a specific class.
	 * @param klass
	 * @return A String containing the namespace to be used for a class in it's XML representation.
	 */
	@SuppressWarnings("rawtypes")
	public static String getNamespace(Class klass){
		String ns = classNamespaces.get(klass.getName());
		if(ns == null){
			try {
				Class<?> info = Class.forName(klass.getPackage().getName() + ".package-info");
				XmlSchema schema = info.getAnnotation(XmlSchema.class);
				ns = schema.namespace();
				classNamespaces.put(klass.getName(), ns);
			}
			catch (ClassNotFoundException e) {
				if(!parsedEpisodes){
					try{
						Enumeration<URL> resources = Bindings.class.getClassLoader().getResources("META-INF/sun-jaxb.episode");
						while(resources.hasMoreElements()){
							// Get The XML Context From Each File
							URL url = resources.nextElement();
							JAXBEpisodeSAXParser20.load(url.openStream(), classNamespaces);
						}
						ns = classNamespaces.get(klass.getName());
					}
					catch(Exception e1){}
					parsedEpisodes = true;
				}
			}
			finally{
				if(ns == null){
					ns = "http://common.pliant.code/domain";
				}
			}
		}
		return ns;
	}
	
	/**
	 * Gets the valid {@link QName} for a klass.
	 * @param klass
	 * @return A {@link QName} that represents a Class.
	 */
	@SuppressWarnings("rawtypes")
	public static QName getQName(Class klass){
		return new QName(getNamespace(klass), klass.getSimpleName());
	}
}
