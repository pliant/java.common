package code.pliant.common.camel.format;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Creates two basic routes that use MQ as a go between.
 * 
 * @author Daniel Rugg
 */
@Component
public class FormatRouteBuilder extends RouteBuilder {
	
	@Autowired
	MessageSerializationDataFormat format;
	
	/**
	 * The starting endpoint to serialize a message.
	 */
	public static final String DIRECT_START_MARSHAL = "direct:start.format.marshal";
	
	/**
	 * The starting endpoint to serialize a message.
	 */
	public static final String DIRECT_START_UNMARSHAL = "direct:start.format.unmarshal";

	/**
	 * The mock endpoint to serialize a message.
	 */
	public static final String MOCK_END_MARSHAL = "mock:end.format.marshal";
	
	/**
	 * The mock endpoint to serialize a message.
	 */
	public static final String MOCK_END_UNMARSHAL = "mock:end.format.unmarshal";
	
	/**
	 * 
	 */
	public FormatRouteBuilder() {
		super();
	}

	/**
	 * @param context
	 */
	public FormatRouteBuilder(CamelContext context) {
		super(context);
	}

	/* (non-Javadoc)
	 * @see org.apache.camel.builder.RouteBuilder#configure()
	 */
	@Override
	public void configure() throws Exception {
		from(DIRECT_START_MARSHAL).marshal(format).to(MOCK_END_MARSHAL);
		from(DIRECT_START_UNMARSHAL).unmarshal(format).to(MOCK_END_UNMARSHAL);
	}
}
