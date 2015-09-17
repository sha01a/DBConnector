package com.dbconnector.model;

import java.util.*;

/**
 * Created by Dmitry Chokovski
 */
public class DbTemplate {

    private String name;
    private List<String> fields;
    private List<String> params;


    //Full Constructor
    public DbTemplate(String name, List<String> fields, List<String> params) {
        this.name = "DefaultName";
        this.fields = null;
        this.params = null;
    }

    // Minimal Constructor
    public DbTemplate(){
    }

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return this.name;
    }

    public List<String> getFields(){
        return this.fields;
    }

    public List<String> getParams(){
        return this.params;
    }

    public void addField(String field){
        if(this.fields==null) this.fields = new ArrayList<>();
        this.fields.add(field);
    }

    public void addParam(String param){
        if(this.params==null) this.params = new ArrayList<>();
        this.params.add(param);
    }

}
