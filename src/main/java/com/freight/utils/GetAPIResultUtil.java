package com.freight.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class GetAPIResultUtil {
    /**
     *
     *
     * @param url
     * @param param
     * @return
     */
    public static String getAPIResult(String url, String param, String plainCredentials) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);

            URLConnection conn = realUrl.openConnection();
            //conn.setConnectTimeout(5000);

            // String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
            conn.setRequestProperty("Authorization", "Bearer " + plainCredentials);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
//            out = new PrintWriter(conn.getOutputStream());
//            out.print("accountId:30002549\n" +
//                    "fromSuburbName:Sockburn\n" +
//                    "toSuburbName:Balfour\n" +
//                    "numberPieces:1\n" +
//                    "weight:30\n" +
//                    "volume:0.579\n" +
//                    "fromPostCode:1061\n" +
//                    "toPostcode:9779");
//            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            in.readLine();
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                in.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

}
