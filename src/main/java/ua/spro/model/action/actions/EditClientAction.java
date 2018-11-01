package ua.spro.model.action.actions;

import ua.spro.entity.Client;
import ua.spro.model.action.UserAction;

public class EditClientAction implements UserAction {

    private StringBuilder description;

    public EditClientAction(Client oldValue, Client newValue) {
        description = new StringBuilder();
        description.
                append("Змінено інфо клієнта. Нові дані: \n").
                append(newValue.getDescription()).
                append("\n").
                append("Попередні дані\n").
                append(oldValue.getDescription());
    }

    @Override
    public String getDescription() {
        return description.toString();
    }
}
