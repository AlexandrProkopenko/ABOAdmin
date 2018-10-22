package ua.spro.model.action.actions;


import ua.spro.entity.Client;
import ua.spro.model.action.UserAction;

public class CreateClientAction implements UserAction {

    private StringBuilder description;

    public CreateClientAction(Client newClient) {
        description = new StringBuilder();
        description.
                append("Додано нового клієнта: \n").
                append(newClient);
    }

    @Override
    public String getDescription() {
        return description.toString();
    }
}
