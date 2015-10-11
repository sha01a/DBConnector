package com.dbconnector.controller;

import com.dbconnector.connect.Connect;
import com.dbconnector.model.DbTemplate;

import java.sql.Connection;

/**
 * Created by shaola on 11.10.2015.
 */
public class UiFunctions {

    public static Connection connect(DbTemplate template){

        // Getting Values from Input

        // Resolving URL
        template.resolveURL();

        // Creating Connectiion object
        return Connect.connectToDB(template);
    }
}
