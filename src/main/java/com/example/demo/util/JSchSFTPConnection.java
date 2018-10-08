package com.example.demo.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.example.demo.config.AppConfiguration;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.ProxyHTTP;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class JSchSFTPConnection {

    private static final Logger logger = LoggerFactory.getLogger(JSchSFTPConnection.class);

    @Autowired
    private AppConfiguration appConfiguration;


    private static Session session = null;
    private static Channel channel = null;
    private static ChannelSftp channelSftp = null;

    public void connect(){
        logger.info("Preparing SFTP Host...");
        JSch jsch = new JSch();
        try {
			session = jsch.getSession(
			    appConfiguration.getCentralRepository().getUsername(), 
			    appConfiguration.getCentralRepository().getHost(), 
			    appConfiguration.getCentralRepository().getPort());
		} catch (JSchException e) {
			e.printStackTrace();
		}
        session.setPassword(appConfiguration.getCentralRepository().getPassword());
        if(appConfiguration.getProxy().getEnabled()){
            session.setProxy(new ProxyHTTP(appConfiguration.getProxy().getUrl(), appConfiguration.getProxy().getPort()));
        }

        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        try {
			session.connect();
		} catch (JSchException e) {
			e.printStackTrace();
		}

        logger.info("SFTP Host connected...");
        try {
			channel = session.openChannel("sftp");
		} catch (JSchException e) {
			e.printStackTrace();
		}
        try {
			channel.connect();
		} catch (JSchException e) {
			e.printStackTrace();
		}
        channelSftp = (ChannelSftp) channel;
    }

    public void disconnect(){
        channelSftp.exit();
        logger.info("SFTP Channel disconnected...");
        channel.disconnect();
        logger.info("Channel disconnected...");
        session.disconnect();
        logger.info("Host Session disconnected...");
    }


    public Boolean send(File file) {

        Boolean flag = false;

        try {
            connect();
            // default destination is /home/ 
            // parameters are (src,dest)
            // channelSftp.put(new FileInputStream(file), file.getName());
            channelSftp.put(
            appConfiguration.getCentralRepository().getTemp_url()+file.getName(), 
            appConfiguration.getCentralRepository().getUpload_url()
            );

            logger.info("SFTP File Sent: " + file.getName());
            flag = true;

        } catch (Exception e) {
            e.printStackTrace();
        
        } finally {
            disconnect();
        }
        return flag;
    }

    // public void checkDirectory(){
    //     channelSftp = (ChannelSftp)channel;
    //     String currentDirectory = "";
	// 	try {
	// 		currentDirectory = channelSftp.pwd();
	// 	} catch (SftpException e1) {
	// 		// TODO Auto-generated catch block
	// 		e1.printStackTrace();
	// 	}
    //     String dir="sophi";
    //     SftpATTRS attrs=null;
    //     try {
    //         attrs = channelSftp.stat(currentDirectory+"/"+dir);
    //     } catch (Exception e) {
    //         System.out.println(currentDirectory+"/"+dir+" not found");
    //     }

    //     if (attrs != null) {
    //         System.out.println("Directory exists IsDir="+attrs.isDir());
    //     } else {
    //         System.out.println("Creating dir "+dir);
    //         try {
	// 			channelSftp.mkdir(dir);
	// 		} catch (SftpException e) {
	// 			// TODO Auto-generated catch block
	// 			e.printStackTrace();
	// 		}
    //     }
    // }



//  public void receive(String fileName) throws Exception {

//   try {
//     connect();

//     byte[] buffer = new byte[1024];
//     BufferedInputStream bis = new BufferedInputStream(channelSftp.get(fileName));
//     File newFile = new File(fileName);
//     OutputStream os = new FileOutputStream(newFile);
//     BufferedOutputStream bos = new BufferedOutputStream(os);
//     int readCount;

//     while ((readCount = bis.read(buffer)) > 0) {
//         bos.write(buffer, 0, readCount);
//     }

//     bis.close();
//     bos.close();

//   } catch (Exception e) {
//    e.printStackTrace();
//   }

//  }

}