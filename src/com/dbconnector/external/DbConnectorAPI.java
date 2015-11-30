package com.dbconnector.external;
import com.dbconnector.*;
import com.dbconnector.exceptions.FieldsNotSetException;
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

    /* All in One - DEMO */
    // NOTE: This function is as a console-based demonstration of how this API should be integrated!

    public Connection APIDemo(String pathOfPropertiesDirectory) throws IOException, FieldsNotSetException;

    /* Reading Properties and Creating DbTemplates */

    public Map readConfigs(String pathOfPropertiesDirectory) throws IOException;

    /* Connection functions */

    // Connection via DbTemplates
    public Connection connectToDb(Map dbTemplateMap, String dbName);

    public Connection connectToDb(DbTemplate dbTemplate) throws IOException;

    public Connection connectToDb(Properties properties, Map<String,String> fields);

    // Database connection for pre-defined DB Types
    public Connection connectToDb(DbType type, String dbname, String user, String password, String port);

    public Connection connectToDb(DbType type, String dbname, String user, String password);

    public Connection connectToDb(DbType type, String dbname);


    /* Testing / Debugging functions */

    // Manually populate fields by console input
    public void manualPopulateFields(DbTemplate dbTemplate);

    // Shows all available Drivers
    public void listDrivers();


}
