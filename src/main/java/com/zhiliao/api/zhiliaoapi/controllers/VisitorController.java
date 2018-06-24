package com.zhiliao.api.zhiliaoapi.controllers;

import com.zhiliao.api.zhiliaoapi.httpObjects.visitor.CreateVisitorRequest;
import com.zhiliao.api.zhiliaoapi.httpObjects.visitor.CreateVisitorResponse;
import com.zhiliao.api.zhiliaoapi.models.Visitor;
import com.zhiliao.api.zhiliaoapi.services.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/visitors")
public class VisitorController {
    @Autowired
    private VisitorService visitorService;

    @GetMapping("/{id}")
    public ResponseEntity<Visitor> findVisitor(@PathVariable("id") int id) {
        Visitor visitor = visitorService.findVisitor(id);
        return new ResponseEntity<>(visitor, OK);
    }

    @GetMapping()
    public ResponseEntity<List<Visitor>> getVisitors() {
        int userId = 100;
        List<Visitor> visitors = visitorService.findMyVisitors(userId);
        return new ResponseEntity<List<Visitor>>(visitors, OK);
    }

    @PostMapping()
    public ResponseEntity<CreateVisitorResponse> createVisitor(@RequestBody CreateVisitorRequest visitor) {
        int id = visitorService.createVisitor(visitor);
        CreateVisitorResponse visitorCreated = new CreateVisitorResponse(id);
        return new ResponseEntity<CreateVisitorResponse>(visitorCreated, CREATED);
    }
}
