package pragoti.shared;

import pragoti.utils.FileHandler;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class LeaveInformation implements Serializable {
    protected int id, userId;
    // status: pending, approved, rejected
    // type: casual, medical, maternity, paternity, earned
    protected String reason, status, type;
    protected LocalDate startDate, endDate, applyDate;
    protected String attachment;

    public static final int MAX_LEAVE_DAYS = 10;

    public LeaveInformation() {
    }

    public LeaveInformation(int id, int userId, String reason, String status, String type, LocalDate startDate, LocalDate endDate, LocalDate applyDate, String attachment) {
        this.id = id;
        this.userId = userId;
        this.reason = reason;
        this.status = status;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.applyDate = applyDate;
        this.attachment = attachment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(LocalDate applyDate) {
        this.applyDate = applyDate;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "LeaveInformation{" +
                "id=" + id +
                ", userId=" + userId +
                ", reason='" + reason + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", applyDate=" + applyDate +
                ", attachment='" + attachment + '\'' +
                '}';
    }

    public static ArrayList<LeaveInformation> getAllLeaveInfo() {
        return FileHandler.<LeaveInformation>readObjectsFromFile("leave_info.bin");
    }

    public static int getNextId() {
        ArrayList<LeaveInformation> leaveInformationList = getAllLeaveInfo();
        int maxId = 1000;
        if (leaveInformationList == null || leaveInformationList.isEmpty()) {
            return maxId + 1;
        }

        for (LeaveInformation leaveInformation : leaveInformationList) {
            if (leaveInformation.getId() > maxId) {
                maxId = leaveInformation.getId();
            }
        }

        return maxId + 1;
    }

    public static LeaveInformation getLeaveInfoById(int id) {
        ArrayList<LeaveInformation> leaveInformationList = getAllLeaveInfo();
        if (leaveInformationList == null || leaveInformationList.isEmpty()) {
            return null;
        }

        for (LeaveInformation leaveInformation : leaveInformationList) {
            if (leaveInformation.getId() == id) {
                return leaveInformation;
            }
        }

        return null;
    }

    public boolean saveToFile() {
        return saveLeaveInfo(this);
    }

    public static boolean saveLeaveInfo(LeaveInformation leaveInformation) {
       return FileHandler.writeObjectToFile(leaveInformation, "leave_info.bin");
    }
}
