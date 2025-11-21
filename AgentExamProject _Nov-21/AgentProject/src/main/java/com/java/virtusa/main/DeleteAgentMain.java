package com.java.virtusa.main;

import com.java.virtusa.dao.AgentDao;
import com.java.virtusa.dao.AgentDaoImpl;

import java.sql.SQLException;
import java.util.Scanner;

public class DeleteAgentMain {
  public static void main(String[] args) {
    int agentId;
    Scanner sc = new Scanner(System.in);

    System.out.println("Enter Agent ID to delete : ");
    agentId = sc.nextInt();

    AgentDao dao = new AgentDaoImpl();
    try {
      System.out.println(dao.deleteAgentDao(agentId));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
