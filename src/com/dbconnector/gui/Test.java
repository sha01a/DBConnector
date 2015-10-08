package com.dbconnector.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * Created by shaola on 17.09.2015.
 */
public class Test {
    public static void main(String [] args){
        Display display = new Display();
        Shell shell = new Shell(display);

        Label label = new Label(shell, SWT.NONE);
        label.setText("HELLO WORLD");

        shell.pack();
        shell.open();

        while (!shell.isDisposed()){
            if(!display.readAndDispatch()){
                display.sleep();
            }
        }
        display.dispose();
        System.out.print("AC"+"AB");
    }
}
