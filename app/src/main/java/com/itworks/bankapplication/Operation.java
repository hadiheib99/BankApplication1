package com.itworks.bankapplication;

import java.io.Serializable;
import java.util.Date;

public class Operation implements Serializable {
    private String name;
    private int amount;
    private String description;

    private String date;
    private static int id;

    public Operation(int id,String name, int value,String description,String date) {
        this(name,value,date);
        this.description = description;

        this.id = id;

    }
    public Operation(String name,int value,String date)
    {
        this.date=date;
        this.name=name;
        this.amount = value;
       // this.isIncome = isIncome;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Operation() {
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int value) {

        this.amount = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIncome() {
        return this.amount>0;
    }



    public static int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String stringIsIncome(){
        if(isIncome()){
            return "income";
        }
        return "expense";

    }
    @Override
    public String toString() {
        return "Operation{" +
                "name='" + name + '\'' +
                ", value=" + amount +
                ", description=" + description +
                ", date=" + date +
                ", id=" + id +
                '}';
    }
}
