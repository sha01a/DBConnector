package com.dbconnector.io;

import com.dbconnector.model.DbTemplate;
import com.dbconnector.model.DriverHolder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Dmitry Chokovski
 *
 * Class used for dynamic class loading from JDBC Drivers
 *
 */

public class Loader {

    public static Driver loadDriverClass(DbTemplate dbTemplate, File jar) throws ClassNotFoundException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, SQLException {
        URLClassLoader classLoader = null;
        JarFile jarFile = null;
        Driver dr = null;

        try {
            classLoader = new URLClassLoader(new URL[] { jar.toURI().toURL() }, System.class.getClassLoader());
            jarFile = new JarFile(jar);
            Enumeration<JarEntry> e = jarFile.entries();

            // Loading class given in Properties
            if (dbTemplate.getProperties().getProperty("driverClass") != null) {
                while (e.hasMoreElements()) {
                    JarEntry je = e.nextElement();
                    if(je.isDirectory() || !je.getName().endsWith(".class")){
                        continue;
                    }
                    // -6 because of .class
                    String className = je.getName().substring(0,je.getName().length()-6);
                    className = className.replace('/', '.');
                    Class c = null;
                    System.out.println(className);
                    c = classLoader.loadClass(className);
                }
                Class cl = classLoader.loadClass(dbTemplate.getProperties().getProperty("driverClass"));
                dr = (Driver) cl.newInstance();
                DriverManager.registerDriver(new DriverHolder(dr));
            } else { // If no class given loading set Main class of .jar Package
                if (jarFile.getManifest().getEntries().containsKey("Main-Class")) {
                    String mainClassName = jarFile.getManifest().getMainAttributes().getValue("Main-Class");
                    Class cl = classLoader.loadClass(mainClassName);
                    dr = (Driver) cl.newInstance();
                    DriverManager.registerDriver(new DriverHolder(dr));
                } else {
                    throw new ClassNotFoundException();
                }
            }
        } finally {
            if (classLoader != null) {
                classLoader.close();
            }
            if (jarFile != null) {
                jarFile.close();
            }
        }
        return dr;
    }
}
