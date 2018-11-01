package ua.spro.model.action.actions;

import ua.spro.model.action.UserAction;

public class EditCommentAction implements UserAction {

    private StringBuilder description;

    public EditCommentAction(String oldValue, String newValue) {
        description = new StringBuilder();
        description.
                append("Коментар змінено з \n").
                append(oldValue).
                append("\nна ").
                append(newValue);
    }

    @Override
    public String getDescription() {
        return description.toString();
    }
}
