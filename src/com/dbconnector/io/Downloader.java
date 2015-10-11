package com.dbconnector.io;

import com.dbconnector.exceptions.NoDriverFoundException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.jar.JarFile;

/**
 * Created by Dmitry Chokovski on 11.10.2015.
 *
 * Class used for dowload of JDBC Drivers.
 *
 */
public class Downloader {

    public static JarFile downloadDriver(URL driverLocation) throws IOException, FileNotFoundException, NoDriverFoundException {
        if (driverLocation==null) throw new NoDriverFoundException();
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

    public static URL makeUrl(String urlString){
        URL fixedUrl = null;
        try {
            fixedUrl = new URL(urlString);
        } catch (MalformedURLException e) {
            if(!urlString.startsWith("http://") && !urlString.startsWith("ftp://") && !urlString.startsWith("https://")){
                urlString = "http://" + urlString;
                fixedUrl = new URL(urlString);
            }
        } finally {
          return fixedUrl;
        }
    }

}

