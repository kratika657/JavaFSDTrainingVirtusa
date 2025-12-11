package com.java.spr.eurekainnerclient;

import com.java.spr.model.Agent;
import com.java.spr.model.Register;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value="/")
    public String dpage() {
        return "Login Page <br/> <a href=\"/oauth2/authorization/github\">Login With GitHub</a>\n";
    }

    @GetMapping("/agentshow")
    public Agent[]  getAgents() {
        Agent[] agents = restTemplate.getForObject("http://AGENTSERVICE/agent/allAgent", Agent[].class);
        return agents;
    }

    @GetMapping("searchagent/{agentId}")
    public Agent search(@PathVariable int agentId) {
        return restTemplate.getForObject("http://AGENTSERVICE/agent/searchAgent/"+agentId, Agent.class);
    }
    @GetMapping("/registershow")
    public Register[] getRegister() {
        Register[] registers = restTemplate.getForObject("http://REGISTERSERVICE/register/all", Register[].class);
        return registers;
    }
    @GetMapping("searchregister/{complaintId}")
    public Register searchId(@PathVariable String complaintId) {
        return restTemplate.getForObject("http://REGISTERSERVICE/register/search/"+complaintId, Register.class);
    }

}
