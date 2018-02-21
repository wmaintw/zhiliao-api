package com.zhiliao.api.zhiliaoapi.controllers;

import com.zhiliao.api.zhiliaoapi.httpObjects.InfoResponse;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DebugController {
    @GetMapping("/info")
    public InfoResponse getInfo() {
        InfoResponse infoResponse = new InfoResponse();
        infoResponse.setStatus("up!");
        infoResponse.setDateTime(new DateTime().toString());
        return infoResponse;
    }
}
