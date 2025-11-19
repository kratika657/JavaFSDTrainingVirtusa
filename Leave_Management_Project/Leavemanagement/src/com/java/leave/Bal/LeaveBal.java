package com.java.leave.Bal;

import com.java.leave.dao.LeaveDao;
import com.java.leave.dao.LeaveDaoImpl;
import com.java.leave.exception.LeaveException;
import com.java.leave.model.LeaveDetails;

import java.util.Date;
import java.util.List;

public class LeaveBal {
    LeaveDao dao = new LeaveDaoImpl();

    private void validate(LeaveDetails leave) throws LeaveException {
        Date today = new Date();

        if (leave.getLeaveStartDate().before(today)) {
            throw new LeaveException("Start date cannot be before today");
        }
        if (leave.getLeaveEndDate().before(today)) {
            throw new LeaveException("End date cannot be before today");
        }
        if (leave.getLeaveStartDate().after(leave.getLeaveEndDate())) {
            throw new LeaveException("Start date cannot be greater than end date");
        }
    }
    private int calculateDays(Date start, Date end) {
        long diff = end.getTime()- start.getTime();
        return (int) ((diff / (1000 * 60 * 60 * 24)) + 1);
    }
    public String addLeaveBal(LeaveDetails leave) throws LeaveException {
        validate(leave);
        leave.setNoOfDays(calculateDays(
                leave.getLeaveStartDate(),
                leave.getLeaveEndDate()
        ));
        leave.setAppliedOn(new Date());
        return dao.addLeaveDao(leave);
    }
    public List<LeaveDetails> showLeaveBal() {
        return dao.showLeaveDao();
    }

    public LeaveDetails searchLeaveBal(int leaveId) {
        return dao.searchLeaveDao(leaveId);
    }
    public String updateLeaveBal(LeaveDetails leave) throws LeaveException {

        validate(leave);

        leave.setNoOfDays(calculateDays(
                leave.getLeaveStartDate(),
                leave.getLeaveEndDate()
        ));

        leave.setAppliedOn(new Date());

        return dao.updateLeaveDao(leave);
    }
    public String deleteLeaveBal(int leaveId) {
        return dao.deleteLeaveDao(leaveId);
    }
}
