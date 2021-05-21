package com.example.bidding.model;


/**
 * This class represent the exception in the form of Problem POJO for better understating of exception
 */
public class Problem {

    private int status;
    private String title;
    private String message;

    public Problem(int status, String title, String message) {
        this.status = status;
        this.title = title;
        this.message = message;
    }
    public Problem(){}

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
