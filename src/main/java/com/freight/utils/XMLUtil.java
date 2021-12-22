package com.freight.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class XMLUtil {
    /**
     * 将String类型的xml转换成对象
     */
    public static Object convertXmlStrToObject(Class clazz, String xmlStr) {
        Object xmlObject = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            // 进行将Xml转成对象的核心接口
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader sr = new StringReader(xmlStr);
            xmlObject = unmarshaller.unmarshal(sr);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlObject;
    }
    public static <T> T xmlToObject(Class<T> clazz, String xml) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(xml);
            T message = (T) unmarshaller.unmarshal(reader);
            return message;
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 实体bean转成xml
     * @param message 实体bean
     * @return String
     */
    public static String bean2xml(Object message) {
        String result = null;
        JAXBContext context = null;
        Marshaller marshaller = null;
        String __defult_encoding = "UTF-8";
        try {
            context = JAXBContext.newInstance(message.getClass());
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, __defult_encoding);

            StringWriter writer = new StringWriter();
            marshaller.marshal(message, writer);
            result = writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
