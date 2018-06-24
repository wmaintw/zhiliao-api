package com.zhiliao.api.zhiliaoapi.httpObjects.visitor;

import java.util.Date;

public class CreateVisitorRequest {
    private String realName;
    private String gender;
    private Date dob;
    private int age;
    private String mobile;

    public CreateVisitorRequest() {
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "CreateVisitorRequest{" +
                "realName='" + realName + '\'' +
                ", gender='" + gender + '\'' +
                ", dob=" + dob +
                ", age=" + age +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
