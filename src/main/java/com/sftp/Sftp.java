package com.sftp;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.ProxyHTTP;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

/**
 * @see https://stackoverflow.com/questions/13680057/downloading-files-from-an-sftp-server-using-jsch
 */
public class Sftp {

    public static void main(String[] args){
        list();
    }

    /**
     * 
     */
    public static void list() {
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession("<username>", "<sftp.example.com>");
            session.setPassword("<password>");
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);            
            System.out.println("Connecting to the sftp...");
            session.connect();
            System.out.println("Connected to the sftp.");            
            System.out.println("---------------------------------------------");
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;
            try {
                channelSftp.cd("/"); // CD to require remote folder here
            } catch (SftpException sftpException) {
                System.out.println("Failed to change the directory in sftp.");                
            }
            // Listing all the .xml file(s) here, change as required
            Vector<ChannelSftp.LsEntry> listEntries = channelSftp.ls(
                    new StringBuilder("*").append(".xml").toString());
            if (listEntries.isEmpty()) {
                System.out.println("No files found");
            }

            // Iterating the list of entries to list
            for (ChannelSftp.LsEntry entry : listEntries) {
                System.out.println("File: "+ entry.getFilename());
            }
        } catch (Exception exception) {
            System.out.println("Unknown exception");
            exception.printStackTrace();
        } finally {
            if (channelSftp != null) {
                channelSftp.exit();
            }
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }
}
