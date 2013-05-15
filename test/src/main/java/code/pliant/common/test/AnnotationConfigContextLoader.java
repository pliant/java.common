package code.pliant.common.test;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextLoader;

/**
 * A context loader that can be used in test classes to load Spring annotated classes 
 * off of the classpath. To utilize, add the following to the top of your TestClass, 
 * replacing the locations value with the packages from which you want classes 
 * loaded:
 * 
<pre>
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
	locations={"code.pliant.common"},
	loader=AnnotationConfigContextLoader.class)}
</pre>

 * 
 * 
 * @author Daniel Rugg
 */
public class AnnotationConfigContextLoader implements ContextLoader {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Simply returns the supplied <var>locations</var> unchanged.
     * <p/>
     *
     * @param clazz the class with which the locations are associated: used to determine how to
     *            process the supplied locations.
     * @param locations the unmodified locations to use for loading the application context; can be
     *            {@code null} or empty.
     * @return an array of application context resource locations
     * @see org.springframework.test.context.ContextLoader#processLocations(Class, String[])
     */
    public String[] processLocations(Class<?> clazz, String... locations) {
        return locations;
    }

    /**
     * Loads a new {@link ApplicationContext context} based on the supplied {@code locations},
     * configures the context, and finally returns the context in fully <em>refreshed</em> state.
     * <p/>
     *
     * Configuration locations are either fully-qualified class names or base package names. These
     * locations will be given to a {@link AnnotationConfigApplicationContext}.
     *
     * @param locations the locations to use to load the application context
     * @return a new application context
     * @throws IllegalArgumentException if any of <var>locations</var> are not valid fully-qualified
     * Class or Package names
     */
    public ApplicationContext loadContext(String... locations) {
        if (logger.isDebugEnabled()) {
            logger.debug("Creating a AnnotationConfigApplicationContext for " + Arrays.asList(locations));
        }

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(locations);

        // Have to create a child context that implements BeanDefinitionRegistry
        // to pass to registerAnnotationConfigProcessors, since
        // AnnotationConfigApplicationContext does not
        final GenericApplicationContext gac = new GenericApplicationContext(context);
        AnnotationConfigUtils.registerAnnotationConfigProcessors(gac);
        
        // copy BeanPostProcessors to the child context
        for (String bppName : context.getBeanFactory().getBeanNamesForType(BeanPostProcessor.class)) {
            gac.registerBeanDefinition(bppName, context.getBeanFactory().getBeanDefinition(bppName));
        }
        gac.refresh();
        gac.registerShutdownHook();
        return gac;
    }
}
