package com.example.elina.application.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Equipment {
    private String image_url;
    private String type;
    private String name;
    private String number;
    private String prodectionYear;
    private String location;
    private String nameResponsible;

    private @ServerTimestamp Date timeStamp;

    private String equip_id;
    private String user_id;

    public Equipment() {
    }

    public Equipment(String image_url, String type, String name, String number, String prodectionYear, String location, String nameResponsible, String equip_id, String user_id) {
        this.image_url = image_url;
        this.type = type;
        this.name = name;
        this.number = number;
        this.prodectionYear = prodectionYear;
        this.location = location;
        this.nameResponsible = nameResponsible;
        this.equip_id = equip_id;
        this.user_id = user_id;
    }

    public Equipment(String type, String name, String number, String prodectionYear, String location, String nameResponsible, Date timeStamp, String equip_id, String user_id) {
        this.type = type;
        this.name = name;
        this.number = number;
        this.prodectionYear = prodectionYear;
        this.location = location;
        this.nameResponsible = nameResponsible;
        this.timeStamp = timeStamp;
        this.equip_id = equip_id;
        this.user_id = user_id;
    }

    public Equipment(String type, String name, String number, String prodectionYear, String location, String nameResponsible, String equip_id, String user_id) {
        this.type = type;
        this.name = name;
        this.number = number;
        this.prodectionYear = prodectionYear;
        this.location = location;
        this.nameResponsible = nameResponsible;
        this.equip_id = equip_id;
        this.user_id = user_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProdectionYear() {
        return prodectionYear;
    }

    public void setProdectionYear(String prodectionYear) {
        this.prodectionYear = prodectionYear;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNameResponsible() {
        return nameResponsible;
    }

    public void setNameResponsible(String nameResponsible) {
        this.nameResponsible = nameResponsible;
    }

    public String getEquip_id() {
        return equip_id;
    }

    public void setEquip_id(String equip_id) {
        this.equip_id = equip_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
