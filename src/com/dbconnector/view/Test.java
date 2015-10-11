package com.dbconnector.view;

import com.dbconnector.exceptions.NoDriverFoundException;
import com.dbconnector.io.*;

import java.io.IOException;
import java.net.URL;

/**
 * Created by shaola on 17.09.2015.
 *
 * Test class / Sandbox.
 *
 */
public class Test {
    public static void main(String[] args) throws IOException, NoDriverFoundException {
        URL testLoc = new URL("http://shaola.de/driver1");
        System.out.println(Downloader.downloadDriver(testLoc).getName());
    }
}


// Path to properties file: "C:/Users/shaola/IdeaProjects/DBConnector/src/com/dbconnector/io/dblist.properties"

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
