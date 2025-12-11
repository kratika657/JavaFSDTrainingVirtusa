package com.java.spr.agentservice.controller;

import com.java.spr.agentservice.exception.ResourceNotFoundException;
import com.java.spr.agentservice.model.Agent;
import com.java.spr.agentservice.repo.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agent")
public class AgentController {

    @Autowired
    private AgentRepository agentRepository;

    // CREATE
    @PostMapping("/addAgent")
    public ResponseEntity<Agent> addAgent(@RequestBody Agent agent) {
        Agent savedAgent = agentRepository.save(agent);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAgent); // 201 Created
    }

    // READ ALL
    @GetMapping("/allAgent")
    public ResponseEntity<List<Agent>> getAllAgents() {
        List<Agent> agents = agentRepository.findAll();
        return ResponseEntity.ok(agents); // 200 OK
    }

    // READ BY agentId
    @GetMapping("/searchAgent/{agentId}")
    public ResponseEntity<Agent> getAgentById(@PathVariable String agentId) {
        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new ResourceNotFoundException("Agent not found with ID: " + agentId));
        return ResponseEntity.ok(agent); // 200 OK
    }

    // UPDATE
    @PutMapping("/update/{agentId}")
    public ResponseEntity<Agent> updateAgent(@PathVariable String agentId, @RequestBody Agent agentDetails) {
        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new ResourceNotFoundException("Agent not found with ID: " + agentId));

        agent.setFirstName(agentDetails.getFirstName());
        agent.setLastName(agentDetails.getLastName());
        agent.setCity(agentDetails.getCity());
        agent.setState(agentDetails.getState());
        agent.setPremiumPaid(agentDetails.getPremiumPaid());

        Agent updatedAgent = agentRepository.save(agent);
        return ResponseEntity.ok(updatedAgent); // 200 OK
    }

    // DELETE BY agentId
    @DeleteMapping("/delete/{agentId}")
    public ResponseEntity<String> deleteAgent(@PathVariable String agentId) {
        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new ResourceNotFoundException("Agent not found with ID: " + agentId));

        agentRepository.delete(agent);
        return ResponseEntity.ok("Agent with ID " + agentId + " deleted successfully!");
    }
}
