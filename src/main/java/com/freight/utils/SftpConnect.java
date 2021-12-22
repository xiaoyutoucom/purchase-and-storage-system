package com.freight.utils;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

@Component
public class SftpConnect {
    private static Logger logger = LoggerFactory.getLogger(SftpConnect.class);

    private long sleepTime = 60000;
    private Session sshSession;
    private ChannelSftp sftp;
    //FTP 服务器地址IP地址
    @Value("${sftp.MF_SFTP_URL}")
    public String host;
    // FTP 登录用户名
    @Value("${sftp.MF_SFTP_USERNAME}")
    public String userName;
    // FTP 登录密码
    @Value("${sftp.MF_SFTP_PASSWORD}")
    public String pwd;

    /**
     * 连接sftp
     *
     * @return
     * @throws Exception
     */
    public ChannelSftp connect() throws Exception {
        try {
            JSch jsch = new JSch();
            logger.info("sftp host: " + host + "; userName:" + userName);
            sshSession = jsch.getSession(userName, host,22);
            logger.debug("Session 已建立.");
            if (pwd != null) {
                sshSession.setPassword(pwd);
            }
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            logger.debug("Session 已连接.");
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();

            sftp = (ChannelSftp) channel;
            logger.info("连接到SFTP成功。host: " + host);
        } catch (Exception e) {
            logger.error("连接sftp失败！");
            Thread.sleep(sleepTime);
            logger.info("重新连接....");
            connect();
        }
        return sftp;
    }

    /**
     * 关闭连接 server
     */
    public void disconnect(ChannelSftp sftp) {
        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
                sshSession.disconnect();
                logger.info("sftp连接关闭成功！" + sftp);
            } else if (sftp.isClosed()) {
                logger.warn("sftp 已经关闭,不需要重复关闭！" + sftp);
            }
        }
    }
}