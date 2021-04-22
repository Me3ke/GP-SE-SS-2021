package gpse.example.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

public class PDFWriter {

    public void generateXML(String owner, List<String> signatures, List<String> history, int protocolID)
        throws XMLTransformationException {

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("protocol");
            doc.appendChild(rootElement);

            Element protocolBody = doc.createElement("protocolBody");
            rootElement.appendChild(protocolBody);

            Element ownerElem = doc.createElement("ownerElem");
            ownerElem.appendChild(doc.createTextNode(owner));
            protocolBody.appendChild(ownerElem);

            Element sigs = doc.createElement("sigs");
            protocolBody.appendChild(sigs);

            Element[] sig = new Element[signatures.size()];
            for (int i = 0; i < signatures.size(); i++) {
                sig[i] = doc.createElement("sigElem");
                sig[i].appendChild(doc.createTextNode(signatures.get(i)));
                sigs.appendChild(sig[i]);
            }

            Element h = doc.createElement("hist");
            protocolBody.appendChild(h);

            Element[] hist = new Element[history.size()];
            for (int i = 0; i < signatures.size(); i++) {
                hist[i] = doc.createElement("histElem");
                hist[i].appendChild(doc.createTextNode(history.get(i)));
                h.appendChild(hist[i]);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(
                new File("src/main/resources/output/Protocol_" + protocolID + ".xml"));
            transformer.transform(source, result);

        } catch (ParserConfigurationException | TransformerException exc) {
            throw new XMLTransformationException(exc);
        }
    }

    public void convertToPDF(int protocolID) throws PDFConversionException {

        try (OutputStream out = Files.newOutputStream(
            Paths.get("src/main/resources/output/Protocol_" + protocolID + ".pdf"))) {

            File xsltFile = new File("src/main/resources/Protocol_Template.xsl");
            StreamSource xmlFile = new StreamSource(
                new File("src/main/resources/output/Protocol_" + protocolID + ".xml"));

            FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));

            Result res = new SAXResult(fop.getDefaultHandler());

            transformer.transform(xmlFile, res);

        } catch (FOPException | TransformerException | IOException exc) {
            throw new PDFConversionException(exc);
        }
    }
}
