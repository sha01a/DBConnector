package com.dbconnector.test;

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
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Map;

/**
 * Created by Dmitry Chokovski
 */
public class GuiTest {

 private final String annotation = "Please select the desired database connection template: ";

 private Map<String, DbTemplate> configs;

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
  label2Data.bottom = new FormAttachment(dbselector, 25, SWT.BOTTOM);
  label2Data.left = new FormAttachment(1);
  label2Data.right = new FormAttachment(99);
  label2.setLayoutData(label2Data);



  // Creating buttons
  Button connectBtn = new Button(shell, SWT.PUSH);
  Button quitBtn = new Button(shell, SWT.PUSH);
  // Adjusting buttons
  connectBtn.setText("&Connect");
  quitBtn.setText("&Quit");
  //Button Layout data
  FormData connectData = new FormData(80, 30);
  connectData.right = new FormAttachment(98);
  connectData.bottom = new FormAttachment(98);
  connectBtn.setLayoutData(connectData);
  FormData quitData = new FormData(80, 30);
  quitData.right = new FormAttachment(connectBtn, -5, SWT.LEFT);
  quitData.bottom = new FormAttachment(connectBtn, 0, SWT.BOTTOM);
  quitBtn.setLayoutData(quitData);

  shell.pack();

  // Function selector
  dbselector.addListener(SWT.Selection, event -> listFields(dbselector));

  // Function Connect Button
  connectBtn.addSelectionListener(new SelectionAdapter() {
   @Override
   public void widgetSelected(SelectionEvent e) {
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

 // Function Selector
 private void listFields(Combo dbselector) {
  String sel = dbselector.getText();

 }

 private void populateSelection() {

 }

 private void init(){
  try {
   APIFunctions api = new APIFunctions();
   Path currentRelativePath = Paths.get("");
   String here = currentRelativePath.toAbsolutePath().toString();
   configs = api.readConfigs("C:/Users/choko-dm/Documents/IntelliJ Projects/DBConnector/properties");
  } catch (Exception e) {
   e.printStackTrace();
   System.exit(0);
  }

 }



 @SuppressWarnings("unused")
 public static void main(String[] args) {
  Display display = new Display();
  GuiTest ex = new GuiTest(display);
  display.dispose();
  }
 }
