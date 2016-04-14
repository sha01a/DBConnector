package com.dbconnector.gui;

import com.dbconnector.exceptions.FieldsNotSetException;
import com.dbconnector.exceptions.NoDriverFoundException;
import com.dbconnector.exceptions.RequiredParameterNotSetException;
import com.dbconnector.exceptions.TypeUnknownException;
import com.dbconnector.external.APIFunctions;
import com.dbconnector.model.DbTemplate;
import com.dbconnector.model.DbType;
import org.eclipse.swt.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;


/**
 * Created by Dmitry Chokovski
 */
public class Mainview {

    private final static String annotation = "Please select the desired database connection template: ";

    private Map<String, DbTemplate> configs;
    Composite dynfield = null;
    APIFunctions api = new APIFunctions();
    DbTemplate current;
    boolean firstsel = true;

    public Mainview(Display display) {

        //Initialize
        init();

        // Creating shell
        Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);
        // Adjusting shell
        shell.setText("DBConnector v1.0");
        shell.setSize(450, 600);
        shell.setToolTipText("DBConnector");

        // Creating row layout
        FormLayout layout = new FormLayout();
        layout.marginLeft = 10;
        layout.marginRight = 10;
        layout.marginTop = 30;
        layout.marginBottom = 5;
        shell.setLayout(layout);

        // Creating Label
        Label label1 = new Label(shell, SWT.LEFT);
        label1.setText(annotation);
        // Label Layout Data
        FormData label1Data = new FormData();
        label1Data.bottom = new FormAttachment(0);
        label1.setLayoutData(label1Data);

        // Creating Database Selector
        Combo dbselector = new Combo(shell, SWT.READ_ONLY);
        // Pupulating it with data
        dbselector.add("MySQL (default)");
        dbselector.add("Oracle (default)");
        dbselector.add("MS-SQL (default)");
        dbselector.add("PostgreSQL (default)");
        dbselector.add("DB2 (default)");
        dbselector.add("SQLite (default)");
        for (String name : configs.keySet()) {
            dbselector.add(name);
        }
        // Selector Layout Data
        FormData dbselectorData = new FormData();
        dbselectorData.bottom = new FormAttachment(label1, 25, SWT.BOTTOM);
        dbselector.setLayoutData(dbselectorData);

        // Creating Separator after selector
        Label label2 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
        // Label Layout Data
        FormData label2Data = new FormData();
        label2Data.bottom = new FormAttachment(dbselector, 10, SWT.BOTTOM);
        label2Data.left = new FormAttachment(0);
        label2Data.right = new FormAttachment(100);
        label2.setLayoutData(label2Data);


        // Creating buttons
        Button connectBtn = new Button(shell, SWT.PUSH);
        Button quitBtn = new Button(shell, SWT.PUSH);
        // Adjusting buttons
        connectBtn.setText("&Connect");
        quitBtn.setText("&Quit");
        //Button Layout data
        FormData connectData = new FormData(80, 30);
        connectData.right = new FormAttachment(quitBtn, -5, SWT.LEFT);
        connectData.bottom = new FormAttachment(quitBtn, 0, SWT.BOTTOM);
        FormData quitData = new FormData(80, 30);
        quitData.right = new FormAttachment(98);
        quitData.bottom = new FormAttachment(98);
        connectBtn.setLayoutData(connectData);
        quitBtn.setLayoutData(quitData);

