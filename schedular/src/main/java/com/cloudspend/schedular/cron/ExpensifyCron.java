package com.cloudspend.schedular.cron;
import com.cloudspend.dao.ReportEnum;
import com.cloudspend.dao.entity.ExpensifyReports;
import com.cloudspend.dao.entity.ExpensifyUsers;
import com.cloudspend.dao.repository.ExpensifyReportsRepository;
import com.cloudspend.dao.repository.ExpensifyUsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExpensifyCron {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExpensifyCron.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    ExpensifyCron(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    @Autowired
    ExpensifyUsersRepository expensifyUsersRepository;
    @Autowired
    ExpensifyReportsRepository expensifyReportsRepository;
    @Value("${cloudkarafka.generate_reports}")
    private String generateReporsKafkaTopic;
    @Value("${cloudkarafka.download_reports}")
    private String downlaodReporsKafkaTopic;

    @Scheduled(cron = "0 * * * * *")
    public void GenerateReports() {
        try {
            LOGGER.info("CRON >> Processing fetching partner IDS");
            List<ExpensifyUsers> users = expensifyUsersRepository.findAll();
            LOGGER.info("Fetched Users : {}", users);
            users.forEach(user -> {
                kafkaTemplate.send(generateReporsKafkaTopic, user.getId());
            });
            Thread.sleep(1000 * 60);
        } catch (Exception e) {
            LOGGER.error("exception occured.." + e.getMessage(), e);
        }
    }

    @Scheduled(cron = "0 * * * * *")
    public void DownloadReports() {
        try {
            LOGGER.info("CRON >> Processing fetching reports to download");
            List<ExpensifyReports> reports = expensifyReportsRepository.findByStatus(ReportEnum.PENDING);
            LOGGER.info("Fetched reports:{}",reports);
            reports.forEach(report -> {
                kafkaTemplate.send(downlaodReporsKafkaTopic, report.getId());
            });
            Thread.sleep(1000 * 60);
        } catch (Exception e) {
            LOGGER.error("exception occured.." + e.getMessage(), e);
        }
    }
}
