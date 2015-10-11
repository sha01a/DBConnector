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
    // Reads dblist.properties file and returns the text-based settings as DbTemplates
    public static List<DbTemplate> readDbList(String path) throws IOException {
        FileReader reader = new FileReader(path);
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
