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
import java.sql.SQLException;
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
    public Connection connectToDb(Map<String, DbTemplate> dbTemplateMap, String dbName) throws NoDriverFoundException, ClassNotFoundException, FieldsNotSetException, RequiredParameterNotSetException, SQLException {
        DbTemplate dbTemplate = dbTemplateMap.get(dbName);
        dbTemplate.verify();
        dbTemplate.resolveURL();
        Connection connectionObject = Connect.establishConnection(dbTemplate);
        return connectionObject;
    }

    @Override
    public Connection connectToDb(DbTemplate dbTemplate) throws NoDriverFoundException, ClassNotFoundException, FieldsNotSetException, RequiredParameterNotSetException, SQLException {
        dbTemplate.verify();
        dbTemplate.resolveURL();
        Connection connectionObject = Connect.establishConnection(dbTemplate);
        return connectionObject;
    }

    @Override
    public Connection connectToDb(Properties properties, Map<String, String> fields) throws NoDriverFoundException, ClassNotFoundException, FieldsNotSetException, RequiredParameterNotSetException, SQLException {
        DbTemplate dbTemplate = new DbTemplate(properties);
        dbTemplate.setFields(fields);
        dbTemplate.verify();
        dbTemplate.resolveURL();
        Connection connectionObject = Connect.establishConnection(dbTemplate);
        return connectionObject;
    }

    @Override
    public Connection connectToDb(DbType type, String dbname, String user, String password, String host, int port) throws TypeUnknownException, FieldsNotSetException, RequiredParameterNotSetException, NoDriverFoundException, ClassNotFoundException, SQLException {
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
    public Connection connectToDb(DbType type, String dbname, String user, String password, int port) throws TypeUnknownException, NoDriverFoundException, ClassNotFoundException, FieldsNotSetException, RequiredParameterNotSetException, SQLException {
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
    public Connection connectToDb(DbType type, String dbname, String user, String password, String host) throws TypeUnknownException, FieldsNotSetException, RequiredParameterNotSetException, NoDriverFoundException, ClassNotFoundException, SQLException {
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
    public Connection connectToDb(DbType type, String dbname, String user, String password) throws TypeUnknownException, FieldsNotSetException, RequiredParameterNotSetException, NoDriverFoundException, ClassNotFoundException, SQLException {
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
    public Connection connectToDb(DbType type, String dbname) throws TypeUnknownException, NoDriverFoundException, ClassNotFoundException, FieldsNotSetException, RequiredParameterNotSetException, SQLException {
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
    public DbTemplate fetchDbTemplate(DbType type) throws TypeUnknownException, RequiredParameterNotSetException{
        DbTemplate dbTemplate = new DbTemplate(new Properties());
        switch (type){
            case MYSQL:
                dbTemplate.getProperties().put("name", "MySQL (default)");
                dbTemplate.getProperties().put("fields", "Server, Port, Database, User, Password");
                dbTemplate.getProperties().put("url", "jdbc:mysql://Server:Port/Database?user=User&password=Password");
                break;
            case ORACLE:
                dbTemplate.getProperties().put("name", "Oracle (default)");
                dbTemplate.getProperties().put("fields", "Server, Port, SID, User, Password");
                dbTemplate.getProperties().put("url", "jdbc:oracle:thin:User/Password@Server:Port:SID");
                break;
            case MSSQL:
                dbTemplate.getProperties().put("name", "MS-SQL (default)");
                dbTemplate.getProperties().put("fields", "Server, Port, Database, User, Password");
                dbTemplate.getProperties().put("url", "jdbc:sqlserver://Server:Port;databaseName=Database;user=User;password=Password;");
                break;
            case POSTGRESQL:
                dbTemplate.getProperties().put("name", "PostgreSQL (default)");
                dbTemplate.getProperties().put("fields", "Server, Port, Database, User, Password");
                dbTemplate.getProperties().put("url", "jdbc:postgresql://Server:Port/Database?user=User&password=Password");
                break;
            case DB2:
                dbTemplate.getProperties().put("name", "DB2 (default)");
                dbTemplate.getProperties().put("fields", "Server, Port, Database, User, Password");
                dbTemplate.getProperties().put("url", "jdbc:db2://Server:Port/Database:user=User;password=Password");
                break;
            case SQLITE:
                dbTemplate.getProperties().put("name", "SQLite (default)");
                dbTemplate.getProperties().put("fields", "Path");
                dbTemplate.getProperties().put("url", "jdbc:sqlite:Path");
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
