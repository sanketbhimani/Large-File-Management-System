
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Meet Dave
 */
public class search {

    HashMap<String, ArrayList<String>> hypernymMapId, hyponymMapId;
    ArrayList<String> hypernym, hyponym, hypernymTemp, hyponymTemp;

    public void searchWord(String searchKey) throws FileNotFoundException {
        try {
            ArrayList<String> synset = new ArrayList<String>();
            
            ArrayList<String> definitions = new ArrayList<String>();
            hypernym = new ArrayList<String>();
            hyponym = new ArrayList<String>();
            hypernymMapId = new HashMap<String, ArrayList<String>>();
            hyponymMapId = new HashMap<String, ArrayList<String>>();
            boolean foundFlag = false;
            boolean definitionFlag = false;
            InputStream is = new FileInputStream("C:\\Users\\Sanket Bhimani\\Desktop\\sdp project\\EWNlexicalEntry.xml");
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(is);
            while (reader.hasNext()) {
                int event = reader.next();
                
                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        String element = reader.getLocalName();
                        if (element.equalsIgnoreCase("lemma")) {
                            
                            if (searchKey.equalsIgnoreCase(reader.getAttributeValue(1))) {
                                foundFlag = true;
                                
                            }
                        }
                        
                        if (element.equalsIgnoreCase("sense")) {
                            if (foundFlag) {
                                synset.add(reader.getAttributeValue(0));
                             //   System.out.println(reader.getAttributeValue(0));
                            }
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        String closingElement = reader.getLocalName();
                        if (closingElement.equalsIgnoreCase("lexicalentry")) {
                            foundFlag = false;
                            //break;
                        }
                        break;
                }
            }
            
            is = new FileInputStream("C:\\Users\\Sanket Bhimani\\Desktop\\sdp project\\EWNsynset.xml");
            reader = factory.createXMLStreamReader(is);
            String temp = "";
            while (reader.hasNext()) {
                int event = reader.next();
                if (synset.size() == 0) {
                    
                    break;
                }
                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        String startElement = reader.getLocalName();
                        if (startElement.equalsIgnoreCase("synset")) {
                            hypernym.clear();
                            hyponym.clear();
                            temp = reader.getAttributeValue(1);
                            if (synset.contains(temp)) {
                                definitionFlag = true;
                            }
                        }
                        
                        if (startElement.equalsIgnoreCase("definition")) {
                            if (definitionFlag) {
                               // System.out.println(reader.getAttributeValue(0));
                                definitions.add(reader.getAttributeValue(0));
                                
                            }
                            
                        }
                        
                        if (startElement.equalsIgnoreCase("SynsetRelation")) {
                            if (definitionFlag) {
                                String type = reader.getAttributeValue(0);
                                if (type.equalsIgnoreCase("has_hypernym")) {
                                    hypernym.add(reader.getAttributeValue(1));
                                } else if (type.equalsIgnoreCase("has_hyponym")) {
                                    hyponym.add(reader.getAttributeValue(1));
                                }
                            }
                        }
                        break;
                        
                    case XMLStreamConstants.END_ELEMENT:
                        String endElement = reader.getLocalName();
                        if (endElement.equalsIgnoreCase("synset")) {
                            if (definitionFlag) {
                                
                                hypernymTemp = (ArrayList<String>) hypernym.clone();
                                hyponymTemp = (ArrayList<String>) hyponym.clone();
                                
                                hypernymMapId.put(definitions.get(definitions.size() - 1), searchHypernym(hypernymTemp));
                                hyponymMapId.put(definitions.get(definitions.size() - 1), searchHyponym(hyponymTemp));
                                hyponym.clear();
                                hypernym.clear();
                                synset.remove(temp);
                                definitionFlag = false;
                            }
                        }
                }
            }
        } catch (XMLStreamException ex) {
            Logger.getLogger(search.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }

    }

    public ArrayList<String> searchHypernym(ArrayList<String> hypernymkeys) throws FileNotFoundException, XMLStreamException {
        ArrayList<String> result = new ArrayList<String>();
        InputStream is = new FileInputStream("C:\\Users\\Sanket Bhimani\\Desktop\\sdp project\\EWNlexicalEntry.xml");
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(is);
        String word = "";
        while (reader.hasNext()) {
            if (hypernymkeys.size() == 0) {
                break;
            }
            int event = reader.next();

            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    String startElement = reader.getLocalName();
                    if (startElement.equalsIgnoreCase("lemma")) {
                        word = reader.getAttributeValue(1);
                    }

                    if (startElement.equalsIgnoreCase("sense")) {
                        if (hypernymkeys.contains(reader.getAttributeValue(0))) {
                            result.add(word);
                            hypernymkeys.remove(reader.getAttributeValue(0));
                        }
                    }
            }

        }
        return result;
    }

    public ArrayList<String> searchHyponym(ArrayList<String> hyponymkeys) throws FileNotFoundException, XMLStreamException {
        ArrayList<String> result = new ArrayList<String>();
        InputStream is = new FileInputStream("C:\\Users\\Sanket Bhimani\\Desktop\\sdp project\\EWNlexicalEntry.xml");
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(is);
        String word = "";
        while (reader.hasNext()) {
            if (hyponymkeys.size() == 0) {
                break;
            }
            int event = reader.next();

            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    String startElement = reader.getLocalName();
                    if (startElement.equalsIgnoreCase("lemma")) {
                        word = reader.getAttributeValue(1);
                    }

                    if (startElement.equalsIgnoreCase("sense")) {
                        if (hyponymkeys.contains(reader.getAttributeValue(0))) {
                            result.add(word);
                            hyponymkeys.remove(reader.getAttributeValue(0));
                        }
                    }
            }

        }
        return result;
    }

    public HashMap<String, ArrayList<String>> getHypernym() {
        return hypernymMapId;
    }

    public HashMap<String, ArrayList<String>> getHyponym() {
        return hyponymMapId;
    }

}
