package com.alaska.securitylearn.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TaskController {

    @GetMapping("/tasks")
    public ResponseEntity<String> allTasks() {
        return new ResponseEntity<String>("all tasks", HttpStatus.OK);
    }
}
