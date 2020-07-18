package com.cloudspend.dao.repository;

import com.cloudspend.dao.entity.VendorDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VendorDetailsRepository extends MongoRepository<VendorDetails,String> {
}
