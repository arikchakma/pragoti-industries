package pragoti.shared;

import pragoti.utils.FileHandler;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class DispatchInfo implements Serializable {
    protected int id, vehicleId, driverId, dispatcherUserId;
    // Status can be: Scheduled, Dispatched, Delivered, Cancelled
    protected String destination, status;
    protected LocalDate dispatchDate, estimatedArrivalDate;
    protected String priority, remarks;

    public DispatchInfo() {
    }

    public DispatchInfo(int id, int vehicleId, int driverId, int dispatcherUserId, String destination, String status, LocalDate dispatchDate, LocalDate estimatedArrivalDate, String priority, String remarks) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.driverId = driverId;
        this.dispatcherUserId = dispatcherUserId;
        this.destination = destination;
        this.status = status;
        this.dispatchDate = dispatchDate;
        this.estimatedArrivalDate = estimatedArrivalDate;
        this.priority = priority;
        this.remarks = remarks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getDispatcherUserId() {
        return dispatcherUserId;
    }

    public void setDispatcherUserId(int dispatcherUserId) {
        this.dispatcherUserId = dispatcherUserId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDispatchDate() {
        return dispatchDate;
    }

    public void setDispatchDate(LocalDate dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    public LocalDate getEstimatedArrivalDate() {
        return estimatedArrivalDate;
    }

    public void setEstimatedArrivalDate(LocalDate estimatedArrivalDate) {
        this.estimatedArrivalDate = estimatedArrivalDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    @Override
    public String toString() {
        return "DispatchSchedule{" +
                "id=" + id +
                ", vehicleId=" + vehicleId +
                ", driverId=" + driverId +
                ", dispatcherUserId=" + dispatcherUserId +
                ", destination='" + destination + '\'' +
                ", status='" + status + '\'' +
                ", dispatchDate=" + dispatchDate +
                ", estimatedArrivalDate=" + estimatedArrivalDate +
                ", priority='" + priority + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }

    public static ArrayList<DispatchInfo> getAllDispatchInfo() {
        return FileHandler.<DispatchInfo>readObjectsFromFile("dispatch_info.bin");
    }

    public static DispatchInfo getDispatchInfo(int id) {
        ArrayList<DispatchInfo> dispatchList = getAllDispatchInfo();
        if (dispatchList == null) {
            return null;
        }

        for (DispatchInfo dispatch : dispatchList) {
            if (dispatch.getId() == id) {
                return dispatch;
            }
        }

        return null;
    }

    public static int getNextId() {
        ArrayList<DispatchInfo> dispatchList = getAllDispatchInfo();
        int maxId = 1000;
        if (dispatchList == null || dispatchList.isEmpty()) {
            return maxId + 1;
        }

        for (DispatchInfo dispatch : dispatchList) {
            if (dispatch.getId() > maxId) {
                maxId = dispatch.getId();
            }
        }

        return maxId + 1;
    }

    public boolean saveToFile() {
        return saveDispatchInfo(this);
    }

    public static boolean saveDispatchInfo(DispatchInfo di) {
        return FileHandler.writeObjectToFile(di, "dispatch_info.bin");
    }

    public boolean updateAndSaveToFile() {
        return updateDispatchInfo(this);
    }

    public static boolean updateDispatchInfo(DispatchInfo di) {
        ArrayList<DispatchInfo> dispatchList = getAllDispatchInfo();
        if (dispatchList == null) {
            return false;
        }

       int index = -1;
        for (int i = 0; i < dispatchList.size(); i++) {
            if (dispatchList.get(i).getId() == di.getId()) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            return false;
        }

        Vehicle vehicle = Vehicle.getVehicle(di.getVehicleId());
        if (vehicle == null) {
            return false;
        }

        vehicle.setStatus(di.getStatus());
        if (!Vehicle.updateVehicle(vehicle)) {
            return false;
        }

        dispatchList.set(index, di);
        FileHandler.deleteFile("dispatch_info.bin");
        return FileHandler.replaceFile(dispatchList, "dispatch_info.bin");
    }
}
