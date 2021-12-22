package com.freight.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.*;

public class FileUtil {
    /**
     * 将base64编码转换成PDF
     * @param base64sString 1.使用BASE64Decoder对编码的字符串解码成字节数组
     *            2.使用底层输入流ByteArrayInputStream对象从字节数组中获取数据；
     *            3.建立从底层输入流中读取数据的BufferedInputStream缓冲输出流对象；
     *            4.使用BufferedOutputStream和FileOutputSteam输出数据到指定的文件中
     * @param fileName 文件名称
     */
    public File getfile(String filedata,String fulename) {
        BufferedInputStream bin = null;
        FileOutputStream fout = null;
        BufferedOutputStream bout = null;
        try {
            // 将base64编码的字符串解码成字节数组
            byte[] bytes = Base64.decodeBase64(filedata);
            // 创建一个将bytes作为其缓冲区的ByteArrayInputStream对象
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            // 创建从底层输入流中读取数据的缓冲输入流对象
            bin = new BufferedInputStream(bais);
            // 指定输出的文件
            File file = new File(fulename);
            // 创建到指定文件的输出流
            fout = new FileOutputStream(file);
            // 为文件输出流对接缓冲输出流对象
            bout = new BufferedOutputStream(fout);
            byte[] buffers = new byte[1024];
            int len = bin.read(buffers);
            while (len != -1) {
                bout.write(buffers, 0, len);
                len = bin.read(buffers);
            }
            // 刷新此输出流并强制写出所有缓冲的输出字节，必须这行代码，否则有可能有问题
            bout.flush();

            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                bin.close();
                fout.close();
                bout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public File getfile(byte[] bytes,String fulename) {
        BufferedInputStream bin = null;
        FileOutputStream fout = null;
        BufferedOutputStream bout = null;
        try {
            // 创建一个将bytes作为其缓冲区的ByteArrayInputStream对象
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            // 创建从底层输入流中读取数据的缓冲输入流对象
            bin = new BufferedInputStream(bais);
            // 指定输出的文件
            File file = new File(fulename);
            // 创建到指定文件的输出流
            fout = new FileOutputStream(file);
            // 为文件输出流对接缓冲输出流对象
            bout = new BufferedOutputStream(fout);
            byte[] buffers = new byte[1024];
            int len = bin.read(buffers);
            while (len != -1) {
                bout.write(buffers, 0, len);
                len = bin.read(buffers);
            }
            // 刷新此输出流并强制写出所有缓冲的输出字节，必须这行代码，否则有可能有问题
            bout.flush();

            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                bin.close();
                fout.close();
                bout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
