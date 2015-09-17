package com.dbconnector.io;

import com.dbconnector.model.DbTemplate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaola on 16.09.2015.
 */
public class FileRead {

    public static void main(String [] args) throws IOException {
        readDbList();
    }

    public static List<DbTemplate> readDbList() throws IOException {
        FileReader reader = new FileReader("C:/Users/shaola/IdeaProjects/DBConnector/src/com/dbconnector/io/dblist.txt");
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        boolean dbStatus=false;
        List<DbTemplate> dbList = new ArrayList<>();
        DbTemplate current = new DbTemplate();
        while ((line = bufferedReader.readLine()) != null) {
            if(line.trim().startsWith("##")) continue;
            if(line.contains("db{") && dbStatus==false) {
                dbStatus=true;
                current = new DbTemplate();
            }
            if(line.contains("}") && dbStatus==true) {
                dbStatus=false;
                dbList.add(current);
            }

            if(dbStatus){
                if(line.trim().startsWith("name=")){
                    current.setName(line.trim().replaceFirst("name=", ""));
                }
                if(line.trim().startsWith("fields=")){
                    for(String f : line.trim().replaceFirst("fields=", "").split(",")) {
                        current.addField(f);
                    }
                }
                if(line.trim().startsWith("param=")){
                    current.addParam(line.trim().replaceFirst("param=", ""));
                }
            }
        }
        reader.close();


        for(DbTemplate dbt : dbList){
            System.out.println(dbt.getName());
            for(String f : dbt.getFields()){
                System.out.println(f);
            }
            for(String p : dbt.getParams()){
                System.out.println(p);
            }
            System.out.println("---------------------------");
        }

        return dbList;
    }



}
