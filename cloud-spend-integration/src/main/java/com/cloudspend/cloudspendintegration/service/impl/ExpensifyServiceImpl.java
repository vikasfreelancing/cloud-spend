package com.cloudspend.cloudspendintegration.service.impl;

import com.cloudspend.cloudspendintegration.config.IntegrationConfig;
import com.cloudspend.cloudspendintegration.service.ExpensifyService;
import com.cloudspend.cloudspendintegration.vo.DownloadReportJobDiscriptionVO;
import com.cloudspend.cloudspendintegration.vo.RequestJobDescriptionVO;
import com.cloudspend.dao.entity.VendorDetails;
import com.cloudspend.dao.repository.VendorDetailsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.opencsv.CSVReader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpensifyServiceImpl implements ExpensifyService {

    private static final Logger LOGGER = LogManager.getLogger(IntegrationConfig.class);
    @Autowired
    IntegrationConfig integrationConfig;
    @Autowired
    Configuration freeMarkerConfig;
    RestTemplate restTemplate = new RestTemplate();
    @Autowired
    Gson gson;

    public String generateReport(String partnerUserID, String partnerUserSecret) throws IOException, TemplateException {
        ObjectMapper objectMapper = new ObjectMapper();
        String url = integrationConfig.getExpensify().getUrl();
        HttpHeaders httpHeaders = getHeaders();
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        Template template = freeMarkerConfig.getTemplate("template.ftl");
        Map<String, Object> model = new HashMap<>();
        //String templateRequest = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        RequestJobDescriptionVO requestJobDescription = createRequestJobDescription(partnerUserID, partnerUserSecret);
        LOGGER.info("requestJobDescription: {} ", objectMapper.writeValueAsString(requestJobDescription));
        map.add("requestJobDescription", objectMapper.writeValueAsString(requestJobDescription));
        map.add("template", template);
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(map, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        LOGGER.info(response);
        return response.getBody();
    }
    private List<String[]> readCSVFile(InputStream inputStream) throws IOException {
        CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream));
        List<String[]> records = csvReader.readAll();
        return records;
    }
    @Override
    public String downloadReport(String fileName, String partnerUserID, String partnerUserSecret) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String url = integrationConfig.getExpensify().getUrl();
        HttpHeaders httpHeaders = getHeaders();
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        DownloadReportJobDiscriptionVO requestJobDescription = createDownloadJobDescription(partnerUserID, partnerUserSecret, fileName);
        LOGGER.info("requestJobDescription: {} ", objectMapper.writeValueAsString(requestJobDescription));
        map.add("requestJobDescription", objectMapper.writeValueAsString(requestJobDescription));
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(map, httpHeaders);
        ResponseEntity<Resource> response = restTemplate.exchange(url, HttpMethod.POST, entity, Resource.class);
        LOGGER.info("response-->{}", response.getBody().getInputStream().toString());
        List<String[]> records = readCSVFile(response.getBody().getInputStream());
        List<VendorDetails> vendorDetailsList = generateAndSaveVendorDetails(records,partnerUserID);
        LOGGER.info("Success fully fetched data from Expensify : {}",vendorDetailsList);
        return "SUCCESS";
    }

    private DownloadReportJobDiscriptionVO createDownloadJobDescription(String partnerUserID, String partnerUserSecret, String fileName) {
        DownloadReportJobDiscriptionVO downloadReportJobDiscriptionVO = new DownloadReportJobDiscriptionVO();
        RequestJobDescriptionVO.Credential credential = new RequestJobDescriptionVO.Credential();
        credential.setPartnerUserID(partnerUserID);
        credential.setPartnerUserSecret(partnerUserSecret);
        downloadReportJobDiscriptionVO.setCredentials(credential);
        downloadReportJobDiscriptionVO.setFileName(fileName);
        return downloadReportJobDiscriptionVO;
    }

    private RequestJobDescriptionVO createRequestJobDescription(String partnerUserID, String partnerUserSecret) {
        RequestJobDescriptionVO requestJobDescriptionVO = new RequestJobDescriptionVO();
        RequestJobDescriptionVO.Credential credential = new RequestJobDescriptionVO.Credential();
        credential.setPartnerUserID(partnerUserID);
        credential.setPartnerUserSecret(partnerUserSecret);
        requestJobDescriptionVO.setCredentials(credential);
        RequestJobDescriptionVO.ONReceive onReceive = new RequestJobDescriptionVO.ONReceive();
        requestJobDescriptionVO.setOnReceive(onReceive);
        RequestJobDescriptionVO.InputSettings inputSettings = new RequestJobDescriptionVO.InputSettings();
        inputSettings.getFilters().put("startDate", "2016-01-01");
        requestJobDescriptionVO.setInputSettings(inputSettings);
        requestJobDescriptionVO.getOutputSettings().put("fileExtension", "csv");
        return requestJobDescriptionVO;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    @Autowired
    VendorDetailsRepository vendorDetailsRepository;
    private List<VendorDetails> generateAndSaveVendorDetails(List<String[]> records, String partnerUserID){
        List<VendorDetails> vendorDetailsList = new ArrayList<>();
        records.forEach(record->{
            VendorDetails vendorDetails = createVendorDetails(record);
            vendorDetails.setPartnerUserID(partnerUserID);
            vendorDetailsList.add(vendorDetails);
        });
        List<VendorDetails> savedVendorDetails = vendorDetailsRepository.saveAll(vendorDetailsList);
        return vendorDetailsList;
    }

    private VendorDetails createVendorDetails(String[] record) {
        VendorDetails vendorDetails = new VendorDetails();
        vendorDetails.setVendorName(record[0]);
        vendorDetails.setAmount(new BigDecimal(record[1]));
        vendorDetails.setCreatedAt(LocalDate.parse(record[2]));
        vendorDetails.setReceiptID(record[3]);
        vendorDetails.setReceiptFilename(record[4]);
        vendorDetails.setReceiptID(record[5]);
        vendorDetails.setCount(record[6]);
        vendorDetails.setAttendees(record[7]);
        return vendorDetails;
    }


}
