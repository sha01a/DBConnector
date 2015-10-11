package com.dbconnector.gui;

import com.dbconnector.io.*;
import com.dbconnector.model.DbTemplate;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;

import com.dbconnector.model.DbTemplate;

/**
 * Created by shaola on 17.09.2015.
 */
public class Test {
    public static void main(String[] args) throws IOException {
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
