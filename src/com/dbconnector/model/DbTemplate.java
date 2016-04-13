package com.dbconnector.model;

import com.dbconnector.exceptions.FieldsNotSetException;
import com.dbconnector.exceptions.RequiredParameterNotSetException;

import java.lang.ref.ReferenceQueue;
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
    private boolean authStatus = false;
    private String username;
    private String password;
    private boolean fieldsDefined;

    private final List<String> requiredParams = Arrays.asList("name", "url");


    // Minimal Constructor  - not in use
    public DbTemplate() {
    }

    // Constructor for FileRead
    public DbTemplate(Properties properties) throws RequiredParameterNotSetException {
        this.fieldsDefined = false;
        this.properties = properties;
        if (properties.containsKey("authentication")){
            if (properties.getProperty("authentication") == "true" ){ this.authStatus = true; }
        }
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

    public void setUsername(String user) {
        this.username = user;
    }

    public void setPassword(String pwd) {
        this.username = pwd;
    }

    public Properties getProperties(){
//        if(this.properties == null){
//            this.properties = new Properties();
//        }
        return this.properties;
    }

    public boolean getAuthStatus(){
        return this.authStatus;
    }

    public String getUsername() {
        return (String) this.username;
    }

    public String getPassword() { return (String) this.password; }

    public Map<String, String> getFields() { return this.fields; }

    public void setFields(Map<String, String> fields) { this.fields = fields; }

    public void createFields() throws RequiredParameterNotSetException{
        try {
            if (! this.getProperties().containsKey("requiredFields")) {
                throw new RequiredParameterNotSetException("requiredFields");
            }
        } catch (RequiredParameterNotSetException e) {
            return;
        }
        for (String s : this.getProperties().getProperty("requiredFields").split(",")){
            s.trim();
            if(this.fields == null) this.fields = new HashMap<String, String>();
            this.fields.put(s, null);
        }
    }

    public void verify() throws RequiredParameterNotSetException, FieldsNotSetException{
        for(String param : requiredParams){
            if(!(this.properties.containsKey(param) && !this.properties.getProperty(param).equals(null))){
                this.unReady();
                throw new RequiredParameterNotSetException(param);
            }
        }
        if(this.properties.containsKey("requiredFields")){
            List<String> requiredFields = Arrays.asList(this.properties.getProperty("requiredFields").split(","));
            for (String field : requiredFields){
                field = field.trim();
                if(!this.fields.containsKey(field) || !this.fields.get(field).equals(null)){
                    this.unReady();
                    throw new FieldsNotSetException();
                }
            }
        }
        if (this.properties.containsKey("forceDriver")){
            if (this.properties.getProperty("driver") == null) {
                this.unReady();
                throw new RequiredParameterNotSetException("driver");
            }
        }
        this.ready();
    }

    public void resolveURL() throws FieldsNotSetException{
        if(this.isReady() == false) throw new FieldsNotSetException();
        for(Map.Entry<String,String> entry : this.fields.entrySet()) {
            this.properties.setProperty("url", this.properties.getProperty("url").replaceAll(entry.getKey(), entry.getValue()));
            if (this.properties.containsKey("urlShort")){
                this.properties.setProperty("urlShort", this.properties.getProperty("urlShort").replaceAll(entry.getKey(), entry.getValue()));
            }
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
