package com.dbconnector.io;


import com.dbconnector.model.DbTemplate;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    public static void loadDriverClass(DbTemplate dbTemplate, File jar) throws ClassNotFoundException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        JarFile jarFile = new JarFile(jar);

        URL [] paths = new URL[1];
        paths[0] = new URL("jar:file://" + jar.getAbsolutePath() + "!/");

        URLClassLoader classLoader = new URLClassLoader (paths);
        Class cl = null;

        // Loading class given in Properties
        if(dbTemplate.getProperties().getProperty("driverClass") != null){
            classLoader.loadClass(dbTemplate.getProperties().getProperty("driverClass"));
        } else { // If no class given loading set Main class of .jar Package
            if(jarFile.getManifest().getEntries().containsKey("Main-Class")) {
                String mainClassName = jarFile.getManifest().getMainAttributes().getValue("Main-Class");;
                classLoader.loadClass(mainClassName);
            } else {
                throw new ClassNotFoundException();
            }
        }
    }
}
