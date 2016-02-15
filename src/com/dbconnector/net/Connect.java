package com.dbconnector.net;

import com.dbconnector.exceptions.NoDriverFoundException;
import com.dbconnector.io.Downloader;
import com.dbconnector.io.Loader;
import com.dbconnector.model.DbTemplate;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by Dmitry Chokovski on 18.09.15.
 *
 * Used to net to Databases by creating Connection-Objects
 *
 */

public class Connect {


    // Establishes connection to database, returns Connection Object.
    public static Connection establishConnection(DbTemplate template) throws NoDriverFoundException, ClassNotFoundException{
        Connection connection = null;
        Properties properties = template.getProperties();
        try {
            if(properties.getProperty("forceDriver").equals("true") || DriverManager.getDriver(properties.getProperty("url"))==null){
                // Downloads driver
                File newDriver = Downloader.downloadDriver(Downloader.makeUrl(properties.getProperty("driver")));
                // Loads driver
                Loader.loadDriverClass(template, newDriver);
            }
            // Establishes connection
            connection = DriverManager.getConnection(template.getProperties().getProperty("url"));
        } catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (IOException e){
            e.printStackTrace();
        }
        return connection;
    }

    // Lists all available drivers - FOR TESTING PURPOSES
    public static void listDrivers() {
        Enumeration driverList = DriverManager.getDrivers();
        while (driverList.hasMoreElements()) {
            Driver driverClass = (Driver) driverList.nextElement();
            System.out.println("driver: " + driverClass.getClass().getName());
        }
    }
}



//    public static void main(String [] args) throws IOException, ClassNotFoundException, NoDriverFoundException {
//        DbTemplate mysql = FileRead.readDbList("").get(0);
//
////        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        listDrivers();
//
//        mysql.getFields().put("User", "admin");
//        mysql.getFields().put("Server", "192.168.1.1");
//        mysql.getFields().put("Database", "testdb");
//        mysql.getFields().put("Password", "qwerty");
//
//        mysql.resolveURL();
//        establishConnection(mysql);
//    }


//            int paramCount = db.getParams().size();
////            if(driver == null){
////                URL website = new URL("http://shaola.de/driver1");
////                System.out.println(website.getFile());
////                //URL driverUrl = new URL(db.getDriverPath());
////                ReadableByteChannel rbc = Channels.newChannel(website.openStream());
////                //FileOutputStream fos = new FileOutputStream("information.html");
////                FileOutputStream fos = new FileOutputStream(website.getFile().replaceFirst("/", "").replaceAll("/", "."));
////                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
////                //DriverManager.registerDriver(newDriver);
////            }
//            if(paramCount==1){
//                System.out.println("Paramcount: " + paramCount);
//                conn = DriverManager.getConnection(db.getParams().get(0));
//            }
//            else if(paramCount==3){
//                System.out.println("Paramcount: " + paramCount);
//                conn = DriverManager.getConnection(db.getParams().get(0), db.getParams().get(1), db.getParams().get(2));
//            }
//            else{
//                throw new Exception();
//            }