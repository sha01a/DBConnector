package com.dbconnector.exceptions;

import com.dbconnector.model.DbType;

/**
 * Created by choko-dm on 19.02.2016.
 */
public class TypeUnknownException extends Exception {

    private DbType wrongType;

    public TypeUnknownException(DbType type){
        this.wrongType = type;
    }

    @Override
    public String toString(){
        return "You used a connectToDb function that only supports some default DB Types. Your Input was" + this.wrongType + "\n" +
                "Possible types are: MYSQL, ORACLE, MSSQL, POSTGRESQL, DB2.";

    }
}
