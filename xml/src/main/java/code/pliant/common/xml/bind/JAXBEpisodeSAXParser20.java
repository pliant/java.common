package code.pliant.common.xml.bind;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Parses a JAXB sun-jaxb.episode file and adds the class=namespace mapping to a provided map, where the classname is the key
 * and the namespace is the value.
 * 
 * @author Daniel Rugg
 */
public class JAXBEpisodeSAXParser20 extends DefaultHandler {
	
	/**
	 * Performs the parsing that is needed.
	 * @param xmlStream
	 * @param mappings
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void load(InputStream xmlStream, Map<String, String> mappings) 
	throws ParserConfigurationException, SAXException, IOException{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setXIncludeAware(true);
		factory.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
		SAXParser parser = factory.newSAXParser();
		parser.parse(xmlStream, new JAXBEpisodeSAXParser20(mappings));
	}

	private Map<String, String> mappings = null;
	
	private String namespace = null;
	
	/**
	 * 
	 */
	private JAXBEpisodeSAXParser20(Map<String, String> mappings) {
		this.mappings = mappings;
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		final String eBindings = "bindings";
		final String eClass = "class";
		
		String eName = localName;
		if(localName == null){
			eName = qName;
			if(eName.indexOf(':') > 0){
				eName = eName.substring(eName.indexOf(':'));
			}
		}
		
		if(eBindings.equals(eName)){
			String tns = attributes.getValue("xmlns:tns");
			if(tns == null){
				tns = attributes.getValue("tns");
			}
			if(tns != null){
				namespace = tns;
			}
		}
		else if(eClass.equals(eName)){
			String ref = attributes.getValue("ref");
			if(namespace != null && ref != null){
				mappings.put(ref, namespace);
			}
		}
	}
}
