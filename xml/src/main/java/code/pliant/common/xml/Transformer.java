package code.pliant.common.xml;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;


/**
 * Generic interface for transforming content between formats.
 * 
 * @author Daniel Rugg
 */
public interface Transformer {

	/**
	 * Performs transformation of the InputStream to an OutputStream according to the implementation.
	 * @param in The InputStream to transform.
	 * @param out The OutputStream to sent the transformed content to.
	 * @param options Map for implementation specific options.
	 * @throws TransformException If the transformation fails.
	 */
	void transform(InputStream in, OutputStream out, Map<String, Object> options) throws TransformException;
}
