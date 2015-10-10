package com.dbconnector.connect;

import com.dbconnector.io.FileRead;
import com.dbconnector.model.DbTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.List;
import java.util.Properties;

/**
 * Created by shaola on 18.09.15.
 */
public class Connect {

    public static void main(String [] args) throws IOException {
        DbTemplate mysql = FileRead.readDbList("").get(0);

        mysql.getFields().put("User", "admin");
        mysql.getFields().put("Server", "192.168.1.1");
        mysql.getFields().put("Database", "testdb");
        mysql.getFields().put("Password", "qwerty");
//        mysql.getFields().get(0).setValue("localhost");
//        mysql.getFields().get(1).setValue("databasename");
//   //     mysql.getFields().get(2).setValue("666");
//        mysql.getFields().get(2).setValue("usr");
//        mysql.getFields().get(3).setValue("pwd");
        mysql.resolveURL();
        connectToDB(mysql);
    }

    public static void connectToDB(DbTemplate template){
        Connection connection;
        try {
            connection = DriverManager.getConnection(template.getProperties().getProperty("url"));
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

        } catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return;
        } catch (Exception e){
            System.err.println("Parameter count error, please check your dblist.txt!" +
                    "\n Allowed formats are: " +
                    "\n param=[ConnectionString] " +
                    "\n OR" +
                    "\n param=[ConnectionString]" +
                    "\n param=[User]" +
                    "\n param=[Password]");
        }
    }
}
