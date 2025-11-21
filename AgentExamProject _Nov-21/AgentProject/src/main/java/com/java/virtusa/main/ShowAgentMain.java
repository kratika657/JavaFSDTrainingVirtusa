package com.java.virtusa.main;

import com.java.virtusa.dao.AgentDao;
import com.java.virtusa.dao.AgentDaoImpl;
import com.java.virtusa.model.AgentExam;

import java.sql.SQLException;
import java.util.List;

public class ShowAgentMain {
  public static void main(String[] args) {
    AgentDao dao = new AgentDaoImpl();
    try {
      List<AgentExam> agentList = dao.showAgentDao();
      for (AgentExam agent : agentList) {
        System.out.println(agent);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
