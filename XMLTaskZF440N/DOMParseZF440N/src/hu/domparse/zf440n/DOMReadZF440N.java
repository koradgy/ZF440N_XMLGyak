package hu.domparse.zf440n;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMReadZF440N {

    private static final String FILE_NAME = "XMLZF440N.xml";

    public static void main(String[] args) {
        try {
            Document document = parseXml(FILE_NAME);

            document.getDocumentElement().normalize();
            System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            System.out.println("<Iskola_ZF440N xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"XMLSchemaZF440N.xsd\">\n");

            readTanar(document);
            printEmptyLine();

            readTanit(document);
            printEmptyLine();

            readOsztaly(document);
            printEmptyLine();

            readTantargy(document);
            printEmptyLine();

            readTankonyv(document);
            printEmptyLine();

            readDiak(document);
            printEmptyLine();

            readTanul(document);
            printEmptyLine();

            System.out.println("\n</Iskoa_ZF440N>");
        } catch (ParserConfigurationException | IOException | SAXException e) {
            handleException(e);
        }
    }

private static void readTanar(Document document) {
    NodeList tanarList = document.getElementsByTagName("Tanar");
    for (int temp = 0; temp < tanarList.getLength(); temp++) {
        Node node = tanarList.item(temp);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) node;
            // Extract the necessary information from the new XML structure
            String vnev = eElement.getElementsByTagName("vnev").item(0).getTextContent();
            String knev = eElement.getElementsByTagName("knev").item(0).getTextContent();
            String irsz = eElement.getElementsByTagName("irsz").item(0).getTextContent();
            String varos = eElement.getElementsByTagName("varos").item(0).getTextContent();
            String utca = eElement.getElementsByTagName("utca").item(0).getTextContent();
            String hsz = eElement.getElementsByTagName("hsz").item(0).getTextContent();
            String ev = eElement.getElementsByTagName("ev").item(0).getTextContent();
            String ho = eElement.getElementsByTagName("ho").item(0).getTextContent();
            String nap = eElement.getElementsByTagName("nap").item(0).getTextContent();
            
            // Extract telephone numbers
            NodeList telefonList = eElement.getElementsByTagName("telefon");
            List<String> telefonok = new ArrayList<>();
            for (int i = 0; i < telefonList.getLength(); i++) {
                telefonok.add(telefonList.item(i).getTextContent());
            }

            // Print the extracted information
            System.out.println("    <Tanar>");
            printElement("nev", vnev + " " + knev);
            System.out.println("        <lakcim>");
            printElement("irsz", irsz);
            printElement("varos", varos);
            printElement("utca", utca);
            printElement("hsz", hsz);
            System.out.println("        </lakcim>");
            System.out.println("        <szulido>");
            printElement("ev", ev);
            printElement("ho", ho);
            printElement("nap", nap);
            System.out.println("        </szulido>");
            
            // Print telephone numbers
            for (String telefon : telefonok) {
                printElement("telefon", telefon);
            }

            System.out.println("    </Tanar>");
        }
    }
}


    private static void readTanit(Document document) {
        NodeList tanitList = document.getElementsByTagName("Tanit");
        for (int temp = 0; temp < tanitList.getLength(); temp++) {
            Node node = tanitList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                String szint = eElement.getElementsByTagName("szint").item(0).getTextContent();
                String miota = eElement.getElementsByTagName("miota").item(0).getTextContent();

                System.out.println("    <Tanit>");
                printElement("szint", szint);
                printElement("miota", miota);
                System.out.println("    </Tanit>");
            }
        }
    }

    private static void readOsztaly(Document document) {
        NodeList osztalyList = document.getElementsByTagName("Osztaly");
        for (int temp = 0; temp < osztalyList.getLength(); temp++) {
            Node node = osztalyList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                String letszam = eElement.getElementsByTagName("letszam").item(0).getTextContent();
                String tagozat = eElement.getElementsByTagName("tagozat").item(0).getTextContent();

                System.out.println("    <Osztaly>");
                printElement("letszam", letszam);
                printElement("tagozat", tagozat);
                System.out.println("    </Osztaly>");
            }
        }
    }

    private static void readTantargy(Document document) {
        NodeList tantargyList = document.getElementsByTagName("Tantargy");
        for (int temp = 0; temp < tantargyList.getLength(); temp++) {
            Node node = tantargyList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                String tantargyNev = eElement.getElementsByTagName("nev").item(0).getTextContent();

                System.out.println("    <Tantargy>");
                printElement("nev", tantargyNev);
                System.out.println("    </Tantargy>");
            }
        }
    }

    private static void readTankonyv(Document document) {
        NodeList tankonyvList = document.getElementsByTagName("Tankonyv");
        for (int temp = 0; temp < tankonyvList.getLength(); temp++) {
            Node node = tankonyvList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                String cim = eElement.getElementsByTagName("cim").item(0).getTextContent();
                String iro = eElement.getElementsByTagName("iro").item(0).getTextContent();
                String kiado = eElement.getElementsByTagName("kiado").item(0).getTextContent();
                String kiadido = eElement.getElementsByTagName("kiadido").item(0).getTextContent();

                System.out.println("    <Tankonyv>");
                printElement("cim", cim);
                printElement("iro", iro);
                printElement("kiado", kiado);
                printElement("kiadido", kiadido);
                System.out.println("    </Tankonyv>");
            }
        }
    }

    private static void readDiak(Document document) {
        NodeList diakList = document.getElementsByTagName("Diak");
        for (int temp = 0; temp < diakList.getLength(); temp++) {
            Node node = diakList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                String vnev = eElement.getElementsByTagName("vnev").item(0).getTextContent();
                String knev = eElement.getElementsByTagName("knev").item(0).getTextContent();
                String irsz = eElement.getElementsByTagName("irsz").item(0).getTextContent();
                String varos = eElement.getElementsByTagName("varos").item(0).getTextContent();
                String utca = eElement.getElementsByTagName("utca").item(0).getTextContent();
                String hsz = eElement.getElementsByTagName("hsz").item(0).getTextContent();
                String ev = eElement.getElementsByTagName("ev").item(0).getTextContent();
                String ho = eElement.getElementsByTagName("ho").item(0).getTextContent();
                String nap = eElement.getElementsByTagName("nap").item(0).getTextContent();
                String anyjaneve = eElement.getElementsByTagName("anyjaneve").item(0).getTextContent();
                String kor = eElement.getElementsByTagName("kor").item(0).getTextContent();

                System.out.println("    <Diak>");
                printElement("nev", vnev + " " + knev);
                System.out.println("        <lakcim>");
                printElement("irsz", irsz);
                printElement("varos", varos);
                printElement("utca", utca);
                printElement("hsz", hsz);
                System.out.println("        </lakcim>");
                System.out.println("        <szulido>");
                printElement("ev", ev);
                printElement("ho", ho);
                printElement("nap", nap);
                System.out.println("        </szulido>");
                printElement("anyjaneve", anyjaneve);
                printElement("kor", kor);
                System.out.println("    </Diak>");
            }
        }
    }

    private static void readTanul(Document document) {
        NodeList tanulList = document.getElementsByTagName("Tanul");
        for (int temp = 0; temp < tanulList.getLength(); temp++) {
            Node node = tanulList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                String erdemjegy = eElement.getElementsByTagName("erdemjegy").item(0).getTextContent();
                String datum = eElement.getElementsByTagName("datum").item(0).getTextContent();

                System.out.println("    <Tanul>");
                printElement("erdemjegy", erdemjegy);
                printElement("datum", datum);
                System.out.println("    </Tanul>");
            }
        }
    }

    private static void printElement(String name, String value) {
        System.out.println("            <" + name + ">" + value + "</" + name + ">");
    }

    private static void printEmptyLine() {
        System.out.println();
    }

    private static Document parseXml(String fileName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new File(fileName));
    }

    private static void handleException(Exception e) {
        // Handle the exception (e.g., log it)
        e.printStackTrace();
    }
}
