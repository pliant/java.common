package code.pliant.common.camel.format;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.camel.Exchange;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;

import code.pliant.common.xml.bind.Bindings;


/**
 * An DataFormt implementation that is automatically configured to marshal and unmarshall all objects that 
 * were generated from an XSD schema using the XJC converter.
 * 
 * @author Daniel Rugg
 */
@Component("xjcDataFormat")
public class XJCDataFormat implements DataFormat {

	/* (non-Javadoc)
	 * @see org.apache.camel.spi.DataFormat#marshal(org.apache.camel.Exchange, java.lang.Object, java.io.OutputStream)
	 */
	@Override
	public void marshal(Exchange exchange, Object graph, OutputStream stream) throws Exception {
		Bindings.marshal(graph, stream);
	}

	/* (non-Javadoc)
	 * @see org.apache.camel.spi.DataFormat#unmarshal(org.apache.camel.Exchange, java.io.InputStream)
	 */
	@Override
	public Object unmarshal(Exchange exchange, InputStream stream) throws Exception {
		return Bindings.unmarshal(stream);
	}

}
