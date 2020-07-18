package com.cloudspend.cloudspendservice.service.impl;

import com.cloudspend.cloudspendservice.service.UserService;
import com.cloudspend.dao.entity.ExpensifyUsers;
import com.cloudspend.dao.entity.VendorDetails;
import com.cloudspend.dao.repository.ExpensifyUsersRepository;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Override
    public List<VendorDetails> fetchVendorDetails(String partnerUserID, String partnerUserSecret) {
        return null;
    }

    @Autowired
    ExpensifyUsersRepository expensifyUsersRepository;
    @Override
    public ExpensifyUsers addUser(String partnerUserID, String partnerUserSecret) {
        Optional<ExpensifyUsers> usersOptional = expensifyUsersRepository.findByPartnerUserID(partnerUserID);
        Preconditions.checkArgument(!usersOptional.isPresent(),"User already present with this id:"+partnerUserID);
        ExpensifyUsers user = new ExpensifyUsers();
        user.setPartnerUserID(partnerUserID);
        user.setPartnerUserSecret(partnerUserSecret);
        log.info("Successfully added user :{}",expensifyUsersRepository.save(user));
        return user;
    }

    @Override
    public ExpensifyUsers updatePassword(String partnerUserID, String partnerUserSecret) {
        return null;
    }
}
