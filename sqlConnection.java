package cedocpackage;

import java.awt.HeadlessException;
import java.sql.*;
import javax.swing.*;

public class sqlConnection {
    Connection conn=null;
    static String dbURL="jdbc:mysql://localhost:3306/cedoc?zeroDateTimeBehavior=CONVERT_TO_NULL";
    static String username="root";
    static String password="";
    public static Connection dbconnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn =DriverManager.getConnection(dbURL,username,password);
            return conn;
	}catch(HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
	}
    }   
}

