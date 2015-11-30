package com.dbconnector.external;
import com.dbconnector.*;
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

    /* Parse & Connect */

    public Connection parseAndConnectToDb(String pathOfPropertiesDirectory, String nameOfDb);

    /* Reading Properties and Creating DbTemplates */

    public Map readConfigs(String pathOfPropertiesDirectory) throws IOException;

    /* Connection functions */

    // Connection via DbTemplates - Please make sure DbTemplate.fields hold connection values!
    public Connection connectToDb(Map dbTemplateMap, String dbName);

    public Connection connectToDb(DbTemplate dbTemplate) throws IOException;

    public Connection connectToDb(Properties properties, Map<String,String> fields);

    // Database connection for pre-defined DB Types
    public Connection connectToDb(DbType type, String dbname, String user, String password, String port);

    public Connection connectToDb(DbType type, String dbname, String user, String password);

    public Connection connectToDb(DbType type, String dbname);


    /* Additional functions */

    // Manually populate fields
    public void manualPopulateFields(DbTemplate dbTemplate);

    // Shows all available Drivers
    public String listDrivers();


}
