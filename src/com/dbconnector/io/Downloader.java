package com.dbconnector.io;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.jar.JarFile;

/**
 * Created by shaola on 11.10.2015.
 */
public class Downloader {

    public static JarFile downloadDriver(URL driverLocation) throws IOException, FileNotFoundException {
        ReadableByteChannel rbc = Channels.newChannel(driverLocation.openStream());
        FileOutputStream fos = new FileOutputStream(parseToName(driverLocation));
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        return new JarFile(parseToName(driverLocation));
    }

    public static String parseToName(URL url){
        String urlString = url.toString();
        String [] dividedUrlString = urlString.split("/");
        return dividedUrlString[dividedUrlString.length-1];
    }

}

