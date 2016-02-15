package com.dbconnector.external;

import com.dbconnector.exceptions.FieldsNotSetException;
import com.dbconnector.exceptions.NoDriverFoundException;
import com.dbconnector.exceptions.RequiredParameterNotSetException;
import com.dbconnector.io.FileRead;
import com.dbconnector.model.DbTemplate;
import com.dbconnector.model.DbType;
import com.dbconnector.net.Connect;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created by Dmitry Chokovski on 30.11.15.
 *
 *
 * This class implements the functions defined in "DbConnectorAPI".
 *
 */

public class APIFunctions implements DbConnectorAPI {

    public APIFunctions() {

    }

    // NOTE: This function is as a console-based demonstration of how this API works should be integrated!
    @Override
    public Connection APIDemo(String pathOfPropertiesDirectory) throws IOException, ClassNotFoundException {
        Scanner reader = new Scanner(System.in);
        Connection connectionObject = null;
        System.out.println("Reading Properties with \"readConfigs()\"...");
        Map<String,DbTemplate> templateMap = readConfigs(pathOfPropertiesDirectory);
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
            manualPopulateFields(dbTemplate);
            System.out.println("The fields are now set!");
        }
        try{
            System.out.println("Verifying DBTemplate and trying to establish connection...");
            connectionObject = connectToDb(dbTemplate);
            if (!connectionObject.equals(null)){ System.out.println("Connection successfully established!"); }
        } catch (FieldsNotSetException e){
            e.toString();
        } catch (RequiredParameterNotSetException e){
            e.toString();
        } catch (NoDriverFoundException e){
            e.toString();
        }
        reader.close();
        return connectionObject;
    }

    @Override
    public Map<String, DbTemplate> readConfigs(String pathOfPropertiesDirectory) throws IOException {
        return FileRead.readDbList(pathOfPropertiesDirectory);
    }

    @Override
    public Connection connectToDb(Map<String, DbTemplate> dbTemplateMap, String dbName) throws NoDriverFoundException, ClassNotFoundException, FieldsNotSetException, RequiredParameterNotSetException  {
        DbTemplate dbTemplate = dbTemplateMap.get(dbName);
        dbTemplate.verify();
        dbTemplate.resolveURL();
        Connection connectionObject = Connect.establishConnection(dbTemplate);
        return connectionObject;
    }

    @Override
    public Connection connectToDb(DbTemplate dbTemplate) throws NoDriverFoundException, ClassNotFoundException, FieldsNotSetException, RequiredParameterNotSetException  {
        dbTemplate.verify();
        dbTemplate.resolveURL();
        Connection connectionObject = Connect.establishConnection(dbTemplate);
        return connectionObject;
    }

    @Override
    public Connection connectToDb(Properties properties, Map<String, String> fields) throws NoDriverFoundException, ClassNotFoundException, FieldsNotSetException, RequiredParameterNotSetException {
        DbTemplate dbTemplate = new DbTemplate(properties);
        dbTemplate.setFields(fields);
        dbTemplate.verify();
        dbTemplate.resolveURL();
        Connection connectionObject = Connect.establishConnection(dbTemplate);
        return connectionObject;
    }

    @Override
    public Connection connectToDb(DbType type, String dbname, String user, String password, String port) {
        return null;
    }

    @Override
    public Connection connectToDb(DbType type, String dbname, String user, String password) {
        return null;
    }

    @Override
    public Connection connectToDb(DbType type, String dbname) {
        return null;
    }

    @Override
    public void manualPopulateFields(DbTemplate dbTemplate) {
        Scanner reader = new Scanner(System.in);
        for(Map.Entry<String, String> entry : dbTemplate.getFields().entrySet()){
            System.out.println("Enter Value for \"" + entry.getKey() + "\": ");
            String newValue = reader.next();
            entry.setValue(newValue);
        }
        reader.close();
        dbTemplate.ready();
    }

    @Override
    public void listDrivers() {
        Enumeration driverList = DriverManager.getDrivers();
        while (driverList.hasMoreElements()) {
            Driver driverClass = (Driver) driverList.nextElement();
            System.out.println("Driver: " + driverClass.getClass().getName());
        }
    }
}
