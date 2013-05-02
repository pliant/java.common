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
	 * 
	 * @param in
	 * @param out
	 * @param options
	 * @throws TransformException
	 */
	void transform(InputStream in, OutputStream out, Map<String, Object> options) throws TransformException;
}
