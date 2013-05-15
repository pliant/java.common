package code.pliant.common.xml.fop;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import code.pliant.common.xml.TransformException;
import code.pliant.common.xml.XSLTTransformer;


/**
 * Transforms XML using the Apache FOP Library.
 * 
 * @author Daniel Rugg
 */
public class FOPTransformer extends XSLTTransformer {

	/**
	 * Must be a java.util.Map.  The options are passed to the FOUserAgent
	 */
	public static final String FOP_OPTIONS = "FOP_OPTIONS";
	
	/**
	 * MIME type to transform the XML to.  If not available, defaults to MimeConstants.MIME_PDF
	 */
	public static final String FOP_MIMETYPE = "FOP_MIMETYPE";
	
	/* (non-Javadoc)
	 * @see com.saic.rpms.transformers.Transformer#transform(java.io.InputStream, java.io.OutputStream, java.util.Map)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void transform(InputStream in, OutputStream out, Map<String, Object> options) throws TransformException {
		try {
			FopFactory fopFactory = FopFactory.newInstance();
			FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
			
			Map fopOptions = (Map)options.get(FOP_OPTIONS);
			if(fopOptions != null){
				foUserAgent.getRendererOptions().putAll(fopOptions);
			}
			
			String mimeType = (String)options.get(FOP_MIMETYPE);
			if(mimeType == null || mimeType.trim().length() == 0){
				mimeType = MimeConstants.MIME_PDF;
			}
			Fop fop = fopFactory.newFop(mimeType, foUserAgent, out);
			SAXResult result = new SAXResult(fop.getDefaultHandler());
			transform(new StreamSource(in), result, options);
		}
		catch (FOPException e) {
			throw new TransformException("Failed To Create FOP Transformer", e);
		}
	}
}
