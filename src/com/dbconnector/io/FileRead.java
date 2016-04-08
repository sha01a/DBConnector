package com.dbconnector.io;

import com.dbconnector.exceptions.RequiredParameterNotSetException;
import com.dbconnector.model.DbTemplate;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by Dmitry Chokovski on 16.09.2015.
 */
public class FileRead {
    // Reads all .properties file from given direcroty and returns a list of according DbTemplates
    public static Map<String, DbTemplate> readDbList(String pathOfDir) throws IOException {

        List<DbTemplate> dbList = new ArrayList<>();
        Map<String,DbTemplate> dbListHandle = new HashMap<>();

        // Iteration over all files in given Directory
        Files.walk(Paths.get(pathOfDir)).forEach(filePath -> {
            // If found .properties File (no directories included in check)
            if (Files.isRegularFile(filePath) && filePath.getFileName().toString().endsWith(".properties")) {
                try {
                    Properties currentProperties = new Properties();
                    currentProperties.load(Files.newBufferedReader(filePath));
                    dbList.add(new DbTemplate(currentProperties));

                } catch (Exception e) {
                    e.printStackTrace();
                }
                ;
                // Output to check what files were handled - TESTING ONLY
                // System.out.println(filePath.getParent());
                // System.out.println(filePath.getFileName());
            }
        });

        // Maps DbLists to a Handle represented by "name" property
        for(int i=0; i< dbList.size(); i++){
            dbListHandle.put(dbList.get(i).getProperties().getProperty("name"), dbList.get(i));
        }

        return dbListHandle;
    }
}

//
//
//
//        //FileReader reader = new FileReader(path);
//        //BufferedReader bufferedReader = new BufferedReader(reader);
//        String line;
//        boolean foundDB=false;
//       // List<DbTemplate> dbList = new ArrayList<>();
//        String buffer = "";
//        while ((line = bufferedReader.readLine()) != null) {
//            if(line.trim().startsWith("#") || line.trim().startsWith("!")) continue;
//
//            if(line.trim().startsWith("db{")){
//                foundDB = true;
//                buffer = "";
//            }
//
//            if(line.trim().startsWith("}")){
//                foundDB = false;
//                StringReader bufferReader = new StringReader(buffer);
//                Properties bufferProperties = new Properties();
//                bufferProperties.load(bufferReader);
//                dbList.add(new DbTemplate(bufferProperties));
//            }
//
//            if(foundDB && !line.startsWith("db{")){
//                buffer = buffer + "\n" + line;
//            }
//        }
//        //reader.close();
//        return dbList;
//    }
