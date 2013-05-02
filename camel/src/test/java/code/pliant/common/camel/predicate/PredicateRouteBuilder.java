package code.pliant.common.camel.predicate;

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
public class PredicateRouteBuilder extends RouteBuilder {
	
	@Autowired
	PredicateTestService service;
	
	@Autowired
	IsRecoverable predicate;
	
	/**
	 * The starting endpoint of the nonrecoverable route.
	 */
	public static final String DIRECT_START_NONRECOVER = "direct:start.predicate.nonrecover";
	
	/**
	 * The starting endpoint of the recoverable route.
	 */
	public static final String DIRECT_START_RECOVER = "direct:start.predicate.recover";
	
	
	/**
	 * 
	 */
	public PredicateRouteBuilder() {
		super();
	}

	/**
	 * @param context
	 */
	public PredicateRouteBuilder(CamelContext context) {
		super(context);
	}

	/* (non-Javadoc)
	 * @see org.apache.camel.builder.RouteBuilder#configure()
	 */
	@Override
	public void configure() throws Exception {
		from(DIRECT_START_RECOVER)
			.doTry()
				.bean(service, "throwRecoverable")
			.doCatch(Exception.class)
				.choice()
					.when(predicate)
						.bean(service, "doRecovers")
					.otherwise()
						.bean(service, "doNonRecovers")
				.end()
			.end();
		

		from(DIRECT_START_NONRECOVER)
			.doTry()
				.bean(service, "throwNonRecoverable")
			.doCatch(Exception.class)
				.choice()
					.when(predicate)
						.bean(service, "doRecovers")
					.otherwise()
						.bean(service, "doNonRecovers")
				.end()
			.end();
	}
}
