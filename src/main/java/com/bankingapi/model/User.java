package com.bankingapi.model;

public class User {
    private String userid;
    private String toDate;
    private String fromDate;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "userid='" + userid + '\'' +
                ", toDate='" + toDate + '\'' +
                ", fromDate='" + fromDate + '\'' +
                '}';
    }

    public User() {
    }

    public User(String userid, String toDate, String fromDate) {
        this.userid = userid;
        this.toDate = toDate;
        this.fromDate = fromDate;
    }
}
