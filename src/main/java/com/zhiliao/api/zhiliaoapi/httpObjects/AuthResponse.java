package com.zhiliao.api.zhiliaoapi.httpObjects;

public class AuthResponse {
    private boolean loggedIn = false;
    private String token;
    private String name;
    private Integer id;
    private String mobile;

    public AuthResponse() {
    }

    public AuthResponse(boolean loggedIn, String token, String name, Integer id) {
        this.loggedIn = loggedIn;
        this.token = token;
        this.name = name;
        this.id = id;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
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

    @Override
    public String toString() {
        return "AuthResponse{" +
                "loggedIn=" + loggedIn +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", mobile='" + mobile + '\'' +
                '}';
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
