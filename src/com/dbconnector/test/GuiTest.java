package com.dbconnector.test;

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
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Map;



/**
 * Created by Dmitry Chokovski
 */
public class GuiTest {

    private final String annotation = "Please select the desired database connection template: ";

    private Map<String, DbTemplate> configs;
    APIFunctions api = new APIFunctions();
    DbTemplate current;

    public GuiTest(Display display) {

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

        // Creating Composite to hold Fields
        Composite fieldspace;




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
                String sel = dbselector.getText();
                System.out.print(sel);
                current = configs.get(sel);
                Map<String,String> fields = current.getFields();
                Composite replacement = new Composite(shell, SWT.NONE);
                GridLayout grid = new GridLayout();
                grid.numColumns = 2;
                grid.makeColumnsEqualWidth = true;
                switch (sel){
                    case "MySQL (default)":
                        System.out.print("YOYO");
                        break;
                    case "Oracle (default)":

                        break;
                    case "MS-SQL (default)":

                        break;
                    case "PostgreSQL (default)":

                        break;
                    case "DB2 (default)":

                        break;
                    default:
                        int i = 0;
                        for (String name : fields.keySet()){
                            Label label = new Label(replacement, SWT.LEFT);
                            label.setText(name);
                            Text textfield = new Text(replacement, SWT.SINGLE);
                        }
                }
                FormData fieldspaceData = new FormData();
                fieldspaceData.top = new FormAttachment(label2, 10, SWT.BOTTOM);
                replacement.setLayoutData(fieldspaceData);
                shell.layout();
            }
        });
        // Function Connect Button
        connectBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                try {
                    Map<String,String> fields = current.getFields();
                    current.setFields(fields);
                    Connection conn = api.connectToDb(current);
                    MessageBox messageBox = new MessageBox(shell, SWT.ICON_WORKING);
                    messageBox.setMessage("Connection successful!");
                } catch (NoDriverFoundException ex) {
                    MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR);
                    messageBox.setMessage(ex.toString());
                } catch (RequiredParameterNotSetException ex) {
                    MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR);
                    messageBox.setMessage(ex.toString());
                } catch (FieldsNotSetException ex) {
                    MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR);
                    messageBox.setMessage(ex.toString());
                } catch (SQLException ex) {
                    MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR);
                    messageBox.setMessage("SQLException: " + ex.getMessage() + "\n" + "SQLState: " + ex.getSQLState() + "\n" + "VendorError: " + ex.getErrorCode());
                } catch (ClassNotFoundException ex) {
                    MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR);
                    messageBox.setMessage("Correct class counldn't be found automatically in the Driver jar. Please specify a classname in your configuration!");
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

    public static void main(String[] args) {
        Display display = new Display();
        GuiTest ex = new GuiTest(display);
        display.dispose();
    }

    private void init(){
        try {
            Path currentRelativePath = Paths.get("");
            String here = currentRelativePath.toAbsolutePath().toString();
            configs = api.readConfigs("/Users/shaola/IntellijProjects/DBConnector/properties");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

    }
}
