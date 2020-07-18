package com.cloudspend.dao.repository;

import com.cloudspend.dao.entity.ExpensifyUsers;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpensifyUsersRepository extends MongoRepository<ExpensifyUsers,String> {
   Optional<ExpensifyUsers> findByPartnerUserID(String partnerUserID);
}
