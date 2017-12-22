package Digilinc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Premnath Borkar
 */
public class MainClass {
    
    static ResultSet rs;
    static Statement st;
    static int valid;
    static Connection conn;
    static String exp;
    public static void main(String[] args) {
        // TODO code application logic here
        
        try {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/digilinc","root","");
        
    } catch (SQLException | ClassNotFoundException ex ) {
        Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Server is not connected");
        
    }
        
        System.out.println("GO to Login");
        LoginFrameGUI log = new LoginFrameGUI();
        log.setVisible(true);
        
    }
}
