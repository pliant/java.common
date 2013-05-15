package code.pliant.common.xml;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


/**
 * Transforms XML using a stylesheet.
 * 
 * @author Daniel Rugg
 */
public class XSLTTransformer implements Transformer {

	/**
	 * Used to store the contents of the XSLT stylesheet.  Must be a javax.xml.transform.Source instance.
	 */
	public static final String XSLT_SOURCE = "XSLT_SOURCE";
	
	/**
	 * Flag used to indicate that no stylesheet is used in the tranformation.  This is useful if using SAXResult/Handler 
	 * combinations.  If no stylesheet is provided and this flag is not on the options, an exception is thrown.  The value 
	 * is for this key in the options is not taken into account.
	 */
	public static final String XSLT_NONE = "XSLT_NONE";
	
	/**
	 * Map of parameters that can be set on the transformer.  Must be a java.util.Map<String, Object>
	 */
	public static final String XSLT_PARAMETERS = "XSLT_PARAMETERS";
	
	/* (non-Javadoc)
	 * @see com.saic.rpms.transformers.Transformer#transform(java.io.InputStream, java.io.OutputStream, java.util.Map)
	 */
	@Override
	public void transform(InputStream in, OutputStream out, Map<String, Object> options) throws TransformException {
		transform(new StreamSource(in), getXSLT(options), new StreamResult(out), options);
	}
	
	/**
	 * Gets the XSLT Source.  Wrapped in a method so it can be expanded upon later.
	 * @param options
	 * @return
	 * @throws TransformException
	 */
	protected Source getXSLT(Map<String, Object> options) throws TransformException {
		Source xslt = (Source)options.get(XSLT_SOURCE);
		if(xslt != null){
			return xslt;
		}
		if(options.containsKey(XSLT_NONE)){
			return null;
		}
		throw new TransformException("No XSLT Was Provided To Transform The XML With.");
	}
	
	/**
	 * Performs the transformation from a {@link Source} to a {@link Result}.
	 * @param xml The {@link Source} to transform.
	 * @param out The {@link Result} to send the transformed content to.
	 * @param options Transformation options.
	 * @throws TransformException If the transformation fails.
	 */
	protected void transform(Source xml, Result out, Map<String, Object> options) throws TransformException {
		transform(xml, getXSLT(options), out, options);
	}
	
	/**
	 * Performs a transformation on a {@link Source} xml document using an XML Style Sheet, sending that transformation 
	 * to the provided {@link Result}.
	 * @param xml The XML to transform.
	 * @param xslt The XML Style Sheet to use in the transformation.
	 * @param out The {@link Result} to send the transformed content to.
	 * @param options Transformation options.
	 * @throws TransformException If the transformation fails.
	 */
	@SuppressWarnings("unchecked")
	protected void transform(Source xml, Source xslt, Result out, Map<String, Object> options) throws TransformException {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			javax.xml.transform.Transformer transformer = null;
			if(xslt != null){
				transformer = transformerFactory.newTransformer(xslt);
			}
			else{
				transformer = transformerFactory.newTransformer();
			}
			
			// Add Parameters.
			if(options.containsKey(XSLT_PARAMETERS)){
				Map<String, Object> parms = (Map<String, Object>)options.get(XSLT_PARAMETERS);
				for(String key : parms.keySet()){
					Object value = parms.get(key);
					transformer.setParameter(key, value);
				}
			}
			transformer.transform(xml, out);
		}
		catch (TransformerConfigurationException e) {
			throw new TransformException("Failed To Create XSKT Transformer", e);
		}
		catch (TransformerException e) {
			throw new TransformException("Failed To Transform XML", e);
		}
	}
}
