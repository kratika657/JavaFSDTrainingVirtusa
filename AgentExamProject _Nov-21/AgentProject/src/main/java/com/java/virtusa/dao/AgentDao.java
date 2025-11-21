package com.java.virtusa.dao;

import com.java.virtusa.model.AgentExam;

import java.sql.SQLException;
import java.util.List;

public interface AgentDao {
  List<AgentExam> showAgentDao() throws SQLException, ClassNotFoundException;
  AgentExam searchAgentDao(int agentId) throws SQLException, ClassNotFoundException;
  String addAgentDao(AgentExam agent) throws SQLException, ClassNotFoundException;
  String updateAgentDao(AgentExam agent) throws SQLException, ClassNotFoundException;
  String deleteAgentDao(int agentId) throws SQLException, ClassNotFoundException;
}
