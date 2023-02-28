package com.DataBase;

import java.sql.*;




public class DataBaseRead {
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

    public static int get_table_length(Connection Connect, String TableName) {
        try {
            Statement statement = Connect.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM " + TableName);
            int n = 1;
            while (result.next())
                n++;
            return (n-1);
        } catch (Exception ex) {
            System.out.println("error while counting rows");
            return (0);
        }
    }

    public static String[] Read_Table_Rows_Of(Connection Connect, String TableName, String ColumnName, int TableLength) {
        try {
            Statement statement = Connect.createStatement();
            ResultSet result = statement.executeQuery("SELECT " + ColumnName + " FROM " + TableName);
            String[] Table = new String[TableLength];
            int i = 0;
            while (result.next()) {
                Table[i] = result.getString(ColumnName);
                i++;
            }
            System.out.println("reading information about " + ColumnName + " was successful");
            return (Table);
        } catch (SQLException e) {
            System.out.println("failed to read information about " + ColumnName);
            return (new String[]{"failed to read a row"});
        }

    }

    public static int GetColumnsCount(Connection connect, String TableName) {
        int columnsCount = 0;
        try {
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Category,Name,Amount,Date FROM " + TableName);
            ResultSetMetaData rsmd = rs.getMetaData();
            columnsCount = rsmd.getColumnCount();
        } catch (SQLException ex) {
            System.out.println("error while counting Columns");
        }
        return (columnsCount);
    }

    public static String[] GetColumnsNames(Connection connect, String TableName) {
        try {
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Category,Name,Amount,Date FROM " + TableName);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            String nameColumns[] = new String[columnCount];
            for (int i = 0; i < columnCount; i++)
                nameColumns[i] = rsmd.getColumnName(i + 1);
            return (nameColumns);
        } catch (SQLException ex) {
            System.out.println("Error while getting rows names of"+TableName);
            return ((new String[]{"Error while reading a row name"}));
        }

    }
 private static int get_table_length_by_condition(Connection connect,String TableName,String AttName,String AttValue){
        int counter_of_lignes=1;
        try {
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Category,Name,Amount,Date FROM " + TableName + " WHERE " + AttName + "='" + AttValue + "'");
            while(rs.next()){
                counter_of_lignes++;
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }
        if(counter_of_lignes==1){
            System.out.println("Table "+TableName+" is empty or the condition is not verified");
        }
        return(counter_of_lignes);
}
    public static String[][] Read_Table_Column_Of(Connection connect, String TableName, String AttName, String AttValue) {
        try {
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Category,Name,Amount,Date FROM "+TableName+" WHERE "+AttName+"='"+AttValue+"'");
            int NumberOfColumns=GetColumnsCount(connect,TableName);int NumberOfRows=get_table_length_by_condition(connect,TableName,AttName,AttValue);
            String columnsNames[]=GetColumnsNames(connect,TableName);
            String infos[][]=new String[NumberOfRows][NumberOfColumns];

            for(int p=0;p<NumberOfColumns;p++)
                infos[0][p]=columnsNames[p];

            int i=0;int j=0;
           while(rs.next()) {
               i++;
               for(j=0;j<NumberOfColumns;j++)
                   infos[i][j]=rs.getString(j + 1);
           }
           return(infos);
        } catch (SQLException ex) {
            System.out.println(ex);
            return(new String[][]{{"failed","to load infos"}});
        }
    }

    public static String[][] read_table_infos(Connection connect, String TableName) {
        int NumberOfRows = DataBaseRead.get_table_length(connect, TableName)+1;
        int NumberOfColumns = DataBaseRead.GetColumnsCount(connect, TableName);
        if ((NumberOfRows > 1) && (NumberOfColumns > 1)) {
            String Columns[][] = new String[NumberOfRows][NumberOfColumns];
            String Vecteurn[] = new String[NumberOfRows];
            String Vecteurj[] = new String[NumberOfColumns];
            Vecteurj = DataBaseRead.GetColumnsNames(connect, TableName);
            for (int j = 0; j < NumberOfColumns; j++) {
                Columns[0][j] = Vecteurj[j];
            }
            for (int j = 0; j < NumberOfColumns; j++) {
                Vecteurn = DataBaseRead.Read_Table_Rows_Of(connect, TableName, Columns[0][j], NumberOfRows);
                for (int i = 1; i < NumberOfRows; i++)
                    Columns[i][j] = Vecteurn[i - 1];
            }
            return Columns;
        } else {
            System.out.println("Table "+TableName+" is empty or you don t have permission to access");
            return (new String[][]{{"the table is empty or unacceptable", "failed to read information"}});
        }
    }

    public static void print_table(Connection connect,String TableName,String DBName){               //for just testing
        connect=connect(DBName,"root","");
        String[][] infos = read_table_infos(connect,TableName);
        for(int j=1;j<infos.length;j++) {
            System.out.println("--------------------");
            for (int i = 0; i < infos[0].length; i++) {
                System.out.println(infos[0][i]+"="+infos[j][i]);
            }
        }
    }

}
