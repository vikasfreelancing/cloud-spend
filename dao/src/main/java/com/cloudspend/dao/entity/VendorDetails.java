package com.cloudspend.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class VendorDetails {
    @Id
    private String id;
    private String partnerUserID;
    private String vendorName;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private String reportId;
    private String receiptID;
    private String receiptFilename;
    private String count;
    private String attendees;
}