        // Function selector
        dbselector.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                Map<String,String> fields = null;
                String sel = dbselector.getText();
                boolean auth = false;
                try {
                    switch (sel) {
                        case "MySQL (default)":
                            current = api.fetchDbTemplate(DbType.MYSQL);
                            fields = current.getFields();
                            break;
                        case "Oracle (default)":
                            current = api.fetchDbTemplate(DbType.ORACLE);
                            fields = current.getFields();
                            break;
                        case "MS-SQL (default)":
                            current = api.fetchDbTemplate(DbType.MSSQL);
                            fields = current.getFields();
                            break;
                        case "PostgreSQL (default)":
                            current = api.fetchDbTemplate(DbType.POSTGRESQL);
                            fields = current.getFields();
                            break;
                        case "DB2 (default)":
                            current = api.fetchDbTemplate(DbType.DB2);
                            fields = current.getFields();
                            break;
                        case "SQLite (default)":
                            current = api.fetchDbTemplate(DbType.SQLITE);
                            fields = current.getFields();
                            break;
                        default:
                            current = configs.get(sel);
                            fields = current.getFields();
                            if (current.getAuthStatus()) auth = true;
                    }
                } catch (TypeUnknownException ex){
                    popup(shell, ex, true);
                } catch (RequiredParameterNotSetException ex){
                    popup(shell, ex, true);
                }
                if (dynfield != null) { dynfield.setVisible(false); }
                Composite replacement = makeFieldComposite(shell, fields, auth);
                FormData fieldCompositeData = new FormData();
                fieldCompositeData.top = new FormAttachment(label2, 20, SWT.BOTTOM);
                fieldCompositeData.left = new FormAttachment(0);
                fieldCompositeData.right = new FormAttachment(100);
                replacement.setLayoutData(fieldCompositeData);
                replacement.setVisible(true);
                dynfield = replacement;
                firstsel = false;
                shell.layout();
            }
        });
        // Function Connect Button
        connectBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                try {
                    if (firstsel) {
                        popup(shell, null, true);
                    } else{
                        Map<String,String> fields = current.getFields();
                        Control [] children = dynfield.getChildren();
                        boolean auth = current.getAuthStatus();
                        for (int i = 0; i<children.length; i++){
                            if (i%2 == 1) {
                                Label l = (Label) children[i-1];
                                Text t = (Text) children[i];
                                if ((auth) && i == children.length){
                                    Text t1 = (Text) children[i-2];
                                    current.setUsername(t1.getText());
                                    current.setPassword(t.getText());
                                } else {
                                    if (t.getText() != "") {
                                        fields.put(l.getText(), t.getText());
                                    } else {
                                        fields.put(l.getText(), null);
                                    }
                                }
                            }
                        }
                        for (Map.Entry val : fields.entrySet()){
                        }
                        current.setFields(fields);
                        Connection conn = api.connectToDb(current);
                        popup(shell, null, false);
                    }
                } catch (NoDriverFoundException ex) {
                    popup(shell, ex, true);
                } catch (RequiredParameterNotSetException ex) {
                    popup(shell, ex, true);
                } catch (FieldsNotSetException ex) {
                    popup(shell, ex, true);
                } catch (SQLException ex) {
                    popup(shell, ex, true);
                } catch (ClassNotFoundException ex) {
                    popup(shell, ex, true);
                }
            }
        });

        // Function Quit Button
        quitBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                shell.getDisplay().dispose();
                System.exit(0);
            }
        });

        shell.open();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    public static void popup(Shell shell, Exception ex, boolean isError){
        MessageBox mbox = null;
        if (isError) {
            if (ex instanceof SQLException) {
                SQLException ex1 = (SQLException) ex;
                mbox = new MessageBox(shell, SWT.ICON_ERROR);
                mbox.setMessage("SQLException: " + ex.getMessage() + "\n" + "SQLState: " + ex1.getSQLState() + "\n" + "VendorError: " + ex1.getErrorCode());
            }
            else if (ex instanceof ClassNotFoundException){
                ClassNotFoundException ex1 = (ClassNotFoundException) ex;
                mbox = new MessageBox(shell, SWT.ICON_ERROR);
                mbox.setMessage("Correct class counldn't be found automatically in the Driver jar. Please specify a classname in your configuration!");
            }
            else if (ex instanceof FieldsNotSetException){
                FieldsNotSetException ex1 = (FieldsNotSetException) ex;
                mbox = new MessageBox(shell, SWT.ICON_ERROR);
                mbox.setMessage(ex1.toString());
            }
            else if (ex == null){
                mbox = new MessageBox(shell, SWT.ICON_ERROR);
                mbox.setMessage(annotation.replace(":", "!"));
            }
        }
        else {
            mbox = new MessageBox(shell, SWT.ICON_WORKING);
            mbox.setMessage("Connection successful!");
        }
        mbox.open();
    }

    public static Composite makeFieldComposite(Shell shell, Map<String, String> fields, boolean auth) {
        Composite replacement = new Composite(shell, SWT.NONE);
        GridLayout grid = new GridLayout();
        grid.numColumns = 2;
        grid.makeColumnsEqualWidth = true;
        replacement.setLayout(grid);
        for (String fname : fields.keySet()) {
            Label flabel = new Label(replacement, SWT.LEFT);
            flabel.setText(fname);
            Text ftext = new Text(replacement, SWT.SINGLE);
        }
        if (auth) {
            Label flabel1 = new Label(replacement, SWT.LEFT);
            flabel1.setText("User");
            Text ftext1 = new Text(replacement, SWT.SINGLE);
            Label flabel2 = new Label(replacement, SWT.LEFT);
            flabel2.setText("Password");
            Text ftext2 = new Text(replacement, SWT.SINGLE);
        }


        return replacement;
    }

    public static void main(String[] args) {
        Display display = new Display();
        Mainview ex = new Mainview(display);
        display.dispose();
    }

    private void init(){
        try {
            Path currentRelativePath = Paths.get("");
            String here = currentRelativePath.toAbsolutePath().toString();
            // Testing Paths
            String winpath = "C:\\Users\\shaola\\IdeaProjects\\DBConnector\\properties";
            String macpath = "";
            configs = api.readConfigs(winpath);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

    }
}
