package dbd4topostgres.model;

import com.sun.org.apache.xpath.internal.XPathAPI;
import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLParser {

    public static Document getDocument(InputSource is) throws Exception{
        try {
            DocumentBuilderFactory docBuilderFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(is);
            return doc;
        } catch (SAXParseException e) {
            String msg = "Invalid XML @" + e.getColumnNumber() + ":"
                    + e.getLineNumber();
            throw new Exception(msg, e);
        } catch (SAXException e) {
            System.out.println(e.getException());
            throw new Exception("Invalid XML file", e);
        } catch (Exception e) {
        }
        throw new Exception("Invalid XML file");
    }

    public static Iterator getChildrenByTagName(Element element, String tagName) {
        if (element == null) {
            return null;
        }

        NodeList children = element.getChildNodes();
        ArrayList goodChildren = new ArrayList();
        for (int i = 0; i < children.getLength(); i++) {
            Node currentChild = children.item(i);
            if ((currentChild.getNodeType() != 1)
                    || (!((Element) currentChild).getTagName().equals(tagName))) {
                continue;
            }
            goodChildren.add((Element) currentChild);
        }

        return goodChildren.iterator();
    }

    public static Element getUniqueChild(Element element, String tagName) {
        Iterator goodChildren = getChildrenByTagName(element, tagName);

        if ((goodChildren != null) && (goodChildren.hasNext())) {
            Element child = (Element) goodChildren.next();
            if (goodChildren.hasNext()) {
                throw new RuntimeException("expected only one " + tagName + " tag");
            }
            return child;
        }
        throw new RuntimeException("expected one " + tagName + " tag");
    }

    public static Element getOptionalChild(Element element, String tagName)
            throws RuntimeException {
        return getOptionalChild(element, tagName, null);
    }

    public static Element getOptionalChild(Element element, String tagName, Element defaultElement)
            throws RuntimeException {
        Iterator goodChildren = getChildrenByTagName(element, tagName);

        if ((goodChildren != null) && (goodChildren.hasNext())) {
            Element child = (Element) goodChildren.next();
            if (goodChildren.hasNext()) {
                throw new RuntimeException("expected only one " + tagName + " tag");
            }
            return child;
        }
        return defaultElement;
    }

    public static String getElementContent(Element element)
            throws RuntimeException {
        return getElementContent(element, null);
    }

    public static String getElementContent(Element element, String defaultStr)
            throws RuntimeException {
        if (element == null) {
            return defaultStr;
        }
        NodeList children = element.getChildNodes();
        String result = "";
        for (int i = 0; i < children.getLength(); i++) {
            if ((children.item(i).getNodeType() == 3)
                    || (children.item(i).getNodeType() == 4)) {
                result = result + children.item(i).getNodeValue();
            } else {
                children.item(i).getNodeType();
            }

        }

        return DBDesignerModel4.normalizeAttribute(result.trim());
    }

    public static String getUniqueChildContent(Element element, String tagName)
            throws RuntimeException {
        return getElementContent(getUniqueChild(element, tagName));
    }

    public static String getOptionalChildContent(Element element, String tagName) {
        return getElementContent(getOptionalChild(element, tagName));
    }

    public static boolean getOptionalChildBooleanContent(Element element, String name) throws RuntimeException {
        Element child = getOptionalChild(element, name);
        if (child != null) {
            String value = getElementContent(child).toLowerCase();
            return (value.equals("true")) || (value.equals("yes")) || (value.equals("1"));
        }

        return false;
    }

    public static NodeIterator getNodeByXPath(Node target, String xpath) throws Exception {        
            Transformer serializer = TransformerFactory.newInstance().newTransformer();
            serializer.setOutputProperty("omit-xml-declaration", "yes");
            NodeIterator nl = XPathAPI.selectNodeIterator(target, xpath);
            return nl;                        
    }
    
    
}