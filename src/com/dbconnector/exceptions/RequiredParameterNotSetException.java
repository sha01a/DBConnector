package com.dbconnector.exceptions;

import com.dbconnector.model.DbTemplate;

/**
 * Created by Dmitry Chokovski on 09.11.2015.
 */
public class RequiredParameterNotSetException extends Exception {

    private String fileWithError;
    private String missingParameter;

    // Unused
    public RequiredParameterNotSetException(String file, String parameter){
        this.fileWithError = file;
        this.missingParameter = parameter;
    }

    public RequiredParameterNotSetException(String parameter){
        this.missingParameter = parameter;
    }

    @Override
    public String toString() {
        return "A required Parameter "+ this.missingParameter + " was not found in the properties or has a empty value assigned! This Connection Template will not be usable.";
    }

    public String getMissingParameter() {
        return this.getMissingParameter();
    }

    // Unused
    public void setFile(String pathToFile){
        this.fileWithError = pathToFile;
    }

    // Unused
    public void setMissingParameter(String parameter){
        this.missingParameter = parameter;
    }

}
