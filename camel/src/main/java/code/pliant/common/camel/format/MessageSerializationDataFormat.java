package code.pliant.common.camel.format;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * An DataFormt implementation that is automatically configured to marshal and unmarshall all objects that 
 * were generated from an XSD schema using the XJC converter.
 * 
 * @author Daniel Rugg
 */
@Component("messageSerializationDataFormat")
public class MessageSerializationDataFormat implements DataFormat {

	final String MESSAGE = "message";
	final String HEADERS = "headers";
	final String BODY = "body";
	final String TYPE = "type";
	
	/* (non-Javadoc)
	 * @see org.apache.camel.spi.DataFormat#marshal(org.apache.camel.Exchange, java.lang.Object, java.io.OutputStream)
	 */
	@Override
	// Message To String
	public void marshal(Exchange exchange, Object graph, OutputStream stream) throws Exception {
		Message in = exchange.getIn();
		String bodyObject = in.getBody(String.class);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.newDocument();
		
		Element root = document.createElement(MESSAGE);
		document.appendChild(root);
		
		Element headers = document.createElement(HEADERS);
		root.appendChild(headers);
		for(Entry<String, Object> entry : in.getHeaders().entrySet()){
			Element header = document.createElement(entry.getKey());
			if(entry.getValue() != null){
				header.setAttribute(TYPE, entry.getValue().getClass().getName());
				header.setTextContent(entry.getValue().toString());
			}
			headers.appendChild(header);
		}
		
		if(bodyObject != null){
			Element body = document.createElement(BODY);
			CDATASection cdata = document.createCDATASection(bodyObject.toString());
			body.appendChild(cdata);
			root.appendChild(body);
		}
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(stream);
		transformer.transform(source, result);
	}

	/* (non-Javadoc)
	 * @see org.apache.camel.spi.DataFormat#unmarshal(org.apache.camel.Exchange, java.io.InputStream)
	 */
	@Override
	// String To Message
	public Object unmarshal(Exchange exchange, InputStream stream) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(stream);
		
		Object ret = null;
		
		Node root = document.getFirstChild();
		NodeList children = root.getChildNodes();
		for(int i = 0; i < children.getLength(); i++){
			Node node = children.item(i);
			if(HEADERS.equals(node.getNodeName())){
				processHeaders(node, exchange);
			}
			else if(BODY.equals(node.getNodeName())){
				ret = processBody(node, exchange);
			}
		}
		return ret;
	}

	private void processHeaders(Node headers, Exchange exchange) throws Exception{
		Message in = exchange.getOut();
		NodeList children = headers.getChildNodes();
		for(int i = 0; i < children.getLength(); i++){
			Node node = children.item(i);
			in.setHeader(node.getNodeName(), node.getTextContent());
		}
	}

	private Object processBody(Node body, Exchange exchange) throws Exception{
		StringBuilder text = new StringBuilder();
		Message in = exchange.getOut();
		
		NodeList children = body.getChildNodes();
		for(int i = 0; i < children.getLength(); i++){
			text.append(children.item(i).getTextContent());
		}
		in.setBody(text.toString());
		return text.toString();
	}
}
