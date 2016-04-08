package com.dbconnector.external;
import com.dbconnector.*;
import com.dbconnector.exceptions.FieldsNotSetException;
import com.dbconnector.exceptions.NoDriverFoundException;
import com.dbconnector.exceptions.RequiredParameterNotSetException;
import com.dbconnector.exceptions.TypeUnknownException;
import com.dbconnector.model.DbTemplate;
import com.dbconnector.model.DbType;

import java.io.IOException;
import java.sql.Connection;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;


/**
 * Created by Dmitry Chokovski on 30.11.15.
 *
 *
 * This interface defines the functions that a API user can use in his/her software.
 *
 */

public interface DbConnectorAPI {

    /* 1 - Reading Properties and Creating DbTemplates */

    public Map readConfigs(String pathOfPropertiesDirectory) throws IOException;

    /* 2 - Connection functions */

    // Connection via DbTemplates
    public Connection connectToDb(Map<String, DbTemplate> dbTemplateMap, String dbName) throws NoDriverFoundException, ClassNotFoundException, FieldsNotSetException, RequiredParameterNotSetException ;

    public Connection connectToDb(DbTemplate dbTemplate) throws NoDriverFoundException, ClassNotFoundException, FieldsNotSetException, RequiredParameterNotSetException ;

    public Connection connectToDb(Properties properties, Map<String,String> fields) throws NoDriverFoundException, ClassNotFoundException, FieldsNotSetException, RequiredParameterNotSetException;

     /* 1+2 - Connection functions with default configs for pre-defined types */

    // Full Information needed
    public Connection connectToDb(DbType type, String dbname, String user, String password, String host, int port) throws TypeUnknownException, FieldsNotSetException, RequiredParameterNotSetException, NoDriverFoundException, ClassNotFoundException;

    // Assume localhost for host name
    public Connection connectToDb(DbType type, String dbname, String user, String password, int port) throws TypeUnknownException, NoDriverFoundException, ClassNotFoundException, FieldsNotSetException, RequiredParameterNotSetException;

    // Assume default port (MYSQL: 3306, ORACLE:1521, MSSQL:1433, POSTGRESQL: 5432, DB2: 50000)
    public Connection connectToDb(DbType type, String dbname, String user, String password, String host) throws TypeUnknownException, FieldsNotSetException, RequiredParameterNotSetException, NoDriverFoundException, ClassNotFoundException;


    public Connection connectToDb(DbType type, String dbname, String user, String password) throws TypeUnknownException, FieldsNotSetException, RequiredParameterNotSetException, NoDriverFoundException, ClassNotFoundException;

    public Connection connectToDb(DbType type, String dbname) throws TypeUnknownException, NoDriverFoundException, ClassNotFoundException, FieldsNotSetException, RequiredParameterNotSetException;

    /* Support Functions */

    // Gets DbTemplate for selected default DbType

    public DbTemplate fetchDbTemplate(DbType type) throws TypeUnknownException, RequiredParameterNotSetException;


    /* Testing / Debugging functions */

    // Manually populate fields by console input
    public void manualPopulateFields(DbTemplate dbTemplate);


}
