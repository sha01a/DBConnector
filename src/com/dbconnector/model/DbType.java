package com.dbconnector.model;

/**
 * Created by Dmitry Chokovski
 *
 * This Enum defines some default DB Types that can be used without any .properties files configured
 *
 */

public enum DbType {
    MYSQL(3306),
    ORACLE(1521),
    MSSQL(1433),
    POSTGRESQL(5432),
    DB2(50000);

    private final int defaultPort;

    DbType(int defaultPort) {
        this.defaultPort = defaultPort;
    }

    public int getDefaultPort() {
        return defaultPort;
    }
}
