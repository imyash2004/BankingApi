package com.bankingapi.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    @CsvBindByName(column = "email")
    private String email;
    @CsvBindByName(column = "Date")
    private String date;
    @CsvBindByName(column = "Amount")
    private String amount;

    public String getUserId() {
        return email;
    }

    public void setUserId(String userId) {
        this.email = userId;
    }

    public Date  getDate() throws ParseException {
        SimpleDateFormat d= new SimpleDateFormat("dd/MM/yyyy");
        return d.parse(date);
    }

    public void setDate(Date date) {
        this.date = String.valueOf(date);
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Transaction(String userId, String date, String amount) {
        this.email = userId;
        this.date = date;
        this.amount = amount;
    }

    public Transaction() {
    }
}
