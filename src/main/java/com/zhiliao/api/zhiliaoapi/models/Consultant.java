package com.zhiliao.api.zhiliaoapi.models;

import java.io.Serializable;

public class Consultant implements Serializable {
    private Integer id;
    private String mobile;
    private String password;
    private String name;

    public Consultant() {
    }

    public Consultant(Integer id, String mobile, String password, String name) {
        this.id = id;
        this.mobile = mobile;
        this.password = password;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Consultant{" +
                "id=" + id +
                ", mobile='" + mobile + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
