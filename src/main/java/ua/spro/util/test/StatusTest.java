package ua.spro.util.test;

import javafx.collections.ObservableList;
import ua.spro.entity.client.Status;
import ua.spro.service.jdbc.StatusServiceImpl;

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
