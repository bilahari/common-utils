package personnel.bilahari.common.utils;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author bilahari.th
 * 
 */
public class DomXmlUtils {

	private final static Logger logger = LoggerFactory.getLogger(DomXmlUtils.class);

    public static void copyElementToAnotherDocElement(
	    Element elementToBeCopied, Document destDoc, Element destElement) {
	Node copiedNode = destDoc.importNode(elementToBeCopied, true);
	destDoc.getDocumentElement().appendChild(copiedNode);
	destElement.appendChild(copiedNode);
    }

    /*
     * // This method is to be changed to a good logic public static void
     * copyElementToAnotherDocElement2(Element elementToBeCopied,Document
     * destDoc,Element destElement){ Node copiedNode =
     * destDoc.importNode(elementToBeCopied, true);
     * destDoc.getDocumentElement().appendChild(copiedNode); Element mElement =
     * (Element)destDoc.getElementsByTagName("match").item(0); Element rElement=
     * (Element)mElement.getElementsByTagName("result").item(0);
     * rElement.appendChild(copiedNode); }
     */
    public static void setElementTextContent(Document doc, Element element,
	    String text) {
	element.appendChild(doc.createTextNode(text));
    }

    public static String getLeafElementTextContent(Element element) {
	String nodeVal = null;
	if (element == null)
	    return null;
	try {
	    NodeList childNodes = element.getChildNodes();
	    for (int i = 0; i < childNodes.getLength(); i++) {
		if (childNodes.item(i) != null) {
		    nodeVal = (childNodes.item(i)).getNodeValue();
		    nodeVal = nodeVal.trim();
		}
	    }
	} catch (NullPointerException npe) {
	    logger.error("getElementTextForLeafNode",
		    "The element : " + element.getNodeName()
			    + " is not a leaf level node");
	}
	return nodeVal;
    }

    public static String serialize(Document node) {
	XMLSerializer serialiser = new XMLSerializer();
	StringWriter writer = new StringWriter();
	serialiser.setOutputCharStream(writer);

	OutputFormat format = new OutputFormat();
	format.setOmitXMLDeclaration(true);
	format.setIndenting(true);
	serialiser.setOutputFormat(format);

	try {
	    serialiser.serialize((Element) node.getFirstChild());
	} catch (IOException e) {
	    e.printStackTrace();
	    return null;
	}
	return writer.toString();
    }
}
