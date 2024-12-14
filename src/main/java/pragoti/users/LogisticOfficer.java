package pragoti.users;

import pragoti.shared.*;
import pragoti.utils.FileHandler;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class LogisticOfficer extends User implements Serializable {
    public LogisticOfficer() {
    }

    public LogisticOfficer(int id, String name, String gender, String email, String designation, LocalDate dob, LocalDate doj, String password, float salary) {
        super(id, name, gender, email, designation, dob, doj, password, salary);
    }

    public boolean applyForLeave(String reason, String type, LocalDate startDate, LocalDate endDate, String attachment) {
        int leaveId = LeaveInformation.getNextId();
        LeaveInformation leaveInformation = new LeaveInformation(leaveId, this.getId(), reason, "Pending", type, startDate, endDate, LocalDate.now(), attachment);
        if (!leaveInformation.saveToFile()) {
            System.out.println("Failed to save leave information to file");
            return false;
        }

        return true;
    }

    public void validateApplyLeave(String reason, String type, LocalDate startDate, LocalDate endDate) throws ValidationError {
        if (startDate.isAfter(endDate)) {
            throw new ValidationError("Start date can't be after end date");
        }

        ArrayList<LeaveInformation> leaveInformationArrayList = LeaveInformation.getAllLeaveInfo();

        int leaveCount = 0;
        for (LeaveInformation leaveInformation : leaveInformationArrayList) {
            if (leaveInformation.getUserId() == this.getId()) {
                if (leaveInformation.getStatus().equals("Pending")) {
                    throw new ValidationError("You can't apply for leave because you already have a pending leave");
                }
                if (leaveInformation.getStartDate().isBefore(startDate) && leaveInformation.getEndDate().isAfter(startDate)) {
                    throw new ValidationError("You can't apply for leave because you are already on leave for the same date");
                }


                if (leaveInformation.getStatus().equals("Approved")) {
                    // +1 because the leave count should include the start date
                    leaveCount += leaveInformation.getEndDate().getDayOfYear() - leaveInformation.getStartDate().getDayOfYear() + 1;
                }
            }
        }

        int days = endDate.getDayOfYear() - startDate.getDayOfYear() + 1;
        if (leaveCount + days > LeaveInformation.MAX_LEAVE_DAYS) {
            throw new ValidationError("You can't apply for leave because you have exceeded the leave limit");
        }
    }

    public void validateDispatchVehicle(int vehicleId, int driverId, String priority, String destination, LocalDate dispatchDate, LocalDate arrivalDate, String status, String remarks) throws ValidationError {
        if (dispatchDate.isAfter(arrivalDate)) {
            throw new ValidationError("Dispatch date can't be after arrival date");
        }

        // check if the driver is already assigned to a vehicle
        // and if the vehicle is already dispatched
        ArrayList<DispatchInfo> dispatchInfos = DispatchInfo.getAllDispatchInfo();
        for (DispatchInfo di : dispatchInfos) {
            if (di.getDriverId() == driverId && di.getStatus().equals("Dispatched")) {
                throw new ValidationError("Driver is already assigned to a vehicle");
            }
        }
    }

    public boolean dispatchVehicle(int vehicleId, int driverId, String priority, String destination, LocalDate dispatchDate, LocalDate arrivalDate, String status, String remarks) {
        DispatchInfo di = new DispatchInfo(DispatchInfo.getNextId(), vehicleId, driverId, this.getId(), destination, status, dispatchDate, arrivalDate, priority, remarks);
        Vehicle vehicle = Vehicle.getVehicle(vehicleId);
        if (vehicle == null) {
            System.out.println("Vehicle not found");
            return false;
        }

        vehicle.setStatus(status);
        vehicle.updateAndSaveVehicle();
        return di.saveToFile();
    }

    public boolean deleteDriver(int driverId) throws ValidationError {
        ArrayList<DispatchInfo> dispatchInfos = DispatchInfo.getAllDispatchInfo();
        for (DispatchInfo di : dispatchInfos) {
            if (di.getDriverId() == driverId) {
                throw new ValidationError("Driver is already assigned to a vehicle");
            }
        }

        return Driver.deleteDriver(driverId);
    }

    public void validateUpdatePassword(String oldPassword, String newPassword) throws ValidationError {
        if(!User.validatePassword(newPassword)) {
            throw new ValidationError("Password must contain at least one digit, and one special character");
        }

        if (!this.getPassword().equals(oldPassword)) {
            throw new ValidationError("Old password is incorrect");
        }

    }

    public boolean updatePassword(String newPassword) {
        this.setPassword(newPassword);

        ArrayList<User> users = User.getAllUsers();
        int index = -1;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == this.getId()) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            return false;
        }

        users.set(index, this);
        FileHandler.deleteFile("users.bin");
        return FileHandler.replaceFile(users, "users.bin");
    }
}
