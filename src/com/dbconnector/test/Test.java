package com.dbconnector.test;

import com.dbconnector.exceptions.FieldsNotSetException;
import com.dbconnector.exceptions.NoDriverFoundException;
import com.dbconnector.external.APIFunctions;

import java.io.IOException;

/**
 * Created by Dmitry Chokovski on 17.09.2015.
 *
 * Test class / Sandbox.
 *
 */
public class Test extends APIFunctions {
    public static void main(String[] args) throws IOException, NoDriverFoundException, FieldsNotSetException, ClassNotFoundException {
        //new APIFunctions().APIDemo("/Users/shaola/IntellijProjects/DBConnector/properties/");
    }
}


//        // Iteration over all files in given Directory
//        Files.walk(Paths.get("/Users/shaola/IntellijProjects/DBConnector/properties/")).forEach(filePath -> {
//            // If found .properties File (no directories included in check)
//            if (Files.isRegularFile(filePath) && filePath.getFileName().toString().endsWith(".properties")) {
//                // Output to check what files were handled - TESTING ONLY
//                System.out.println(filePath.getParent());
//                System.out.println(filePath.getFileName());
//            }
//        });
//    }

//        URL testLoc = new URL("http://shaola.de/driver1");
//        System.out.println(Downloader.downloadDriver(testLoc).getName());

// Path to properties file: "C:/Users/shaola/IdeaProjects/DBConnector/properties/mysql.properties"

//        List<DbTemplate> dbList = FileRead.readDbList("");
//        DbTemplate db = dbList.get(0);
//        db.createFields();
//        for (Map.Entry<String,String> entry : db.getFields().entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//            System.out.println(key + " : " + value);
//        }



//        URL wbs = new URL("http://shaola.de/driver1");
//        System.out.println(wbs.getFile());
//        Display display = new Display();
//        Shell shell = new Shell(display);
//
//        Label label = new Label(shell, SWT.NONE);
//        label.setText("HELLO WORLD");
//
//        shell.pack();
//        shell.open();
//
//        while (!shell.isDisposed()){
//            if(!display.readAndDispatch()){
//                display.sleep();
//            }
//        }
//        display.dispose();
//        System.out.print("AC"+"AB");
//    }
//}
