package com.dbconnector.model;

import java.util.*;

/**
 * Created by Dmitry Chokovski
 */
public class DbTemplate {

    private String name;
    private List<DescVal> fields;
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

    public List<DescVal> getFields(){
        return this.fields;
    }

    public List<String> getParams(){
        return this.params;
    }

    public void addDescr(String descr){
        if(this.fields==null) this.fields = new ArrayList<>();
        DescVal dv = new DescVal();
        dv.setDescr(descr);
        this.fields.add(dv);
    }

    public void addParam(String param){
        if(this.params==null) this.params = new ArrayList<>();
        this.params.add(param);
    }

    public void resolveParams(){
        for(int i=0; i<params.size(); i++){
            for(DescVal current : this.fields){
                String temp = params.get(i).replaceAll(current.getDescr(), current.getValue());
                params.set(i, temp);
            }
        }
    }

}
