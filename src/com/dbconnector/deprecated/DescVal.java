package com.dbconnector.deprecated;

/**
 * Created by shaola on 17.09.2015.
 */
public class DescVal {
    private String descr;
    private String value;

    // Minimal constructor
    public DescVal(){
    }

    public String getDescr(){
        return this.descr;
    }

    public void setDescr(String descr){
        this.descr=descr;
    }

    public String getValue(){
        return this.value;
    }

    public void setValue(String value){
        this.value=value;
    }


}
