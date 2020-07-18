package com.cloudspend.dao.repository;

import com.cloudspend.dao.entity.VendorDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorDetailsRepository extends MongoRepository<VendorDetails,String> {
    List<VendorDetails> findByPartnerUserID(String partnerUserID);
}
