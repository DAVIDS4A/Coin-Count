package com.DataBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.controllers.dataModel;
public class DatabaseDisplay {

    Connection conn = null;

    //Establishes connection to Database
    public static Connection connect(String DataBaseName, String UserName, String Password) {
        Connection connect = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost/" + DataBaseName, UserName, Password);
            System.out.println("Connection succeed");
            return (connect);
        } catch (Exception ex) {
            System.out.println("Connection error");
            return (connect);
        }
    }

    //Retrieves Information from Table in Database, fills it in list and returns list
    public static ObservableList<dataModel> getDatausers(){
        Connection conn = connect("coin count", "root", "");
        ObservableList<dataModel> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from income");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                list.add(new dataModel(Integer.parseInt(rs.getString("Id")),rs.getString("Category"),rs.getString("Name"), rs.getString("Amount"), rs.getString("Date")));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static String getBalance(){
        Connection conn = connect("coin count", "root", "");
        int balance=0;
        try {
            PreparedStatement ps = conn.prepareStatement("select Category,Name,Amount,Date from income");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                balance=balance+Integer.parseInt(rs.getString("Amount"));
            }
        } catch (Exception e) {
        }
        return Integer.toString(balance);
    }
}
