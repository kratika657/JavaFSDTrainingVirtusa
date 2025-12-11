package com.java.spr.registerservice.repo;

import com.java.spr.registerservice.model.Register;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegisterRepository extends MongoRepository<Register, String> {

}
