package com.dbconnector.external;

import com.dbconnector.exceptions.FieldsNotSetException;
import com.dbconnector.exceptions.NoDriverFoundException;
import com.dbconnector.exceptions.RequiredParameterNotSetException;
import com.dbconnector.exceptions.TypeUnknownException;
import com.dbconnector.io.FileRead;
import com.dbconnector.model.DbTemplate;
import com.dbconnector.model.DbType;
import com.dbconnector.net.Connect;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.*;

/**
 * Created by Dmitry Chokovski
 *
 * This class implements the functions defined in "DbConnectorAPI".
 *
 */

public class APIFunctions implements DbConnectorAPI {

    public APIFunctions() {

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
    public Connection connectToDb(DbType type, String dbname, String user, String password, String host, int port) throws TypeUnknownException, FieldsNotSetException, RequiredParameterNotSetException, NoDriverFoundException, ClassNotFoundException {
        DbTemplate dbTemplate = fetchDbTemplate(type);
        Map<String, String> fields = new HashMap<>();
        fields.put("User", user);
        fields.put("Password", password);
        fields.put("Database", dbname);
        fields.put("Server", host);
        fields.put("Port", String.valueOf(port));
        dbTemplate.setFields(fields);
        dbTemplate.verify();
        dbTemplate.resolveURL();
        return Connect.establishConnection(dbTemplate);
    }

    @Override
    public Connection connectToDb(DbType type, String dbname, String user, String password, int port) throws TypeUnknownException, NoDriverFoundException, ClassNotFoundException, FieldsNotSetException, RequiredParameterNotSetException {
        DbTemplate dbTemplate = fetchDbTemplate(type);
        Map<String, String> fields = new HashMap<>();
        fields.put("User", user);
        fields.put("Password", password);
        fields.put("Database", dbname);
        fields.put("Server", "localhost");
        fields.put("Port", String.valueOf(port));
        dbTemplate.setFields(fields);
        dbTemplate.verify();
        dbTemplate.resolveURL();
        return Connect.establishConnection(dbTemplate);
    }

    @Override
    public Connection connectToDb(DbType type, String dbname, String user, String password, String host) throws TypeUnknownException, FieldsNotSetException, RequiredParameterNotSetException, NoDriverFoundException, ClassNotFoundException {
        DbTemplate dbTemplate = fetchDbTemplate(type);
        Map<String, String> fields = new HashMap<>();
        fields.put("User", user);
        fields.put("Password", password);
        fields.put("Database", dbname);
        fields.put("Server", host);
        fields.put("Port", String.valueOf(type.getDefaultPort()));
        dbTemplate.setFields(fields);
        dbTemplate.verify();
        dbTemplate.resolveURL();
        return Connect.establishConnection(dbTemplate);
    }
     // redundaten code verkuerzen
    @Override
    public Connection connectToDb(DbType type, String dbname, String user, String password) throws TypeUnknownException, FieldsNotSetException, RequiredParameterNotSetException, NoDriverFoundException, ClassNotFoundException {
        DbTemplate dbTemplate = fetchDbTemplate(type);
        Map<String, String> fields = new HashMap<>();
        fields.put("User", user);
        fields.put("Password", password);
        fields.put("Database", dbname);
        fields.put("Server", "localhost");
        fields.put("Port", String.valueOf(type.getDefaultPort()));
        dbTemplate.setFields(fields);
        dbTemplate.verify();
        dbTemplate.resolveURL();
        return Connect.establishConnection(dbTemplate);
    }

    @Override
    public Connection connectToDb(DbType type, String dbname) throws TypeUnknownException, NoDriverFoundException, ClassNotFoundException, FieldsNotSetException, RequiredParameterNotSetException {
        DbTemplate dbTemplate = fetchDbTemplate(type);
        Map<String, String > fields = new HashMap<>();
        fields.put("Database", dbname);
        fields.put("Server", "localhost");
        fields.put("Port", String.valueOf(type.getDefaultPort()));
        dbTemplate.getProperties().put("forceUrlShort", "true");
        dbTemplate.setFields(fields);
        dbTemplate.verify();
        dbTemplate.resolveURL();
        return Connect.establishConnection(dbTemplate);
    }

    @Override
    public DbTemplate fetchDbTemplate(DbType type) throws TypeUnknownException{
        DbTemplate dbTemplate = new DbTemplate(new Properties());
        switch (type){
            case MYSQL:
                dbTemplate.getProperties().put("name", "MySQL");
                dbTemplate.getProperties().put("fields", "User, Password, Database, Server, Port");
                dbTemplate.getProperties().put("url", "jdbc:mysql://Server:Port/Database?user=User&password=Password");
                dbTemplate.getProperties().put("urlShort", "jdbc:mysql://Server:Port/Database");
                break;
            case ORACLE:
                dbTemplate.getProperties().put("name", "Oracle");
                dbTemplate.getProperties().put("fields", "User, Password, Database, Server, Port");
//                dbTemplate.getProperties().put("url", "jdbc:mysql://Server:Port/Database?user=User&password=Password");
//                dbTemplate.getProperties().put("urlShort", "jdbc:mysql://Server:Port/Database");
                break;
            case MSSQL:
                dbTemplate.getProperties().put("name", "MSSSQL");
                dbTemplate.getProperties().put("fields", "User, Password, Database, Server, Port");
//                dbTemplate.getProperties().put("url", "jdbc:mysql://Server:Port/Database?user=User&password=Password");
//                dbTemplate.getProperties().put("urlShort", "jdbc:mysql://Server:Port/Database");
                break;
            case POSTGRESQL:
                dbTemplate.getProperties().put("name", "PostgreSQL");
                dbTemplate.getProperties().put("fields", "User, Password, Database, Server, Port");
//                dbTemplate.getProperties().put("url", "jdbc:mysql://Server:Port/Database?user=User&password=Password");
//                dbTemplate.getProperties().put("urlShort", "jdbc:mysql://Server:Port/Database");
                break;
            case DB2:
                dbTemplate.getProperties().put("name", "DB2");
                dbTemplate.getProperties().put("fields", "User, Password, Database, Server, Port");
//                dbTemplate.getProperties().put("url", "jdbc:mysql://Server:Port/Database?user=User&password=Password");
//                dbTemplate.getProperties().put("urlShort", "jdbc:mysql://Server:Port/Database");
                break;
            default:
                throw new TypeUnknownException(type);
        }
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
}
