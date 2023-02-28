package com.DataBase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class DataBaseSignUp{

    public static String TestColumnsNames(Connection connect, String TableName){
        try {
            String ColumnsNames[] = DataBaseReadUsers.GetColumnsNames(connect, TableName);
            Statement statement = connect.createStatement();
            String ColumnsValues = "(";String Vergule=",";
            ColumnsValues=ColumnsValues+ColumnsNames[0];
            for (int i = 1; i < DataBaseReadUsers.GetColumnsCount(connect, TableName); i++) {
                ColumnsValues =  ColumnsValues + ","+ ColumnsNames[i];
            }
            ColumnsValues=ColumnsValues+")";
            return(ColumnsValues);
        }catch(SQLException ex){
            System.out.println(ex);
            return(null);
        }
    }
    public static String TestValues(String[] PValues){
        String ColumnsValues = "('";
        String Vergule = "',";
        ColumnsValues = ColumnsValues + PValues[0]+"'";
        for (int i = 1; i < PValues.length; i++) {
            ColumnsValues = ColumnsValues + ",'" + PValues[i]+"'";
        }
        ColumnsValues=ColumnsValues+")";
        return(ColumnsValues);
    }

    //Inserts into Table in Database
    public static void insert(Connection connect, String TableName, String[] PValues) {
        String Columns = TestColumnsNames(connect, TableName);
        String Values = TestValues(PValues);
        try {
            Statement statement = connect.createStatement();
            statement.executeUpdate("INSERT INTO " + TableName + " " + Columns + " VALUES " + Values + ";");
            System.out.println("INSERTION IN " + TableName + " COMPLETED");
        } catch (SQLException EX) {
            System.out.println(EX);
        }
    }

    /*
    public static void create(Connection connect,String TableName1){
        try{
            Statement statement = connect.createStatement();
            statement.executeUpdate("CREATE TABLE "+TableName1+"(Id int(255), )");
        } catch (SQLException EX){

        }
    }

     */

}

