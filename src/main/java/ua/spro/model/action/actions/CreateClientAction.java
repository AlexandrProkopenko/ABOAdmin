package ua.spro.model.action.actions;


import ua.spro.ABOAdminApp;
import ua.spro.entity.Client;
import ua.spro.entity.Department;
import ua.spro.entity.Status;
import ua.spro.model.action.UserAction;
import ua.spro.model.admin.AdminModelInterface;

public class CreateClientAction implements UserAction {

    private StringBuilder description;
    private static AdminModelInterface adminModel;
    private Status status;
    private Department department;

    public CreateClientAction(Client newClient) {
        adminModel = ABOAdminApp.getAdminModel();
        status = adminModel.getStatusesList().get(newClient.getStatusId());
        department = adminModel.getDepartmentsList().get(newClient.getDepartmentId());
        description = new StringBuilder();
        description.
                append("Додано нового клієнта: \n").
                append(newClient.getDescription()).
                append(" ").
                append(status).
                append(" ").
                append(department);
    }

    @Override
    public String getDescription() {
        return description.toString();
    }
}
