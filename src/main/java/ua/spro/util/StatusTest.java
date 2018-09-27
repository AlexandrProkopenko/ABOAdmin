package ua.spro.util;

import javafx.collections.ObservableList;
import ua.spro.entity.Status;
import ua.spro.service.impl.StatusServiceImpl;

public class StatusTest {
    public static void main(String[] args) {
        Status status = new Status("цікавляться");
        StatusServiceImpl statusService = new StatusServiceImpl();
        ObservableList<Status> statuses = statusService.getAll();

        if(statuses.contains(status)){
            status.setStatusId(statuses.indexOf(status));

        }
        System.out.println("statusId: " + status.getStatusId());
    }
}
