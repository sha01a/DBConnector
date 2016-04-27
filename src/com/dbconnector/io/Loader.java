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
        Driver dr = null;
        JarFile jarFile = new JarFile(jar);

        // Load .jar - hack
        URL url = jar.toURI().toURL();
        URLClassLoader cL = (URLClassLoader)ClassLoader.getSystemClassLoader();
        Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        method.setAccessible(true);
        method.invoke(cL, url);

        try {
            // Loading class given in Properties
            if (dbTemplate.getProperties().getProperty("driverClass") != null) {
                dr = (Driver) Class.forName(dbTemplate.getProperties().getProperty("driverClass"), true, cL).newInstance();
                DriverManager.registerDriver(new DriverHolder(dr));
            } else { // If no class given loading set Main class of .jar Package
                if (jarFile.getManifest().getEntries().containsKey("Main-Class")) {
                    String mainClassName = jarFile.getManifest().getMainAttributes().getValue("Main-Class");
                    dr = (Driver) Class.forName(mainClassName, true, classLoader).newInstance();
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
