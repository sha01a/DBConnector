package com.dbconnector.exceptions;

/**
 * Created by Dmitry Chokovski on 09.11.2015.
 */
public class RequiredParameterNotSetException extends Exception {

    public String fileWithError;
    public String missingParameter;

    public RequiredParameterNotSetException(String file, String parameter){
        this.fileWithError = file;
        this.missingParameter = parameter;
    }

    @Override
    public String toString() {
        return "A required Parameter was not found in the .properties File or has a empty value assigned! This Connection Template will not be usable.";
    }

    public void setFile(String pathToFile){
        this.fileWithError = pathToFile;
    }

    public void setMissingParameter(String parameter){
        this.missingParameter = parameter;
    }

}
