package com.example.controllers;

public class dataModel {

    //Variables
    String Category  ;
    String Name ;
    String  Amount;
    String Date ;
    Integer Id;

    //Constructor for the class dataModel
    public dataModel(Integer Id,String Category, String Name, String Amount, String Date) {
        this.Id=Id;
        this.Category = Category;
        this.Name = Name;
        this.Amount = Amount;
        this.Date = Date;
    }

    //Getters and Setters
    public String getCategory() {
        return Category;
    }


    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getAmount() {
        return Amount;
    }
    public void setAmount(String amount) {
        Amount = amount;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
}
