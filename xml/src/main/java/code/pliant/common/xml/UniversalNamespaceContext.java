package code.pliant.common.xml;

import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;

import org.w3c.dom.Document;


/**
 * Generic NamespaceContext to use with XPath processing that gets the prefixes and namespaces 
 * from the source document.
 * 
 * @author Daniel Rugg
 */
public class UniversalNamespaceContext implements NamespaceContext {

	private Document sourceDocument;
	
	/**
	 * Constructor that requires the document that is to be examined.
	 * @param sourceDocument
	 */
	public UniversalNamespaceContext(Document sourceDocument) {
		this.sourceDocument = sourceDocument;
	}

	/* (non-Javadoc)
	 * @see javax.xml.namespace.NamespaceContext#getNamespaceURI(java.lang.String)
	 */
	@Override
	public String getNamespaceURI(String prefix) {
        if (prefix.equals(XMLConstants.DEFAULT_NS_PREFIX)) {
            return sourceDocument.lookupNamespaceURI(null);
        }
        else {
            return sourceDocument.lookupNamespaceURI(prefix);
        }
	}

	/* (non-Javadoc)
	 * @see javax.xml.namespace.NamespaceContext#getPrefix(java.lang.String)
	 */
	@Override
	public String getPrefix(String namespaceURI) {
        return sourceDocument.lookupPrefix(namespaceURI);
	}

	/* (non-Javadoc)
	 * @see javax.xml.namespace.NamespaceContext#getPrefixes(java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Iterator getPrefixes(String namespaceURI) {
		// Not Implemented
		return null;
	}
}
