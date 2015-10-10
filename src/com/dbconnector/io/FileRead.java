package com.dbconnector.io;

import com.dbconnector.model.DbTemplate;
import com.dbconnector.model.DescVal;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
        boolean foundDB=false;
        List<DbTemplate> dbList = new ArrayList<>();
        DbTemplate current = new DbTemplate();
        String buffer = "";
        while ((line = bufferedReader.readLine()) != null) {
            if(line.trim().startsWith("#") || line.trim().startsWith("!")) continue;

            if(line.trim().startsWith("db{")){
                foundDB = true;
                buffer = "";
            }

            if(line.trim().startsWith("}")){
                foundDB = false;
                StringReader bufferReader = new StringReader(buffer);
                Properties bufferProperties = new Properties();
                bufferProperties.load(bufferReader);
                dbList.add(new DbTemplate(bufferProperties));
            }

            if(foundDB && !line.startsWith("db{")){
                buffer = buffer + "\n" + line;
            }
        }
        reader.close();
        return dbList;
    }



}
