package com.dbconnector.exceptions;

/**
 * Created by Dmitry Chokovski on 11.10.2015.
 */
public class NoDriverFoundException extends Exception{

    public NoDriverFoundException(){}

    @Override
    public String toString() {
        return "No Driver location was given when required!";
    }
}
