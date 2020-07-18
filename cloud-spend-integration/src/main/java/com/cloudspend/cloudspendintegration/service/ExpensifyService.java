package com.cloudspend.cloudspendintegration.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import freemarker.template.TemplateException;

import java.io.IOException;

public interface ExpensifyService {
    String generateReport(String partnerUserID, String partnerUserSecret) throws IOException, TemplateException ;

    String downloadReport(String fileName,String partnerUserID, String partnerUserSecret) throws IOException;
}
