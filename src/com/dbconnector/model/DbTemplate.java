package com.dbconnector.model;

import com.dbconnector.exceptions.FieldsNotSetException;

import java.util.*;

/**
 * Created by Dmitry Chokovski
 *
 * Internal representation of Database connection details, bundled for each database type.
 *
 */
public class DbTemplate {

    private Properties properties;
    private Map<String, String> fields;
    private boolean fieldsDefined;


    // Minimal Constructor
    public DbTemplate() {
    }

    // Constructor for FileRead
    public DbTemplate(Properties properties) {
        this.fieldsDefined = false;
        this.properties = properties;
        this.createFields();
    }

    public void ready(){
        this.fieldsDefined = true;
    }

    public void unReady(){
        this.fieldsDefined = false;
    }

    public boolean isReady(){
        return this.fieldsDefined;
    }

    public Properties getProperties(){
        return this.properties;
    }

    public Map<String, String> getFields(){
        return this.fields;
    }

    public void createFields(){
        int i = 0;
        for (String s : this.getProperties().getProperty("fields").split(",")){
            s.trim();
            if(this.fields == null) this.fields = new HashMap<String, String>();
            this.fields.put(s, "default" + i);
            i++;
        }
        this.getProperties().remove("fields");
    }

    public void resolveURL() throws FieldsNotSetException{
        if(this.isReady() == false) throw new FieldsNotSetException();
        for(Map.Entry<String,String> entry : this.fields.entrySet()) {
            this.properties.setProperty("url", this.properties.getProperty("url").replaceAll(entry.getKey(), entry.getValue()));
        }
    }


}


//    private String name; // Name of Template (e.g. MySQL, MySQL w/o Port, Oracle etc.)
//    private List<DescVal> fields; // Pairs of Values - Description + Value
//    private List<String> params;
//    private String driverPath;
//    private boolean forcedDriver;

//    public void setName(String name){
//        this.name=name;
//    }
//
//    // Getter Methods
//    public String getName(){ return this.name; }
//
//    public List<DescVal> getFields(){ return this.fields; }
//
//    public List<String> getParams(){
//        return this.params;
//    }
//
//    public String getDriverPath() { return this.driverPath; }
//
//    public boolean getForcedDriver() { return this.forcedDriver; }
//
//    // Setter Methods
//
//    public void forceDriver(){
//        this.forcedDriver=true;
//    }
//
//    public void setDriverPath(String driverPath){
//        this.driverPath=driverPath;
//    }
//
//    public void addDescr(String descr){
//        if(this.fields==null) this.fields = new ArrayList<>();
//        DescVal dv = new DescVal();
//        dv.setDescr(descr);
//        this.fields.add(dv);
//    }
//
//    public void addParam(String param){
//        if(this.params==null) this.params = new ArrayList<>();
//        this.params.add(param);
//    }
//
//    public void resolveParams(){
//        for(int i=0; i<params.size(); i++){
//            for(DescVal current : this.fields){
//                String temp = params.get(i).replaceAll(current.getDescr(), current.getValue());
//                params.set(i, temp);
//            }
//        }
//    }
//
//}
