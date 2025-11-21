package com.java.virtusa.main;

import com.java.virtusa.dao.AgentDao;
import com.java.virtusa.dao.AgentDaoImpl;
import com.java.virtusa.model.Gender;
import com.java.virtusa.model.AgentExam;


import java.sql.SQLException;
import java.util.Scanner;

public class UpdateAgentMain {
  public static void main(String[] args) {
    AgentExam agent = new AgentExam();
    Scanner sc = new Scanner(System.in);

    System.out.print("Enter Agent ID : ");
    agent.setAgentId(sc.nextInt());

    System.out.print("Enter Agent Name : ");
    agent.setName(sc.next());

    System.out.print("Enter City : ");
    agent.setCity(sc.next());

    System.out.print("Enter Gender (MALE/FEMALE) : ");
    agent.setGender(Gender.valueOf(sc.next()));

    System.out.print("Enter Marital Status (0-Single, 1-Married) : ");
    agent.setMaritalStatus(sc.nextInt());

    System.out.print("Enter Premium : ");
    agent.setPremium(sc.nextDouble());

    AgentDao agentDao = new AgentDaoImpl();
    try {
      System.out.println(agentDao.updateAgentDao(agent));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
