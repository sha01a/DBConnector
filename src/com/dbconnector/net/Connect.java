package com.dbconnector.net;

import com.dbconnector.exceptions.NoDriverFoundException;
import com.dbconnector.io.Downloader;
import com.dbconnector.io.Loader;
import com.dbconnector.model.DbTemplate;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by Dmitry Chokovski
 *
 * Used to connect to Databases by creating Connection-Objects
 *
 */

public class Connect {


    // Establishes connection to database, returns Connection Object.
    public static Connection establishConnection(DbTemplate template) throws NoDriverFoundException, ClassNotFoundException, SQLException{
        Connection connection = null;
        Properties properties = template.getProperties();
        File newDriver = null;
        try {
            if(properties.containsKey("forceDriver")) {
                if(properties.getProperty("forceDriver").equals("true") || DriverManager.getDriver(properties.getProperty("url"))==null){
                    // Downloads driver
                    newDriver = Downloader.downloadDriver(Downloader.makeUrl(properties.getProperty("driver")));
                    // Loads driver
                    Loader.loadDriverClass(template, newDriver);
                }
            }
            // Establishes connection with auth
            if(template.getAuthStatus()){
                Properties p = new Properties();
                p.setProperty("user", template.getUsername());
                p.setProperty("password", template.getPassword());
                connection = DriverManager.getConnection(template.getUrl(), p);
            }
            // Without auth
            else {
                connection = DriverManager.getConnection(template.getUrl());
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // Lists all available drivers - FOR TESTING PURPOSES
    public static void listDrivers(Driver driver) throws SQLException {
        DriverManager.registerDriver(driver);
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