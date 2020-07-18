package com.cloudspend.dao.entity;

import com.cloudspend.dao.ReportEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;

@ToString
@Getter
@Setter
public class ExpensifyReports {
    @Id
    private String id;
    private String fileName;
    private String partnerUserID;
    private ReportEnum status;
}
