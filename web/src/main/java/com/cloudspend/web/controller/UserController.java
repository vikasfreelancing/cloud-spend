package com.cloudspend.web.controller;

import com.cloudspend.cloudspendintegration.service.ExpensifyService;
import com.cloudspend.cloudspendservice.service.UserService;
import com.cloudspend.dao.entity.ExpensifyUsers;
import com.cloudspend.dao.entity.VendorDetails;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;


    @PostMapping("/add")
    public ExpensifyUsers addUser(@RequestBody ExpensifyUsers user){
        log.info("add user request : {}",user);
        return userService.addUser(user.getPartnerUserID(),user.getPartnerUserSecret());
    }
    @PostMapping("/update")
    public ExpensifyUsers updateUser(@RequestBody ExpensifyUsers user){
        log.info("update user request : {}",user);
        return userService.updatePassword(user.getPartnerUserID(),user.getPartnerUserSecret());
    }
    @PostMapping("/details")
    public List<VendorDetails> vendorDetails(@RequestBody ExpensifyUsers user){
        log.info("fetch details request : {}",user);
        return userService.fetchVendorDetails(user.getPartnerUserID(),user.getPartnerUserSecret());
    }
}
