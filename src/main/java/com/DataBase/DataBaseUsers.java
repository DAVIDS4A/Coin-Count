package com.DataBase;
import java.sql.*;

public class DataBaseUsers {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 3306;
    public static final String DB_NAME = "coin count";

    public static Connection con;

    static {
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public static int checkLogin (String username , String password){
        Connection con = DataBaseUsers.con;
        if (con == null) return -1;
        String sql ="SELECT username, password FROM users WHERE username=? AND password=?";
        try{
            PreparedStatement prest = con.prepareStatement(sql);
            prest.setString(1, username);
            prest.setString(2, password);

            ResultSet rs = prest.executeQuery();
            while (rs.next()) { return 0;}




        } catch (SQLException se) {
            se.printStackTrace();
        }
        return 1;
    }
}
