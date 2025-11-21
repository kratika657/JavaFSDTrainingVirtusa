package com.java.virtusa.main;

import com.java.virtusa.dao.AgentDao;
import com.java.virtusa.dao.AgentDaoImpl;
import com.java.virtusa.model.AgentExam;

import java.sql.SQLException;
import java.util.Scanner;

public class SearchAgentMain {
  public static void main(String[] args) {
    int agentId;
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter Agent ID  ");
    agentId = sc.nextInt();

    AgentDao agentDao = new AgentDaoImpl();
    try {
      AgentExam agent = agentDao.searchAgentDao(agentId);
      if (agent != null) {
        System.out.println(agent);
      } else {
        System.out.println("Agent Not Found");
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
