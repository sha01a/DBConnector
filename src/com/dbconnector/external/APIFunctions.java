package com.dbconnector.external;

import com.dbconnector.io.FileRead;
import com.dbconnector.model.DbTemplate;
import com.dbconnector.model.DbType;

import java.io.IOException;
import java.sql.Connection;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created by shaola on 30.11.15.
 */
public class APIFunctions implements DbConnectorAPI {

    public APIFunctions() {

    }

    @Override
    public Connection parseAndConnectToDb(String pathOfPropertiesDirectory, String nameOfDb) {
        return null;
    }

    @Override
    public Map<String, DbTemplate> readConfigs(String pathOfPropertiesDirectory) throws IOException {
        return FileRead.readDbList(pathOfPropertiesDirectory);
    }

    @Override
    public Connection connectToDb(Map dbTemplateMap, String dbName) {
        return null;
    }

    @Override
    public Connection connectToDb(DbTemplate dbTemplate) throws IOException {



        return null;
    }

    @Override
    public Connection connectToDb(Properties properties, Map<String, String> fields) {
        return null;
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
    }

    @Override
    public String listDrivers() {
        return null;
    }
}
