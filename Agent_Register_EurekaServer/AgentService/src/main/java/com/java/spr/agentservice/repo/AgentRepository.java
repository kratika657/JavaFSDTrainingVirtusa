package com.java.spr.agentservice.repo;

import com.java.spr.agentservice.model.Agent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends MongoRepository<Agent, String> {

}
