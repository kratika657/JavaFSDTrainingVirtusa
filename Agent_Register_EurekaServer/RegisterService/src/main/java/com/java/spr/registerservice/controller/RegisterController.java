package com.java.spr.registerservice.controller;

import com.java.spr.registerservice.exception.ResourceNotFoundException;
import com.java.spr.registerservice.model.Register;
import com.java.spr.registerservice.repo.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterRepository registerRepository;

    // ================== CREATE ==================
    @PostMapping("/add")
    public ResponseEntity<String> addRegister(@RequestBody Register register) {
        registerRepository.save(register);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Yeah! Complaint added successfully with ID: " + register.getComplaintId());
    }

    // ================== READ ALL ==================
    @GetMapping("/all")
    public ResponseEntity<List<Register>> getAllRegisters() {
        List<Register> registers = registerRepository.findAll();
        return ResponseEntity.ok(registers);
    }

    // ================== READ BY complaintId ==================
    @GetMapping("/search/{complaintId}")
    public ResponseEntity<Register> getRegisterById(@PathVariable String complaintId) {
        Register register = registerRepository.findById(complaintId)
                .orElseThrow(() -> new ResourceNotFoundException("Register not found with ID: " + complaintId));
        return ResponseEntity.ok(register);
    }


    // ================== UPDATE ==================
    @PutMapping("/update/{complaintId}")
    public ResponseEntity<String> updateRegister(@PathVariable String complaintId, @RequestBody Register registerDetails) {
        Register register = registerRepository.findById(complaintId)
                .orElseThrow(() -> new ResourceNotFoundException("Register not found with ID: " + complaintId));

        register.setComplaintType(registerDetails.getComplaintType());
        register.setComplaintDescription(registerDetails.getComplaintDescription());
        register.setSeverity(registerDetails.getSeverity());
        register.setStatus(registerDetails.getStatus());

        registerRepository.save(register);
        return ResponseEntity.ok("Yeah! Complaint updated successfully with ID: " + complaintId);
    }

    // ================== DELETE ==================
    @DeleteMapping("/delete/{complaintId}")
    public ResponseEntity<String> deleteRegister(@PathVariable String complaintId) {
        Register register = registerRepository.findById(complaintId)
                .orElseThrow(() -> new ResourceNotFoundException("Register not found with ID: " + complaintId));

        registerRepository.delete(register);
        return ResponseEntity.ok("Yeah! Complaint deleted successfully with ID: " + complaintId);
    }
}
