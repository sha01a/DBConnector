package com.dbconnector.exceptions;

/**
 * Created by shaola on 30.11.15.
 */
public class FieldsNotSetException extends Exception {

    public FieldsNotSetException(){}

    @Override
    public String toString() {
        return "Connection Information has not been filled into the fields!";
    }
}

