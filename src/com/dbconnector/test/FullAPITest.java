package com.dbconnector.test;

import com.dbconnector.exceptions.RequiredParameterNotSetException;
import com.dbconnector.io.Downloader;
import com.dbconnector.io.Loader;
import com.dbconnector.net.Connect;
import com.dbconnector.exceptions.FieldsNotSetException;
import com.dbconnector.exceptions.NoDriverFoundException;
import com.dbconnector.io.FileRead;
import com.dbconnector.model.DbTemplate;
import com.dbconnector.external.APIFunctions;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Dmitry Chokovski on 20.10.15.
 *
 * Test method to test full API functionality by simulation Use-Case: Read properties-Files, Download driver,
 * load class, establish connection and return Connection-Object.
 *
 */


public class FullAPITest {

    APIFunctions api = new APIFunctions();

    public static void main(String args []) throws IOException, NoDriverFoundException, ClassNotFoundException, FieldsNotSetException, SQLException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {

        // Reading Properties and creating DbTemplates, then putting them into map, Key = name-property
        Map<String, DbTemplate> templates = FileRead.readDbList("/Users/shaola/IntellijProjects/DBConnector/properties");

        // Extracting Template for testing
        DbTemplate testTemplate = templates.get("MySQL");

        //Filling with connection parameters to test-db
//        testTemplate.getFields().put("User", "root");
        testTemplate.getFields().put("Server", "localhost");
        testTemplate.getFields().put("Port", "3306");
        testTemplate.getFields().put("Database", "ctms2");
//        testTemplate.getFields().put("Password", "mysql");
        testTemplate.setPassword("mysql");
        testTemplate.setUsername("root");

        try {
            testTemplate.verify();
        } catch (RequiredParameterNotSetException e) {
            e.printStackTrace();
        }

        // Resolving URL by substituting field names with above params
        testTemplate.resolveURL();
        File newDriver = Downloader.downloadDriver(Downloader.makeUrl(testTemplate.getProperties().getProperty("driver")));

        Loader.loadDriverClass(testTemplate, newDriver);

        Connect.listDrivers();

        // Establishing connection to DB
        //Connect.establishConnection(testTemplate);
    }


    public Connection APIDemo(String pathOfPropertiesDirectory) throws IOException, ClassNotFoundException {
        Scanner reader = new Scanner(System.in);
        Connection connectionObject = null;
        System.out.println("Reading Properties with \"readConfigs()\"...");
        Map<String,DbTemplate> templateMap = api.readConfigs(pathOfPropertiesDirectory);
        System.out.println("The following Configurations are available:");
        for(Map.Entry<String,DbTemplate> entry : templateMap.entrySet()){
            System.out.print(entry.getKey() + " ");
        }
        System.out.print("\n");
        System.out.println("Please enter name of required Configuration: ");
        String input = reader.next();
        String selection = input.trim();
        DbTemplate dbTemplate = templateMap.get(selection);
        System.out.println("You selected \""+selection+"\" .");
        if(!dbTemplate.isReady()){
            System.out.println("FieldsNotSetException! \""+selection+"\" does not have any field values set. Please enter the Values for the Fields!");
            api.manualPopulateFields(dbTemplate);
            System.out.println("The fields are now set!");
        }
        try{
            System.out.println("Verifying DBTemplate and trying to establish connection...");
            connectionObject = api.connectToDb(dbTemplate);
            if (!connectionObject.equals(null)){ System.out.println("Connection successfully established!"); }
        } catch (FieldsNotSetException e){
            e.toString();
        } catch (RequiredParameterNotSetException e){
            e.toString();
        } catch (NoDriverFoundException e){
            e.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        reader.close();
        return connectionObject;
    }

}
