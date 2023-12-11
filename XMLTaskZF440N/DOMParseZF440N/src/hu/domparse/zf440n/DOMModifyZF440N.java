package hu.domparse.zf440n;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class DOMModifyZF440N {

    public static void main(String[] args) {
        try {
            File inputFile = new File("XMLZF440N.xml");
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // Módosítások végrehajtása
            modifyTelefon(doc, "1", "NewPhoneNumber");
            modifyKor(doc, "1", 20);
            modifySzint(doc, "1", "Alap");
            modifyKiado(doc, "1", "NewPublisher");

            // Visszaírás az XML fájlba
            writeDocumentToFile(doc, "XMLZF440NModify.xml");

            // Fa struktúra kiírása a konzolra
            printNode(doc.getDocumentElement(), "");
            System.out.println("The content has been written to the output file successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void modifyTelefon(Document doc, String tanarID, String newPhoneNumber) {
        NodeList tanarList = doc.getElementsByTagName("Tanar");
        for (int i = 0; i < tanarList.getLength(); i++) {
            Node tanar = tanarList.item(i);
            Element tanarElement = (Element) tanar;

            if (tanarElement.getAttribute("tanarID").equals(tanarID)) {
                tanarElement.getElementsByTagName("telefon").item(0).setTextContent(newPhoneNumber);
                System.out.println("TanarID " + tanarID + " telefon változás: " + newPhoneNumber);
            }
        }
    }

    private static void modifyKor(Document doc, String diakID, int newAge) {
        NodeList diakList = doc.getElementsByTagName("Diak");
        for (int i = 0; i < diakList.getLength(); i++) {
            Node diak = diakList.item(i);
            Element diakElement = (Element) diak;

            if (diakElement.getAttribute("diakID").equals(diakID)) {
                diakElement.getElementsByTagName("kor").item(0).setTextContent(Integer.toString(newAge));
                System.out.println("DiakID " + diakID + " kor változás: " + newAge);
            }
        }
    }

    private static void modifySzint(Document doc, String tanarID, String newSzint) {
        NodeList tanitList = doc.getElementsByTagName("Tanit");
        for (int i = 0; i < tanitList.getLength(); i++) {
            Node tanit = tanitList.item(i);
            Element tanitElement = (Element) tanit;

            if (tanitElement.getAttribute("tanarRef").equals(tanarID)) {
                tanitElement.getElementsByTagName("szint").item(0).setTextContent(newSzint);
                System.out.println("TanarID " + tanarID + " szint változás: " + newSzint);
            }
        }
    }

    private static void modifyKiado(Document doc, String konyvID, String newPublisher) {
        NodeList konyvList = doc.getElementsByTagName("Tankonyv");
        for (int i = 0; i < konyvList.getLength(); i++) {
            Node konyv = konyvList.item(i);
            Element konyvElement = (Element) konyv;

            if (konyvElement.getAttribute("konyvID").equals(konyvID)) {
                konyvElement.getElementsByTagName("kiado").item(0).setTextContent(newPublisher);
                System.out.println("KonyvID " + konyvID + " kiado változás: " + newPublisher);
            }
        }
    }

    private static void writeDocumentToFile(Document doc, String filename) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filename));
            transformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void printNode(Node node, String indent) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            System.out.print("\n" + indent + "<" + node.getNodeName());
            if (node.hasAttributes()) {
                NamedNodeMap nodeMap = node.getAttributes();
                for (int i = 0; i < nodeMap.getLength(); i++) {
                    Node attr = nodeMap.item(i);
                    System.out.print(" " + attr.getNodeName() + "=\"" + attr.getNodeValue() + "\"");
                }
            }

            NodeList children = node.getChildNodes();
            if (children.getLength() == 1 && children.item(0).getNodeType() == Node.TEXT_NODE) {
                System.out.print(">" + children.item(0).getTextContent().trim());
                System.out.println("</" + node.getNodeName() + ">");
            } else {
                System.out.println(">");
                for (int i = 0; i < children.getLength(); i++)
                    printNode(children.item(i), indent + "    ");
                System.out.println(indent + "</" + node.getNodeName() + ">");
            }
        } else if (node.getNodeType() == Node.TEXT_NODE) {
            String content = node.getTextContent().trim();
            if (!content.isEmpty())
                System.out.print(content);
        }
    }
}
