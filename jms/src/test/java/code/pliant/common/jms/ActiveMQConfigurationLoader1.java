package code.pliant.common.jms;

import jms.code.pliant.common.ActiveMQConfiguration;
import jms.code.pliant.common.NoDLQRedeliverPolicy;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * <p>
 * Load the default configuration that is provided with the JMS project.  If all projects are connecting 
 * to the same message broker/farm, it's better to use this method rather than create a new configuration 
 * for each project so that there is only one component.
 * </p>
 * <p>
 * Have defined multiple loaders to show the re-usability.
 * </p>
 * 
 * @author Daniel Rugg
 */
@Configuration
@Import({ActiveMQConfiguration.class, NoDLQRedeliverPolicy.class})
public class ActiveMQConfigurationLoader1 {

}
