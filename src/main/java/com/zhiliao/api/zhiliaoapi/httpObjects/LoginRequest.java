package com.zhiliao.api.zhiliaoapi.httpObjects;

public class LoginRequest {
    private String mobile;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
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

    @Override
    public String toString() {
        return "LoginRequest{" +
                "mobile='" + mobile + '\'' +
                '}';
    }
}
