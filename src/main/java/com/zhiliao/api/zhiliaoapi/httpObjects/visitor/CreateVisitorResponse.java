package com.zhiliao.api.zhiliaoapi.httpObjects.visitor;

public class CreateVisitorResponse {
    private int id;

    public CreateVisitorResponse() {
    }

    public CreateVisitorResponse(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CreateVisitorResponse{" +
                "id=" + id +
                '}';
    }
}
