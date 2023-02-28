package com.DataBase;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import com.DataBase.DataBaseRead;

public class statistics extends Application {
    public static int getDayOfMonth(Date aDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(aDate);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void start(Stage stage) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        int month = now.getMonthValue();
        int month2 = month - 1;
        int month3 = month - 2;
        if (month2 == 0) {
            month2 = 12;
        }
        if (month3 == 0) {
            month3 = 12;
        } else if (month3 == -1) {
            month3 = 11;
        }

        Connection connect = DataBaseRead.connect("coin count", "root", "");
        String Infos[][] = DataBaseRead.read_table_infos(connect, "income");
        //DataBaseRead.print_table(connect,"income","coin count");

        String sDate;
        int length=Infos.length;
        int Value = 0;
        int monthE[] = new int[3];
        int monthS[] = new int[3];
        int Categories[]=new int[4];
        int monthij = 0;
        int dayij=0;
        int remainingdays=0;
        int remainingweeks=0;
        int d=1;
        Date dates[]=new Date[length-1];
        for (int i = 0; i < 3; i++) {
            monthE[i] = 0;
            monthS[i] = 0;
        }
        for (int i = 0; i < 4; i++) {
            Categories[i]=0;
        }
        for (int i = 0; i < Infos.length; i++) {
            if(i!=0)
                dates[i-1]=new SimpleDateFormat("yyyy-MM-dd").parse(Infos[i][4]);
            for (int j = 0; j < Infos[0].length; j++) {
                if ((j == 4) && (i != 0)) {
                    try {
                        sDate = Infos[i][j];
                        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(sDate);
                        dayij=getDayOfMonth(date);monthij=date.getMonth()+1;System.out.println("Monthij=========================="+monthij);
                        System.out.println("Day:"+dayij);
                        remainingdays=30-dayij;
                        remainingweeks=remainingdays/7;
                        Value = Integer.parseInt(Infos[i][3]);
                        System.out.println(Infos[i][1]);
                        if(Infos[i][1].contains("Weekly")){
                            Value=Value*remainingweeks;
                        }else if(Infos[i][1].contains("Daily")){
                            Value=Value*remainingdays;
                        }
                        System.out.println("Amount:TEEEEESSSTTTT-"+Value+"-------------");
                        if (monthij == month) {
                            if (Value < 0) {
                                monthS[0] = monthS[0] + Value * (-1);
                                if(Infos[i][5].contains("Bills")){
                                    Categories[0]+=Value*(-1);
                                }else if(Infos[i][5].contains("Entertainments")){
                                    Categories[1]+=Value*(-1);
                                }else if(Infos[i][5].contains("Health")){
                                    Categories[2]+=Value*(-1);
                                }else if(Infos[i][5].contains("Shopping")){
                                    Categories[3]+=Value*(-1);
                                }
                            } else {
                                monthE[0] = monthE[0] + Value;
                            }
                        } else if (monthij == month2) {
                            if (Value < 0) {
                                monthS[1] = monthS[1] + Value * (-1);
                                if(Infos[i][5].contains("Bills")){
                                    Categories[0]+=Value*(-1);
                                }else if(Infos[i][5].contains("Entertainments")){
                                    Categories[1]+=Value*(-1);
                                }else if(Infos[i][5].contains("Health")){
                                    Categories[2]+=Value*(-1);
                                }else if(Infos[i][5].contains("Shopping")){
                                    Categories[3]+=Value*(-1);
                                }
                            } else {
                                monthE[1] = monthS[1] + Value;
                            }
                        } else if (monthij == month3) {
                            if (Value < 0) {
                                if(Infos[i][5].contains("Bills")){
                                    Categories[0]+=Value*(-1);
                                }else if(Infos[i][5].contains("Entertainments")){
                                    Categories[1]+=Value*(-1);
                                }else if(Infos[i][5].contains("Health")){
                                    Categories[2]+=Value*(-1);
                                }else if(Infos[i][5].contains("Shopping")){
                                    Categories[3]+=Value*(-1);
                                }
                                monthS[2] = monthS[2] + Value * (-1);
                            } else {
                                monthE[2] = monthS[2] + Value;
                            }
                        }
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        for(int i=0;i<length-1;i++){
            System.out.println("date======="+dates[i]);
        }

        Arrays.sort(dates);

        System.out.println("dates array after sort:");
        for(int i=0;i<length-1;i++){
            System.out.println("date1======="+dates[i]);
        }

        int Values[]=new int[length-1];


        for (int i = 0; i < 3; i++) {
            System.out.println(monthE[i]);
            System.out.println(monthS[i]);
        }
        int somme=0;
        for (int i = 0; i < 4; i++){
            System.out.println("CT:--------------"+Categories[i]);
            somme+=Categories[i];
        }
        System.out.println("somme="+somme);


        String monthString1 = new DateFormatSymbols().getMonths()[month - 1];
        String monthString2 = new DateFormatSymbols().getMonths()[month2 - 1];
        String monthString3 = new DateFormatSymbols().getMonths()[month3 - 1];

        XYChart.Series data1 = new XYChart.Series();
        data1.setName("earned");
        data1.getData().add(new XYChart.Data(monthString3, monthE[2]));
        data1.getData().add(new XYChart.Data(monthString2, monthE[1]));
        data1.getData().add(new XYChart.Data(monthString1, monthE[0]));

        XYChart.Series data2 = new XYChart.Series();
        data2.setName("spent");
        data2.getData().add(new XYChart.Data(monthString3, monthS[2]));
        data2.getData().add(new XYChart.Data(monthString2, monthS[1]));
        data2.getData().add(new XYChart.Data(monthString1, monthS[0]));

        XYChart.Series dataline = new XYChart.Series();
        dataline.setName("Outputs");
        dataline.getData().add(new XYChart.Data(monthString3, monthE[2]-monthS[2]));
        dataline.getData().add(new XYChart.Data(monthString2,monthE[1]-monthS[1]));
        dataline.getData().add(new XYChart.Data(monthString1, monthE[0]-monthS[0]));

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Bills", Categories[0]),
                        new PieChart.Data("Entertainment", Categories[1]),
                        new PieChart.Data("Health", Categories[2]),
                        new PieChart.Data("Shopping", Categories[3])
                );



        //VIEW PART:
        //initialization of the barchart's Axis
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Last 3 Months");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Money value in dinars");
        // initialization of the linechart's Axis
        CategoryAxis xAxis1 = new CategoryAxis();
        xAxis1.setLabel("Last 3 Months");
        NumberAxis yAxis1 = new NumberAxis();
        yAxis1.setLabel("Money value in dinars");
        //initialization of the barchart
        BarChart barChart = new BarChart(xAxis, yAxis);
        //Setting Bar and Category gap and the title
        barChart.setBarGap(30);
        barChart.setCategoryGap(30);
        barChart.setTitle("Expenses and incomes");
        //Initialization of the linechart
        LineChart lineChart=new LineChart(xAxis1,yAxis1);
        lineChart.setTitle("Outputs in the 3 last months");

        //Adding data to all the charts
        barChart.getData().addAll(data1, data2);
        lineChart.getData().add(dataline);
        PieChart chart = new PieChart(pieChartData);
        //Setting piechart's title
        chart.setTitle("Expenses in the 3 last months");

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 600,700);
        root.getChildren().addAll(chart,lineChart,barChart);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

}