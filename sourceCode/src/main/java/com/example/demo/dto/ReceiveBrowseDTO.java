package com.example.demo.dto;

public class ReceiveBrowseDTO {
    private String consignee;
    private String recLocation;
    private String phoneNumber;

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getRecLocation() {
        return recLocation;
    }

    public void setRecLocation(String recLocation) {
        this.recLocation = recLocation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
