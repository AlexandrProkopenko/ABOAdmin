package ua.spro.model.action.actions;

import ua.spro.entity.client.Status;
import ua.spro.model.action.UserAction;

public class SetStatusAction implements UserAction {

    private StringBuilder description;

    public SetStatusAction(Status oldValue, Status newValue) {
        description = new StringBuilder();
        description.
                append("Змінено статус з ").
                append(oldValue).
                append(" на ").
                append(newValue);
    }

    @Override
    public String getDescription() {
        return description.toString();
    }
}
