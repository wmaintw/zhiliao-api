package com.zhiliao.api.zhiliaoapi.models;

import java.util.Date;

public class Visitor {
    private int id;
    private String realName;
    private String gender;
    private Date dob;
    private int age;
    private String nationality;
    private String mobile;
    private String address;
    private int consultantId;

    public Visitor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(int consultantId) {
        this.consultantId = consultantId;
    }

    @Override
    public String toString() {
        return "Visitor{" +
                "id=" + id +
                ", realName='" + realName + '\'' +
                ", gender='" + gender + '\'' +
                ", dob=" + dob +
                ", age=" + age +
                ", nationality='" + nationality + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                ", consultantId=" + consultantId +
                '}';
    }
}
