package com.cloudspend.dao.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;

@ToString
@Setter
@Getter
public class ExpensifyUsers {
    @Id
    private String id;
    private String partnerUserID;
    private String partnerUserSecret;
}
