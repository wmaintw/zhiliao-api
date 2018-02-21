package com.zhiliao.api.zhiliaoapi.httpObjects;

public class InfoResponse {
    private String status;
    private String dateTime;

    public InfoResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "InfoResponse{" +
                "status='" + status + '\'' +
                ", dateTime='" + dateTime + '\'' +
                '}';
    }
}
