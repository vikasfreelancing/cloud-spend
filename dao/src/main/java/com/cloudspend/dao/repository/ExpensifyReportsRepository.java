package com.cloudspend.dao.repository;

import com.cloudspend.dao.ReportEnum;
import com.cloudspend.dao.entity.ExpensifyReports;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpensifyReportsRepository extends MongoRepository<ExpensifyReports,String> {
    List<ExpensifyReports> findByPartnerUserIDAndStatus(String partnerUserID, ReportEnum status);
    List<ExpensifyReports> findByStatus(ReportEnum status);
}
