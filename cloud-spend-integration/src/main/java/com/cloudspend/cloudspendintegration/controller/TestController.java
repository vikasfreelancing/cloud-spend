package com.cloudspend.cloudspendintegration.controller;

import com.cloudspend.cloudspendintegration.service.ExpensifyService;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/report")
public class TestController {
    @Autowired
    ExpensifyService expensifyService;

    @GetMapping
    public String generateReport(@RequestParam String id, @RequestParam String secret) throws IOException, TemplateException {
        return expensifyService.generateReport(id, secret);
    }

    @GetMapping("/download")
    public String downloadFile(@RequestParam String id, @RequestParam String secret,@RequestParam String fileName) throws IOException {
        return expensifyService.downloadReport(fileName,id,secret);
    }
}
