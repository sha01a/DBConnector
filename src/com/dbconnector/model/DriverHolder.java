package com.dbconnector.model;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by Dmitry Chokovski on 25.04.16.
 *
 * This class holds a newly loaded Driver so that registration takes place.
 * See notes at http://www.kfu.com/~nsayer/Java/dyn-jdbc.html
 */
public class DriverHolder implements Driver {
    private Driver driver;

    public DriverHolder(Driver d) {
        this.driver = d;
    }
    public boolean acceptsURL(String u) throws SQLException {
        return this.driver.acceptsURL(u);
    }
    public Connection connect(String u, Properties p) throws SQLException {
        return this.driver.connect(u, p);
    }
    public int getMajorVersion() {
        return this.driver.getMajorVersion();
    }
    public int getMinorVersion() {
        return this.driver.getMinorVersion();
    }
    public DriverPropertyInfo[] getPropertyInfo(String u, Properties p) throws SQLException {
        return this.driver.getPropertyInfo(u, p);
    }
    public boolean jdbcCompliant() {
        return this.driver.jdbcCompliant();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return this.driver.getParentLogger();
    }

}
