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
            File inputFile = new File("E:\\ZF440N_XMLGyak\\XMLTaskZF440N\\XMLZF440N.xml");

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputFile);

            // Módosítások végrehajtása
            modifyTelefon(doc, "1", "NewPhoneNumber");
            modifyKor(doc, "1", 20);
            modifySzint(doc, "1", "Alap");
            modifyKiado(doc, "1", "NewPublisher");

            // Visszaírás az XML fájlba
            writeDocumentToFile(doc, "E:\\ZF440N_XMLGyak\\XMLTaskZF440N\\XMLZF440N.xml");
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
            System.out.println("The content has been written to the output file successfully.");
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
    }
}
