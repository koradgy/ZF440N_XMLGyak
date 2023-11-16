import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DOMReadZF440N {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("XMLZF440N.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            String[] subRoots = { "Tanar", "Tanit", "Osztaly", "Tantargy", "Tankonyv", "Diak", "Tanul" };

            for (String element : subRoots) {
                NodeList nList = doc.getElementsByTagName(element);
                for (int i = 0; i < nList.getLength(); i++) {
                    Element elem = (Element) nList.item(i);
                    System.out.println("\nCurrent Element: " + elem.getNodeName());

                    // Handling IDs
                    System.out.println("ID: " + elem.getAttribute(element.toLowerCase() + "_id"));

                    NodeList childNodes = elem.getChildNodes();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        if (childNodes.item(j).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                            Element childElement = (Element) childNodes.item(j);
                            String nodeName = childElement.getNodeName();
                            String textContent = childElement.getTextContent();

                            if (nodeName.equals("lakcim") || nodeName.equals("szulido")) {
                                System.out.println(nodeName + ":");
                                printSubElements(childElement, "\t");
                            } else {
                                System.out.println("\t" + nodeName + ": " + textContent);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printSubElements(Element element, String indent) {
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                Element childElement = (Element) childNodes.item(i);
                System.out.println(indent + childElement.getNodeName() + ": " + childElement.getTextContent());
            }
        }
    }
}
