package com.cloudspend.listener;

import com.cloudspend.cloudspendintegration.service.ExpensifyService;
import com.cloudspend.commons.producers.MessageProducer;
import com.cloudspend.dao.ReportEnum;
import com.cloudspend.dao.entity.ExpensifyReports;
import com.cloudspend.dao.entity.ExpensifyUsers;
import com.cloudspend.dao.repository.ExpensifyReportsRepository;
import com.cloudspend.dao.repository.ExpensifyUsersRepository;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Listeners {
    private static final Logger LOGGER = LoggerFactory.getLogger(Listeners.class);
    @Autowired
    @Qualifier("defaultKafkaProducer")
    MessageProducer<String, String> kafkaPushService;
    @Autowired
    ExpensifyUsersRepository expensifyUsersRepository;
    @Autowired
    ExpensifyService expensifyService;
    @Autowired
    ExpensifyReportsRepository expensifyReportsRepository;

    @KafkaListener(
            topics = "${cloudkarafka.generate_reports}",
            containerFactory = "defaultListenerAutoAckContainerFactory",
            autoStartup = "${enable.kafka.listener:true}"
    )
    public void generateReportsListener(String userId) throws Exception {
        try {
            LOGGER.info(String.format(" generateReportsListener for user id %s ", userId));
            Optional<ExpensifyUsers> usersOptional = expensifyUsersRepository.findById(userId);
            Preconditions.checkArgument(usersOptional.isPresent(),"no user found with Id :"+userId);
            ExpensifyUsers user = usersOptional.get();
            String fileName = expensifyService.generateReport(user.getPartnerUserID(),user.getPartnerUserSecret());
            ExpensifyReports expensifyReports = new ExpensifyReports();
            expensifyReports.setFileName(fileName);
            expensifyReports.setPartnerUserID(user.getPartnerUserID());
            expensifyReports.setStatus(ReportEnum.PENDING);
            ExpensifyReports savedReport = expensifyReportsRepository.save(expensifyReports);
            LOGGER.info("Successfully saved report : {}",savedReport);
        } catch (Exception e) {
            LOGGER.error("Exception caught in initiating credit transfer of bpay.." + e.getMessage(), e);
            throw e;
        }
    }

}
