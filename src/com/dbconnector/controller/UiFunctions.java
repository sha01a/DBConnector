package com.dbconnector.controller;

import com.dbconnector.net.Connect;
import com.dbconnector.exceptions.FieldsNotSetException;
import com.dbconnector.exceptions.NoDriverFoundException;
import com.dbconnector.model.DbTemplate;

import java.sql.Connection;

/**
 * Created by shaola on 11.10.2015.
 */
public class UiFunctions {

    public static Connection connect(DbTemplate template) throws NoDriverFoundException, ClassNotFoundException, FieldsNotSetException {

        // Getting Values from Input

        // Resolving URL
        template.resolveURL();

        // Creating Connectiion object
        return Connect.establishConnection(template);
    }
}
