package com.java.virtusa.dao;

import com.java.virtusa.model.AgentExam;
import com.java.virtusa.model.Gender;
import com.java.virtusa.util.ConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AgentDaoImpl implements AgentDao{
  Connection con = null;
  PreparedStatement pst = null;

  @Override
  public List<AgentExam> showAgentDao() throws SQLException, ClassNotFoundException {
    con = ConnectionHelper.getConnection();
    String cmd = "SELECT * FROM Agent";
    pst = con.prepareStatement(cmd);
    ResultSet rs = pst.executeQuery();
    List<AgentExam> agentList = new ArrayList<>();

    while (rs.next()) {
      AgentExam agent = new AgentExam();
      agent.setAgentId(rs.getInt("AgentID"));
      agent.setName(rs.getString("Name"));
      agent.setCity(rs.getString("City"));
      agent.setGender(Gender.valueOf(rs.getString("Gender")));
      agent.setMaritalStatus(rs.getInt("MaritalStatus"));
      agent.setPremium(rs.getDouble("Premium"));

      agentList.add(agent);
    }
    return agentList;
  }
  @Override
  public AgentExam searchAgentDao(int agentId) throws SQLException, ClassNotFoundException {
    con = ConnectionHelper.getConnection();
    String cmd = "SELECT * FROM Agent WHERE AgentID = ?";
    pst = con.prepareStatement(cmd);
    pst.setInt(1, agentId);

    ResultSet rs = pst.executeQuery();
    AgentExam agent = null;

    if (rs.next()) {
      agent = new AgentExam();
      agent.setAgentId(rs.getInt("AgentID"));
      agent.setName(rs.getString("Name"));
      agent.setCity(rs.getString("City"));
      agent.setGender(Gender.valueOf(rs.getString("Gender")));
      agent.setMaritalStatus(rs.getInt("MaritalStatus"));
      agent.setPremium(rs.getDouble("Premium"));
    }
    return agent;
  }
  @Override
  public String addAgentDao(AgentExam agent) throws SQLException, ClassNotFoundException {
    con = ConnectionHelper.getConnection();
    String cmd = "INSERT INTO Agent(AgentID, Name, City, Gender, MaritalStatus, Premium) VALUES (?, ?, ?, ?, ?, ?)";

    pst = con.prepareStatement(cmd);
    pst.setInt(1, agent.getAgentId());
    pst.setString(2, agent.getName());
    pst.setString(3, agent.getCity());
    pst.setString(4, agent.getGender().toString());
    pst.setInt(5, agent.getMaritalStatus());
    pst.setDouble(6, agent.getPremium());

    pst.executeUpdate();
    return "Agent Record Inserted...";
  }
  @Override
  public String updateAgentDao(AgentExam agent) throws SQLException, ClassNotFoundException {
    con = ConnectionHelper.getConnection();
    String cmd = "UPDATE Agent SET Name = ?, City = ?, Gender = ?, MaritalStatus = ?, Premium = ? WHERE AgentID = ?";

    pst = con.prepareStatement(cmd);
    pst.setString(1, agent.getName());
    pst.setString(2, agent.getCity());
    pst.setString(3, agent.getGender().toString());
    pst.setInt(4, agent.getMaritalStatus());
    pst.setDouble(5, agent.getPremium());
    pst.setInt(6, agent.getAgentId());

    pst.executeUpdate();
    return "Agent Record Updated...";
  }
  @Override
  public String deleteAgentDao(int agentId) throws SQLException, ClassNotFoundException {
    con = ConnectionHelper.getConnection();
    String cmd = "DELETE FROM Agent WHERE AgentID = ?";
    pst = con.prepareStatement(cmd);
    pst.setInt(1, agentId);

    pst.executeUpdate();
    return "Agent Record Deleted...";
  }
}
