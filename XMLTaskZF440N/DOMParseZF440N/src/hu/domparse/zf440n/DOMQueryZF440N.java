package hu.domparse.zf440n;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class DOMQueryZF440N {

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse("XMLZF440N.xml");

            // Példa: Miskolci tanárok kilistázása
            queryBPTanarok(document);
            queryDiakokTantargybol(document, "Fizika");
            queryTanarokAdataiy(document);
            queryOsztalyokLetszamTantargyankent(document);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Budapesti tanárok kilistázása
    private static void queryBPTanarok(Document document) {
        System.out.println("=== Budapesti tanárok ===");

        NodeList tanarList = document.getElementsByTagName("Tanar");

        for (int i = 0; i < tanarList.getLength(); i++) {
            Node tanarNode = tanarList.item(i);

            if (tanarNode.getNodeType() == Node.ELEMENT_NODE) {
                Element tanarElement = (Element) tanarNode;

                String varos = tanarElement.getElementsByTagName("varos").item(0).getTextContent();

                if (varos.equalsIgnoreCase("Budapest")) {
                    // Ha a tanár Miskolcon lakik, kiírjuk az adatait
                    String nev = tanarElement.getElementsByTagName("nev").item(0).getTextContent();
                    String szuletesiEv = tanarElement.getElementsByTagName("ev").item(0).getTextContent();

                    System.out.println("  <Tanar>");
                    System.out.println("    <Nev>" + nev + "</Nev>");
                    System.out.println("    <SzuletesiEv>" + szuletesiEv + "</SzuletesiEv>");
                    System.out.println("  </Tanar>");
                    System.out.println("\n");
                }
            }
        }

        System.out.println("\n");
    }

    private static void queryDiakokTantargybol(Document document, String tantargyNev) {
        System.out.println("=== Diákok a(z) " + tantargyNev + " tantárgyból ===");
    
        NodeList tanulList = document.getElementsByTagName("Tanul");
        NodeList tantargyList = document.getElementsByTagName("Tantargy");
    
        for (int i = 0; i < tantargyList.getLength(); i++) {
            Node tantargyNode = tantargyList.item(i);
            Element tantargyElement = (Element) tantargyNode;
    
            String nev = tantargyElement.getElementsByTagName("nev").item(0).getTextContent();
    
            if (nev.equalsIgnoreCase(tantargyNev)) {
                // Az adott tantárgyhoz tartozó diákok kilistázása
                for (int j = 0; j < tanulList.getLength(); j++) {
                    Node tanulNode = tanulList.item(j);
    
                    if (tanulNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element tanulElement = (Element) tanulNode;
    
                        String erdemjegy = tanulElement.getElementsByTagName("erdemjegy").item(0).getTextContent();
                        String datum = tanulElement.getElementsByTagName("datum").item(0).getTextContent();
                        String diakNev = getDiakNevById(document, tanulElement.getAttribute("diakRef"));
    
                        System.out.println("  <Diak>");
                        System.out.println("    <Nev>" + diakNev + "</Nev>");
                        System.out.println("    <Erdemjegy>" + erdemjegy + "</Erdemjegy>");
                        System.out.println("    <Datum>" + datum + "</Datum>");
                        System.out.println("  </Diak>");
                        System.out.println("\n");
                    }
                }
            }
        }
    
        System.out.println("\n");
    }
    
    private static String getDiakNevById(Document document, String diakId) {
        NodeList diakList = document.getElementsByTagName("Diak");
    
        for (int i = 0; i < diakList.getLength(); i++) {
            Node diakNode = diakList.item(i);
    
            if (diakNode.getNodeType() == Node.ELEMENT_NODE) {
                Element diakElement = (Element) diakNode;
    
                String currentDiakId = diakElement.getAttribute("diakID");
    
                if (currentDiakId.equals(diakId)) {
                    // Az adott diák nevének összeállítása
                    String vnev = diakElement.getElementsByTagName("vnev").item(0).getTextContent();
                    String knev = diakElement.getElementsByTagName("knev").item(0).getTextContent();
    
                    return vnev + " " + knev;
                }
            }
        }
    
        return "";
    }

    private static void queryTanarokAdataiy(Document document) {
        System.out.println("=== Tanárok születési évének és lakhelyének megjelenítése ===");
    
        NodeList tanarList = document.getElementsByTagName("Tanar");
    
        for (int i = 0; i < tanarList.getLength(); i++) {
            Node tanarNode = tanarList.item(i);
    
            if (tanarNode.getNodeType() == Node.ELEMENT_NODE) {
                Element tanarElement = (Element) tanarNode;
    
                String nev = tanarElement.getElementsByTagName("nev").item(0).getTextContent();
                String szuletesiEv = tanarElement.getElementsByTagName("ev").item(0).getTextContent();
                String varos = tanarElement.getElementsByTagName("varos").item(0).getTextContent();
    
                System.out.println("  <Tanar>");
                System.out.println("    <Nev>" + nev + "</Nev>");
                System.out.println("    <SzuletesiEv>" + szuletesiEv + "</SzuletesiEv>");
                System.out.println("    <Lakohely>" + varos + "</Lakohely>");
                System.out.println("  </Tanar>");
                System.out.println("\n");
            }
        }
    
        System.out.println("\n");
    }
    
    private static void queryOsztalyokLetszamTantargyankent(Document document) {
        System.out.println("=== Osztályok létszámainak összegzése tantárgyanként ===");
    
        NodeList osztalyList = document.getElementsByTagName("Osztaly");
        NodeList tanuloList = document.getElementsByTagName("Tanulo");
    
        for (int i = 0; i < osztalyList.getLength(); i++) {
            Node osztalyNode = osztalyList.item(i);
            Element osztalyElement = (Element) osztalyNode;
    
            String tantargy = osztalyElement.getElementsByTagName("tagozat").item(0).getTextContent();
            int osztalyLetszam = Integer.parseInt(osztalyElement.getElementsByTagName("letszam").item(0).getTextContent());
            int tantargyLetszam = 0;
    
            for (int j = 0; j < tanuloList.getLength(); j++) {
                Node tanuloNode = tanuloList.item(j);
    
                if (tanuloNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element tanuloElement = (Element) tanuloNode;
    
                    String osztalyRef = tanuloElement.getAttribute("osztalyRef");
    
                    if (osztalyRef.equals(osztalyElement.getAttribute("osztalyID"))) {
                        tantargyLetszam++;
                    }
                }
            }
    
            System.out.println("  <Osztaly>");
            System.out.println("    <Tantargy>" + tantargy + "</Tantargy>");
            System.out.println("    <Letszam>" + tantargyLetszam + "</Letszam>");
            System.out.println("  </Osztaly>");
            System.out.println("\n");
        }
    
        System.out.println("\n");
    }
}
