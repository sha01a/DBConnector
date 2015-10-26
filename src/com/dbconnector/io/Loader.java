package com.dbconnector.io;


import com.dbconnector.model.DbTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Driver;
import java.util.jar.JarFile;


/**
 * Created by Dmitry Chokovski on 11.10.2015.
 *
 * Class used for dynamic class loading from JDBC Drivers
 *
 */


public class Loader {

    public void loadDriverClass(DbTemplate dbTemplate, File jar) throws ClassNotFoundException, IOException {
        JarFile jarFile = new JarFile(jar);

        URL [] paths = new URL[1];
        paths[0] = new URL(jar.getPath());

        URLClassLoader classLoader = new URLClassLoader (paths);

        //Class classToLoad = Class.forName ("com.MyClass", true, child);
        if(dbTemplate.getProperties().getProperty("driverClass") != null){
            Class driver = Class.forName(dbTemplate.getProperties().getProperty("driverClass"));

        } else {
            if(jarFile.getManifest().getEntries().containsKey("Main-Class")) {
                String mainClassName = jarFile.getManifest().getEntries().get("Main-Class").toString();
            } else {
                throw new ClassNotFoundException();
            }
        }
    }
}
