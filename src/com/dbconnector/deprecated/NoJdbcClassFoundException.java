package com.dbconnector.deprecated;

/**
 * Created by shaola on 11.10.2015.
 */
public class NoJdbcClassFoundException extends Exception {

    public NoJdbcClassFoundException(){}

    @Override
    public String toString() {
        return "No JDBC Class definition was found for the given Driver!";
    }
}
