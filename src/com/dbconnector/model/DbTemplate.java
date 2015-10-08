package com.dbconnector.model;

import java.util.*;

/**
 * Created by Dmitry Chokovski
 *
 * Internal representation of Database connection details, bundled for each database type.
 */
public class DbTemplate {

    private String name; // Name of Template (e.g. MySQL, MySQL w/o Port, Oracle etc.)
    private List<DescVal> fields; // Pairs of Values - Description + Value
    private List<String> params;
    private String driverPath;
    private boolean forcedDriver;


    //Full Constructor
    public DbTemplate(String name, List<String> fields, List<String> params, boolean forcedDriver) {
        this.name = "DefaultName";
        this.forcedDriver = forcedDriver;
        this.fields = null;
        this.params = null;
    }

    // Minimal Constructor
    public DbTemplate(){
        this.forcedDriver = false;
    }

    public void setName(String name){
        this.name=name;
    }

    // Getter Methods
    public String getName(){ return this.name; }

    public List<DescVal> getFields(){ return this.fields; }

    public List<String> getParams(){
        return this.params;
    }

    public String getDriverPath() { return this.driverPath; }

    public boolean getForcedDriver() { return this.forcedDriver; }

    // Setter Methods

    public void forceDriver(){
        this.forcedDriver=true;
    }

    public void setDriverPath(String driverPath){
        this.driverPath=driverPath;
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
