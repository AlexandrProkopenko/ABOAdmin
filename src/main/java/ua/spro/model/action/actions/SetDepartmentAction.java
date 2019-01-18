package ua.spro.model.action.actions;

import ua.spro.entity.client.Department;
import ua.spro.model.action.UserAction;

public class SetDepartmentAction implements UserAction {

    private StringBuilder description;

    public SetDepartmentAction(Department oldValue, Department newValue) {
        description = new StringBuilder();
        description.
                append("Змінено філію з ").
                append(oldValue).
                append(" на ").
                append(newValue);
    }

    @Override
    public String getDescription() {
        return description.toString();
    }
}
