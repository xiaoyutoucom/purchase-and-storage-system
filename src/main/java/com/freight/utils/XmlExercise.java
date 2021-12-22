//package com.freight.utils;
//
//import net.sf.json.JSON;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//import net.sf.json.JSONSerializer;
//import net.sf.json.xml.XMLSerializer;
//import org.w3c.dom.Document;
//
//public class XmlExercise {
//    /**
//     * 将xml字符串<STRONG>转换</STRONG>为JSON字符串
//     *
//     * @param xmlString
//     *            xml字符串
//     * @return JSON<STRONG>对象</STRONG>
//     */
//    public static String xml2json(String xmlString) {
//        XMLSerializer xmlSerializer = new XMLSerializer();
//        JSON json = xmlSerializer.read(xmlString);
//        return json.toString(1);
//    }
//
//    /**
//     * 将xmlDocument<STRONG>转换</STRONG>为JSON<STRONG>对象</STRONG>
//     *
//     * @param xmlDocument
//     *            XML Document
//     * @return JSON<STRONG>对象</STRONG>
//     */
//    public static String xml2json(Document xmlDocument) {
//        return xml2json(xmlDocument.toString());
//    }
//
//    /**
//     * JSON(数组)字符串<STRONG>转换</STRONG>成XML字符串
//     *
//     * @param jsonString
//     * @return
//     */
//    public static String json2xml(String jsonString) {
//        XMLSerializer xmlSerializer = new XMLSerializer();
//        return xmlSerializer.write(JSONSerializer.toJSON(jsonString));
//        // return xmlSerializer.write(JSONArray.fromObject(jsonString));//这种方式只支持JSON数组
//    }
//    /**
//
//     * JSON 转换为XML
//
//     * 2017年4月27日 15:34:24
//
//     * @param json
//
//     * @return
//
//     */
//
//    public static String jsonToXML(String json) {
//        XMLSerializer xmlSerializer = new XMLSerializer();
//
//// 根节点名称
//
//        xmlSerializer.setRootName("xml");
//
//// 不对类型进行设置
//
//        xmlSerializer.setTypeHintsEnabled(false);
//
//        String xmlStr = "";
//
//        if (json.contains("[") && json.contains("]")) {
//// jsonArray
//
//            JSONArray jobj = JSONArray.fromObject(json);
//
//            xmlStr = xmlSerializer.write(jobj);
//
//        } else {
//// jsonObject
//
//            JSONObject jobj = JSONObject.fromObject(json);
//
//            xmlStr = xmlSerializer.write(jobj);
//
//        }
//
//        System.out.println("转换后的参数：" + xmlStr);
//
//        return xmlStr;
//
//    }
//}
