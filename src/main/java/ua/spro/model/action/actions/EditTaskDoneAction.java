package ua.spro.model.action.actions;

import ua.spro.model.action.UserAction;

public class EditTaskDoneAction  implements UserAction {
    private StringBuilder description;

    public EditTaskDoneAction(String comment, Boolean newValue) {
        description = new StringBuilder();
        if(newValue) {
            description.
                    append("Виконано! \n").
                    append(comment);
        }else{
            description.
                    append("Задачу поновлено: \n").
                    append(comment);
        }
    }

    @Override
    public String getDescription() {
        return description.toString();
    }
}
