package code.pliant.common.camel;

import org.springframework.stereotype.Component;


/**
 * @author Daniel Rugg
 */
@Component
public class JAXBBodyTest {

	public static Object body = null;
	
	public void setBody(Object body){
		JAXBBodyTest.body = body;
	}
	
	public Object getBody(){
		return JAXBBodyTest.body;
	}
}
