package com.dbconnector.connect;

import com.dbconnector.model.DbTemplate;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.List;
import java.util.Properties;

/**
 * Created by shaola on 18.09.15.
 */
public class Connect {

    public static void connectToDB(DbTemplate db){
        db.resolveParams();
        Connection conn = null;
        try {
            int paramCount = db.getParams().size();
            Driver driver = DriverManager.getDriver(db.getParams().get(0));
            if(driver == null){
                //download driver?
                //DriverManager.registerDriver(newDriver);
            }
            if(paramCount==1){
                conn = DriverManager.getConnection(db.getParams().get(0));
            }
            else if(paramCount==3){
                conn = DriverManager.getConnection(db.getParams().get(0), db.getParams().get(1), db.getParams().get(2));
            }
            else{
                throw new Exception();
            }

        } catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return;
        } catch (Exception e){
            System.err.println("Parameter count error, please check your dblist.txt!" +
                    "\n Allowed formats are: " +
                    "\n param=[ConnectionString] " +
                    "\n OR" +
                    "\n param=[ConnectionString]" +
                    "\n param=[User]" +
                    "\n param=[Password]");
        }
    }
}
