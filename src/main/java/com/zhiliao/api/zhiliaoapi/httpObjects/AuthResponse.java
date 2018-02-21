package com.zhiliao.api.zhiliaoapi.httpObjects;

public class AuthResponse {
    private Integer id;
    private String mobile;
    private String token;
    private String name;

    public AuthResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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

    @Override
    public String toString() {
        return "AuthResponse{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
