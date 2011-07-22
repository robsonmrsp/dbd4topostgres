/*
 * Db4topostgresApp.java
 */
package dbd4topostgres;

import javax.swing.JFrame;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class Dbd4topostgresApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {
        
        
        

    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override
    protected void configureWindow(java.awt.Window root) {
        
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of Db4topostgresApp
     */
    public static Dbd4topostgresApp getApplication() {
        
        return Application.getInstance(Dbd4topostgresApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        // launch(Db4topostgresApp.class, args);

    }
}
