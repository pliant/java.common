package code.pliant.common.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

/**
 * 
 * @author Daniel Rugg
 */
public class TestMessage {

	/**
	 * Test method for {@link Message#Message(java.lang.String)}.
	 */
	@Test
	public void testMessage() {
		Message message = new Message();
		assertEquals("", message.toString());
		
		String start = "Start";
		message = new Message(start);
		assertEquals(start, message.toString());

		message.add('?');
		assertTrue(message.toString().endsWith("?"));
		
		message.add("Adding");
		assertTrue(message.toString().endsWith("Adding"));

		message.add(342);
		assertTrue(message.toString().endsWith("342"));

		message.add(null);
		assertTrue(message.toString().endsWith(Message.NULL));
		
		message.add("name", "test");
		assertTrue(message.toString().indexOf("name=test") > 0);
		
		message.add("name", (Object)null);
		assertTrue(message.toString().indexOf("name=" + Message.NULL) > 0);
		
		message.add("numbers", new Integer(1), new Integer(2), new Integer(3));
		assertTrue(message.toString().indexOf("numbers=[1,2,3") > 0);
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(5);
		list.add(6);
		list.add(7);
		message.add("list", list);
		assertTrue(message.toString().indexOf("list=[5,6,7") > 0);
		
		message.add("TABS").indent().indent().addTabs();
		assertTrue(message.toString().endsWith("TABS" + Message.TAB + Message.TAB));
		
		message.outdent().add("TABS").addTabs();
		assertTrue(message.toString().endsWith("TABS" + Message.TAB));
	}

}
