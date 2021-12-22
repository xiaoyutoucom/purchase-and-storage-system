package com.freight.utils;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.freight.entity.GSS.pushR.shipmentsbody;
import com.jcraft.jsch.ChannelSftp;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class SaveUtil {

    @Autowired
    SftpConnect sfrpconn;
    @Autowired
    SftpCommon sftpcommon;
    @Autowired
    S3Util s3;
    Regions clientRegion = Regions.AP_SOUTHEAST_2;
    @Value("${AWS.bucketName}")
    String bucketName;
    //保存文件到服务器
    public boolean Savexml(String xml,String Name)
    {
        try {
            Name = Name+".xml";
            // 1、设置生成xml的格式
            OutputFormat format = OutputFormat.createPrettyPrint();
            // 设置编码格式
            format.setEncoding("UTF-8");
            // 6、生成xml文件
            File file = new File(Name);

            XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
            // 设置是否转义，默认使用转义字符
            writer.setEscapeText(false);
            writer.write(xml);
            writer.close();
            System.out.println("生成rss.xml成功");
            InputStream inputStream = null;
            inputStream = new FileInputStream(file);
            file.deleteOnExit();
            ChannelSftp sfrp = sfrpconn.connect();
            //保存到sftp服务器
            boolean ok = sftpcommon.uploadFile(sfrp,inputStream,Name);
            sfrpconn.disconnect(sfrp);
            //保存到亚马逊云
            s3.creatConnect();
            //List<Bucket> bucke=s3.getBuckets();
            //创建桶
            //Bucket buck = s3.creatBucket(bucketName);
            s3.getBacketObjects(bucketName);
            //上传文件
            PutObjectResult pr= s3.creatObject(bucketName,"mainfreight/xml/", Name,file);
            //mainfreight/xml/mainfreight.xml
            //ObjectListing ob = s3.getBacketObjects(bucketName);
            //测试是否上传成功
            //String url =s3.getDownloadUrl(bucketName,"mainfreight/xml/mainfreight.xml");
            return ok;
        }
        catch (Exception e) {
//            e.printStackTrace();
            System.out.println("保存xml文件失败");
            return false;
        }
    }
    /**
     * 将base64编码转换成PDF
     * @param base64sString 1.使用BASE64Decoder对编码的字符串解码成字节数组
     *            2.使用底层输入流ByteArrayInputStream对象从字节数组中获取数据；
     *            3.建立从底层输入流中读取数据的BufferedInputStream缓冲输出流对象；
     *            4.使用BufferedOutputStream和FileOutputSteam输出数据到指定的文件中
     * @param fileName 文件名称
     */
    public String generateBase64StringToFile(String url,String Name) {
        BufferedInputStream bin = null;
        FileOutputStream fout = null;
        BufferedOutputStream bout = null;
        shipmentsbody body = new shipmentsbody();
        try {
            s3.creatConnect();
            s3.getBacketObjects(bucketName);

            FileUtil fileutil = new FileUtil();
            File file = fileutil.getfile(url, Name);
            //上传文件
            PutObjectResult pr = s3.creatObject(bucketName, "fliway/jpg/", Name, file);
            String urljpg = s3.getDownloadUrl(bucketName, "fliway/jpg/" + Name);

            return urljpg;
        } catch (Exception e) {
            return "";
        } finally {

        }
    }
    /**
     * 将base64编码转换成PDF
     * @param base64sString 1.使用BASE64Decoder对编码的字符串解码成字节数组
     *            2.使用底层输入流ByteArrayInputStream对象从字节数组中获取数据；
     *            3.建立从底层输入流中读取数据的BufferedInputStream缓冲输出流对象；
     *            4.使用BufferedOutputStream和FileOutputSteam输出数据到指定的文件中
     * @param fileName 文件名称
     */
    public String saveFile(File file,String Name) {
        BufferedInputStream bin = null;
        FileOutputStream fout = null;
        BufferedOutputStream bout = null;
        shipmentsbody body = new shipmentsbody();
        try {
            s3.creatConnect();
            s3.getBacketObjects(bucketName);
            //上传文件
            PutObjectResult pr = s3.creatObject(bucketName, "fliway/jpg/", Name, file);
            String urljpg = s3.getDownloadUrl(bucketName, "fliway/jpg/" + Name);

            return urljpg;
        } catch (Exception e) {
            return "";
        } finally {

        }
    }
    public String getfile(String url,String name) throws ClientProtocolException, IOException {

        HttpGet get = new HttpGet(url);
        HttpResponse response = HttpClients.createDefault().execute(get);
        if (null == response || response.getStatusLine() == null) {
            return null;
        } else if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {

            return null;
        }
        byte[] btf = EntityUtils.toByteArray(response.getEntity());
        File dest = new File(name);
        InputStream in = null;
        OutputStream out = null;

        in = new ByteArrayInputStream(btf);
        out = new FileOutputStream(dest);

        byte []bt = new byte[1024];
        int length=0;
        while(	(length = in.read(bt)) !=-1) {
            //一次性写入文件a中
            out.write(bt,0,length);
        }

        if(null!=out) {
            out.close();
        }
        s3.creatConnect();
        s3.getBacketObjects(bucketName);
        //上传文件
        PutObjectResult pr = s3.creatObject(bucketName, "fliway/jpg/", name, dest);
        String urljpg = s3.getDownloadUrl(bucketName, "fliway/jpg/" + name);
        return urljpg;
    }

    /**
     * 传入要下载的图片的url列表，将url所对应的图片下载到本地
     *
     * @param urlList
     */
    public void downloadPicture(String urlString,String name) {
        URL url = null;

            try {
                url = new URL(urlString);
                DataInputStream dataInputStream = new DataInputStream(url.openStream());
                String imageName = name;
                FileOutputStream fileOutputStream = new FileOutputStream(new File(imageName));

                byte[] buffer = new byte[1024];
                int length;

                while ((length = dataInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, length);
                }

                dataInputStream.close();
                fileOutputStream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }


    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public   byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    //保存文件到服 务器
    public String SaveS3xml(String xml,String Name)
    {
        try {
            Name = Name+".xml";
            // 1、设置生成xml的格式
            OutputFormat format = OutputFormat.createPrettyPrint();
            // 设置编码格式
            format.setEncoding("UTF-8");
            // 6、生成xml文件
            File file = new File(Name);

            XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
            // 设置是否转义，默认使用转义字符
            writer.setEscapeText(false);
            writer.write(xml);
            writer.close();
            System.out.println("生成rss.xml成功");
            InputStream inputStream = null;
            inputStream = new FileInputStream(file);
            file.deleteOnExit();
            //保存到亚马逊云
            s3.creatConnect();
            //List<Bucket> bucke=s3.getBuckets();
            //创建桶
            //Bucket buck = s3.creatBucket(bucketName);
            s3.getBacketObjects(bucketName);
            //上传文件
            PutObjectResult pr= s3.creatObject(bucketName,"fliway/xml/", Name,file);
            //mainfreight/xml/mainfreight.xml
            //ObjectListing ob = s3.getBacketObjects(bucketName);
            //测试是否上传成功
            String url =s3.getDownloadUrl(bucketName,"fliway/xml/"+Name);
            return url;
        }
        catch (Exception e) {
//            e.printStackTrace();
            System.out.println("保存xml文件失败");
            return "保存xml文件失败"+e.toString();
        }
    }
}
