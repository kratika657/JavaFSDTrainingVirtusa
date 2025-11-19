package com.java.leave.dao;

import com.java.leave.model.LeaveDetails;

import java.util.List;

public interface LeaveDao {
    String addLeaveDao(LeaveDetails leave);
    List<LeaveDetails> showLeaveDao();
    LeaveDetails searchLeaveDao(int leaveId);
    String updateLeaveDao(LeaveDetails leave);
    String deleteLeaveDao(int leaveId);
}
