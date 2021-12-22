package com.freight.utils;

import com.jcraft.jsch.ChannelSftp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class SftpCommon {
      Logger logger = LoggerFactory.getLogger(SftpCommon.class);
      /**
       * 上传文件到固定文件下
       *
       * @param sftp
       * @param targetPath
       * @param in
       * @param fileName
       * @return
       * @throws Exception
       */
      public boolean uploadFile(ChannelSftp sftp, InputStream in,String fileName) throws Exception {
            try {
                  sftp.cd("/In");
                  //上传文件到sftp服务器
                  sftp.put(in, fileName, ChannelSftp.RESUME);
            } catch (Exception e) {
                  logger.error("Upload file failure. TargetPath: {}");
                  return false;
            }
            return true;
      }

}
