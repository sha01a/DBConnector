package com.dbconnector.io;

import com.dbconnector.model.DbTemplate;
import com.dbconnector.model.DescVal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaola on 16.09.2015.
 */
public class FileRead {

/**     Testing ONLY
 *      public static void main(String [] args) throws IOException {
        List<DbTemplate> test = readDbList("");
        DbTemplate test1 = test.get(0);
        System.out.println(test1.getName());
        System.out.println("---------------------------");
        test1.getFields().get(0).setValue("172.0.0.1");
        test1.getFields().get(1).setValue("WarDB");
        test1.getFields().get(2).setValue("666");
        test1.getFields().get(3).setValue("user");
        test1.getFields().get(4).setValue("pwd");
        test1.resolveParams();
        for(String p : test1.getParams()){
            System.out.println(p);
        }
    }
*/
    // Reads dblist.txt file and exports the text-based settings into DbTemplates
    public static List<DbTemplate> readDbList(String path) throws IOException {
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
                        current.addDescr(f);
                    }
                }
                if(line.trim().startsWith("param=")){
                    current.addParam(line.trim().replaceFirst("param=", ""));
                }
                if(line.trim().startsWith("driver=")){
                    current.setDriverPath(line.trim().replaceFirst("driver=", ""));
                }
                if(line.trim().startsWith("forcedriver=")){
                    if((line.trim().replaceFirst("forcedriver=","")).contains("true")) {
                        current.forceDriver();
                    }
                }
            }
        }
        reader.close();
        return dbList;
    }



}
