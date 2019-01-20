package ua.spro.model.action.actions;

import ua.spro.entity.User;
import ua.spro.model.action.UserAction;

import java.time.LocalDate;

public class AddTaskAction implements UserAction {

    private StringBuilder description;

    public AddTaskAction(String comment, LocalDate dateTo, User author, User executor) {
        description = new StringBuilder();
        description.
                append("Задача на ").
                append(dateTo).
                append(". ").
                append(comment).
                append(" для ").
                append(executor.getLogin()).
                append(" від ").
                append(author.getLogin());
    }

    @Override
    public String getDescription() {
        return description.toString();
    }
}
