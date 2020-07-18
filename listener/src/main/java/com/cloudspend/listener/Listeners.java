package com.cloudspend.listener;

import com.cloudspend.cloudspendintegration.service.ExpensifyService;
import com.cloudspend.dao.ReportEnum;
import com.cloudspend.dao.entity.ExpensifyReports;
import com.cloudspend.dao.entity.ExpensifyUsers;
import com.cloudspend.dao.repository.ExpensifyReportsRepository;
import com.cloudspend.dao.repository.ExpensifyUsersRepository;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Listeners {
    private static final Logger LOGGER = LoggerFactory.getLogger(Listeners.class);
    @Autowired
    ExpensifyUsersRepository expensifyUsersRepository;
    @Autowired
    ExpensifyService expensifyService;
    @Autowired
    ExpensifyReportsRepository expensifyReportsRepository;

    @KafkaListener(
            topics = "${cloudkarafka.generate_reports}"
    )
    public void generateReportsListener(String userId) throws Exception {
        try {
            LOGGER.info(String.format(" generateReportsListener for user id %s ", userId));
            Optional<ExpensifyUsers> usersOptional = expensifyUsersRepository.findById(userId);
            Preconditions.checkArgument(usersOptional.isPresent(), "no user found with Id :" + userId);
            ExpensifyUsers user = usersOptional.get();
            String fileName = expensifyService.generateReport(user.getPartnerUserID(), user.getPartnerUserSecret());
            ExpensifyReports expensifyReports = new ExpensifyReports();
            expensifyReports.setFileName(fileName);
            expensifyReports.setPartnerUserID(user.getPartnerUserID());
            expensifyReports.setStatus(ReportEnum.PENDING);
            ExpensifyReports savedReport = expensifyReportsRepository.save(expensifyReports);
            LOGGER.info("Successfully saved report : {}", savedReport);
        } catch (Exception e) {
            LOGGER.error("Exception caught." + e.getMessage(), e);
            throw e;
        }
    }
        @KafkaListener(
            topics = "${cloudkarafka.download_reports}"
    )
    public void downloadReportsListener(String reportId) throws Exception {
        try {
            LOGGER.info(String.format(" downloadReportsListener for report id %s ", reportId));
            Optional<ExpensifyReports> expensifyReports = expensifyReportsRepository.findById(reportId);
            Preconditions.checkArgument(expensifyReports.isPresent(), "no report found with Id :" + reportId);
            ExpensifyReports report = expensifyReports.get();
            Optional<ExpensifyUsers> usersOptional = expensifyUsersRepository.findByPartnerUserID(report.getPartnerUserID());
            Preconditions.checkArgument(usersOptional.isPresent(), "no user found with partnerID :" + report.getPartnerUserID());
            ExpensifyUsers user = usersOptional.get();
            String status = expensifyService.downloadReport(report.getFileName(),user.getPartnerUserID(), user.getPartnerUserSecret());
            report.setStatus(ReportEnum.valueOf(status));
            ExpensifyReports savedReport = expensifyReportsRepository.save(report);
            LOGGER.info("Successfully saved report : {}", savedReport);
        } catch (Exception e) {
            LOGGER.error("Exception caught " + e.getMessage(), e);

            throw e;
        }
    }

}
