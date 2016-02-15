package com.dbconnector.test;

import com.dbconnector.net.Connect;
import com.dbconnector.exceptions.FieldsNotSetException;
import com.dbconnector.exceptions.NoDriverFoundException;
import com.dbconnector.io.FileRead;
import com.dbconnector.model.DbTemplate;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Dmitry Chokovski on 20.10.15.
 *
 * Test method to test full API functionality by simulation Use-Case: Read properties-Files, Download driver,
 * load class, establish connection and return Connection-Object.
 *
 */


public class FullAPITest {

    public static void main(String args []) throws IOException, NoDriverFoundException, ClassNotFoundException, FieldsNotSetException {

        // Reading Properties and creating DbTemplates, then putting them into map, Key = name-property
        Map<String, DbTemplate> templates = FileRead.readDbList("/Users/shaola/IntellijProjects/DBConnector/properties");

        // Extracting Template for testing
        DbTemplate testTemplate = templates.get("mysql");

        //Filling with connection parameters to test-db
        testTemplate.getFields().put("User", "admin");
        testTemplate.getFields().put("Server", "192.168.1.1");
        testTemplate.getFields().put("Database", "testdb");
        testTemplate.getFields().put("Password", "qwerty");

        // Resolving URL by substituting field names with above params
        testTemplate.resolveURL();

        // Establishing connection to DB
        Connect.establishConnection(testTemplate);
    }


}
