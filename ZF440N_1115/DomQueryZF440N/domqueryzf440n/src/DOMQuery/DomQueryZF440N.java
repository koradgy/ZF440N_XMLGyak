package DOMQuery;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomQueryZF440N {
    public static void main(String[] args) throws Exception {

        try {
            File xmlFile = new File("kurzusfelvetel.xml");
            File bOutput = new File("elsoPeldany.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            DOMQuery query = new DOMQuery();
            List<String> courseNames = query.getCourseNames(document);
            System.out.println("a) Kurzusok: " + courseNames + "\n");

            String firstInstance = query.getFirst(document);
            System.out.println("b) Az első elem:");
            System.out.println(firstInstance + "\n");
            FileWriter writer = new FileWriter(bOutput);
            writer.write("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n");
            writer.write(firstInstance);
            writer.close();

            System.out.println("c) Oktatók: " + query.getTeachers(document) + "\n");
            System.out.println("d) Kurzusok időpontjai: " + query.getTimes(document));
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }

    }

    private static class DOMQuery {

        public List<String> getCourseNames(Document document) {
            List<String> courseNames = new ArrayList<>();
            Element root = document.getDocumentElement();
            NodeList courses = root.getElementsByTagName("kurzus");
            for (int i = 0; i < courses.getLength(); i++) {
                Node course = courses.item(i);
                NodeList names = ((Element) course).getElementsByTagName("kurzusnev");
                for (int j = 0; j < names.getLength(); j++) {
                    courseNames.add(names.item(j).getTextContent());
                }
            }
            return courseNames;
        }

        public String getFirst(Document document) {
            Element root = document.getDocumentElement();
            NodeList courses = root.getElementsByTagName("kurzus");
            if (courses.getLength() > 0) {
                Node firstCourse = courses.item(0);
                return writeCourse(firstCourse);
            } else {
                return "No courses found.";
            }
        }

        public List<String> getTeachers(Document document) {
            List<String> teacherNames = new ArrayList<>();
            Element root = document.getDocumentElement();
            NodeList courses = root.getElementsByTagName("kurzus");
            for (int i = 0; i < courses.getLength(); i++) {
                Node course = courses.item(i);
                NodeList names = ((Element) course).getElementsByTagName("oktato");
                for (int j = 0; j < names.getLength(); j++) {
                    teacherNames.add(names.item(j).getTextContent());
                }
            }
            return teacherNames;
        }

        public List<String> getTimes(Document document) {
            List<String> times = new ArrayList<>();
            Element root = document.getDocumentElement();
            NodeList courses = root.getElementsByTagName("kurzus");
            for (int i = 0; i < courses.getLength(); i++) {
                Node course = courses.item(i);
                Node time = ((Element) course).getElementsByTagName("idopont").item(0);
                System.out.println(time);
                String[] timeParts = getTimeParts(time);
                String day = timeParts[0];
                String from = timeParts[1];
                String to = timeParts[2];

                times.add(day + " " + from + "-" + to);
            }
            return times;
        }

        private static String[] getTimeParts(Node time) {
            String[] timeParts = new String[3];
        
            if (time != null) {
                String timeString = time.getTextContent().trim();
                String[] splitTime = timeString.split("\\s+");
        
                if (splitTime.length == 3) {
                    timeParts[0] = splitTime[0]; // Nap
                    timeParts[1] = splitTime[1].split(":")[0]; // Tól (óra)
                    timeParts[2] = splitTime[2].split(":")[0]; // Ig (óra)
                }
            }
        
            return timeParts;
        }
        

        private String writeCourse(Node courseIn) {
            String output = "";
            String indentStr = "   ";
            int indent = 0;
            Element course = (Element) courseIn;
            output += (indentStr.repeat(indent) + "<kurzus>");
            NamedNodeMap attributes = course.getAttributes();
            output += printAttributes(attributes);

            NodeList subjectNodes = course.getElementsByTagName("kurzusnev");
            if (subjectNodes.getLength() > 0) {
                Element subject = (Element) subjectNodes.item(0);
                indent++;
                output += (indentStr.repeat(indent) + "<kurzusnev>" + subject.getTextContent() + "</kurzusnev>\n");
            } else {
                indent++;
                output += (indentStr.repeat(indent) + "<kurzusnev>Unknown</kurzusnev>\n");
            }

            Node time = ((Element) course).getElementsByTagName("idopont").item(0);
            output += printTime(time, indent, indentStr);

            NodeList placeNodes = course.getElementsByTagName("hely");
            if (placeNodes.getLength() > 0) {
                Element place = (Element) placeNodes.item(0);
                output += (indentStr.repeat(indent) + "<hely>" + place.getTextContent() + "</hely>\n");
            } else {
                output += (indentStr.repeat(indent) + "<hely>Unknown</hely>\n");
            }

            NodeList teacherNodes = course.getElementsByTagName("oktato");
            if (teacherNodes.getLength() > 0) {
                Element teacher = (Element) teacherNodes.item(0);
                output += (indentStr.repeat(indent) + "<oktato>" + teacher.getTextContent() + "</oktato>\n");
            } else {
                output += (indentStr.repeat(indent) + "<oktato>Unknown</oktato>\n");
            }

            indent--;
            output += (indentStr.repeat(indent) + "</kurzus>");
            return output;
        }

        private static String printTime(Node time, int indent, String indentStr) {
            String output = "";
            output += (indentStr.repeat(indent) + "<idopont>\n");

            String[] timeParts = getTimeParts(time);
            String dayValue = timeParts[0];
            String fromValue = timeParts[1];
            String toValue = timeParts[2];

            indent++;
            output += (indentStr.repeat(indent) + "<nap>" + dayValue + "</nap>\n");
            output += (indentStr.repeat(indent) + "<tol>" + fromValue + "</tol>\n");
            output += (indentStr.repeat(indent) + "<ig>" + toValue + "</ig>\n");

            indent--;
            output += (indentStr.repeat(indent) + "</idopont>\n");
            return output;
        }

        private static String printAttributes(NamedNodeMap attributes) {
            String output = "";
            if (attributes.getLength() == 0) {
                output += (">\n");
            } else {
                output += (" ");
                for (int i = 0; i < attributes.getLength(); i++) {
                    output += (attributes.item(i).getNodeName() + "=\"" + attributes.item(i).getNodeValue() + "\"");
                    if (i != attributes.getLength() - 1) {
                        output += (" ");
                    }
                }
                output += (">\n");
            }
            return output;

        }
    }
}
