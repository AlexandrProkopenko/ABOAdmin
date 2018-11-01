package ua.spro.model.action.actions;

import ua.spro.model.action.UserAction;

public class AddCommentAction implements UserAction {

    private StringBuilder description;

    public AddCommentAction(String comment) {
        description = new StringBuilder();
        description.
                append(comment);
    }

    @Override
    public String getDescription() {
        return description.toString();
    }
}
