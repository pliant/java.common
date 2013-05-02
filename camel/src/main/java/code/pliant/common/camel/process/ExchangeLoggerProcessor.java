package code.pliant.common.camel.process;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import code.pliant.common.core.Strings;
import code.pliant.common.domain.ValueObject;


/**
 * Logs information about the exchange as if an error had occurred.
 * 
 * @author Daniel Rugg
 */
@Component
public class ExchangeLoggerProcessor implements Processor {
	
	private static Logger logger = LoggerFactory.getLogger(ExchangeLoggerProcessor.class);

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		String routeId = exchange.getFromRouteId();
		if(!Strings.isValid(routeId)){
			routeId = "Unknown RouteID";
		}
		if(exchange.getException() != null){
			logger.error(routeId + ": Exception", exchange.getException());
		}
		else{
			logger.error(routeId + ": No Exception");
		}
		
		// Log Body
		Object body = exchange.getIn().getBody();
		logger.error(routeId + ": Message In Body\r\n" + getValue(body));
		
		// Log Properties
		StringBuilder out = new StringBuilder();
		out.append(routeId).append(SEP).append("Exchange Properties").append(NL);
		Map<String, Object> properties = exchange.getProperties();
		if(properties != null){
			for(Map.Entry<String, Object> entry : properties.entrySet()){
				out.append(entry.getKey()).append(SEP).append(getValue(entry.getValue())).append(NL);
			}
		}
		logger.error(out.toString());
		
		// Log Headers
		out = new StringBuilder();
		out.append(routeId).append(SEP).append("Message Headers").append(NL);
		Map<String, Object> headers = exchange.getIn().getHeaders();
		if(headers != null){
			for(Map.Entry<String, Object> entry : headers.entrySet()){
				out.append(entry.getKey()).append(SEP).append(getValue(entry.getValue())).append(NL);
			}
		}
		logger.error(out.toString());
	}
	
	private static final String NL = "\r\n";
	private static final String SEP = ": ";
	
	private String getValue(Object body){
		StringBuilder out = new StringBuilder();
		if(body == null){
			out.append("[NULL]");
		}
		else if(body instanceof String){
			out.append(body.toString());
		}
		else if(body instanceof ValueObject){
			out.append(body.toString());
		}
		else if(body instanceof Exception){
			Exception e = (Exception)body;
			out.append("{\r\n");
			out.append("    Class: ").append(e.getClass().getName()).append(",\r\n");
			out.append("    Message: ").append(e.getMessage()).append("\r\n");
			out.append("    StackTrace: ");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			e.printStackTrace(new PrintWriter(baos));
			out.append(baos.toString());
			out.append("\r\n");
			out.append("}\r\n");
		}
		else{
			out.append("{\r\n");
			out.append("    Class: ").append(body.getClass().getName()).append(",\r\n");
			out.append("    toString: ").append(body.toString()).append("\r\n");
			out.append("}\r\n");
		}
		return out.toString();
	}
}
