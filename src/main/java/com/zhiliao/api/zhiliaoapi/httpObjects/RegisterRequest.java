package com.zhiliao.api.zhiliaoapi.httpObjects;

public class RegisterRequest {

    private String mobile;
    private String password;

    public RegisterRequest() {
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
        return "RegisterRequest{" +
                "mobile='" + mobile + '\'' +
                '}';
    }
}
