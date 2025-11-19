package com.java.leave.dao;

import com.java.leave.model.LeaveDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LeaveDaoImpl implements LeaveDao{
    static List<LeaveDetails> leaveList = new ArrayList<>();

    @Override
    public String addLeaveDao(LeaveDetails leave) {
        leaveList.add(leave);
        return "Leave added successfully";
    }

    @Override
    public List<LeaveDetails> showLeaveDao() {
        return leaveList;
    }

    @Override
    public LeaveDetails searchLeaveDao(int leaveId) {
        for (LeaveDetails ld : leaveList) {
            if (ld.getLeaveId() == leaveId) {
                return ld;
            }
        }
        return null;
    }

    @Override
    public String updateLeaveDao(LeaveDetails leave) {
        LeaveDetails temp = searchLeaveDao(leave.getLeaveId());
        if (temp != null) {

            temp.setEmpId(leave.getEmpId());
            temp.setLeaveStartDate(leave.getLeaveStartDate());
            temp.setLeaveEndDate(leave.getLeaveEndDate());
            temp.setNoOfDays(leave.getNoOfDays());
            temp.setAppliedOn(new Date());
            temp.setLeaveReason(leave.getLeaveReason());

            return "Leave Updated Successfully";
        }
        return "Leave not found";
    }

    @Override
    public String deleteLeaveDao(int leaveId) {
        LeaveDetails ld = searchLeaveDao(leaveId);
        if (ld != null) {
            leaveList.remove(ld);
            return "Leave Deleted Successfully";
        }
        return "Leave not found";
    }
}
