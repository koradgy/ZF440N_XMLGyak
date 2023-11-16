import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class DOMWriteZF440N {

    public static void main(String[] args) {
        try {
            File inputFile = new File("XMLZF440n.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // Modify the document as needed
            modifyDocument(doc);

            printNode(doc.getDocumentElement(), "");
            writeDocumentToFile(doc, "XMLZF440n1.xml");

            System.out.println("The content has been modified and written to the output file successfully.");
        } catch (SAXException | IOException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private static void modifyDocument(Document doc) {

        Element tanarElement = (Element) doc.getElementsByTagName("Tanar").item(0);
        Element newElement = doc.createElement("NewElement");
        newElement.appendChild(doc.createTextNode("New Element Content"));
        tanarElement.appendChild(newElement);
    }

    private static void printNode(Node node, String indent) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            System.out.print(indent + node.getNodeName());
            if (node.hasAttributes()) {
                NamedNodeMap nodeMap = node.getAttributes();
                for (int i = 0; i < nodeMap.getLength(); i++) {
                    Node attr = nodeMap.item(i);
                    System.out.print(attr.getNodeName() + "=" + attr.getNodeValue() + (i < nodeMap.getLength() - 1 ? ", " : ""));
                }
            }

            System.out.println(" start");

            NodeList children = node.getChildNodes();
            String childIndent = indent + "    ";
            for (int i = 0; i < children.getLength(); i++)
                printNode(children.item(i), childIndent);

            System.out.println(indent + node.getNodeName() + " end");
        } else if (node.getNodeType() == Node.TEXT_NODE) {
            String content = node.getTextContent().trim();
            if (!content.isEmpty())
                System.out.println(indent + content);
        }
    }

    private static void writeDocumentToFile(Document doc, String filename) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filename));
        transformer.transform(source, result);
    }
}
