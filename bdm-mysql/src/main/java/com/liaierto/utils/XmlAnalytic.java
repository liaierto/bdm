package com.liaierto.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class XmlAnalytic {

    public static Document parseDocument(InputStream in){
        SAXReader pReader = new SAXReader();
        Document doc = null;
        try {
            doc = pReader.read(in);
        } catch (DocumentException e) {
            
            e.printStackTrace();
        }
        return doc;
    }
    
    public static Map ParseXmlData(InputStream in){
        Document doc = parseDocument(in);
        Element root = doc.getRootElement();
        Map<String,String> map = new HashMap<String,String>();
        List<Element> pElementList = root.elements();
        for(Element pElement:pElementList){
            map.put(pElement.attributeValue("name"), pElement.attributeValue("class"));
        }
        
        return map;
    }
    
//    public static void main (String args[]){
//        Map pMap = testParseXmlData("WebRoot/AppHome/TABLEINFOTEST.xml");
//        
//        for (Iterator itr = pMap.keySet().iterator();itr.hasNext();){
//            String name = (String) itr.next();
//            String value= (String) pMap.get(name);
//            System.out.println(name+" is value "+value);
//        }
//    }
}
