package com.dbconnector.test;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

/**
 * Created by Dmitry Chokovski
 */
public class Debug {


    public void listDrivers() {
        Enumeration driverList = DriverManager.getDrivers();
        while (driverList.hasMoreElements()) {
            Driver driverClass = (Driver) driverList.nextElement();
            System.out.println("Driver: " + driverClass.getClass().getName());
        }
    }

    public void listDriversToString() {

    }


}
