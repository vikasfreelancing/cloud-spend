package com.cloudspend.cloudspendservice.service;

import com.cloudspend.dao.entity.ExpensifyUsers;
import com.cloudspend.dao.entity.VendorDetails;

import java.util.List;

public interface UserService {
    List<VendorDetails> fetchVendorDetails(String partnerUserID, String partnerUserSecret);
    ExpensifyUsers addUser(String partnerUserID,String partnerUserSecret);
    ExpensifyUsers updatePassword(String partnerUserID,String partnerUserSecret);
    void auth(String partnerUserID, String partnerUserSecret);
}
