package ua.spro.service.jdbc;

import javafx.collections.ObservableList;
import ua.spro.dao.TaskDAO;
import ua.spro.dao.jdbc.TaskDAOImpl;
import ua.spro.entity.client.Client;
import ua.spro.entity.client.History;
import ua.spro.entity.task.Task;
import ua.spro.entity.task.TaskExt;
import ua.spro.entity.task.TaskSelectType;
import ua.spro.service.TaskService;

public class TaskServiceImpl implements TaskService {

    private TaskDAO dao;

    public TaskServiceImpl() {
        dao = new TaskDAOImpl();
    }

    @Override
    public Integer save(Task task) {
        return dao.save(task);
    }

    @Override
    public Task getById(Integer id) {
        return dao.getById(id);
    }

    @Override
    public boolean update(Task task) {
        return dao.update(task);
    }

    @Override
    public boolean updateDone(TaskExt taskExt) {
        return dao.updateDone(taskExt);
    }

    @Override
    public boolean delete(Task task) {
        return dao.delete(task);
    }

    @Override
    public ObservableList<Task> getAll() {
        return dao.getAll();
    }

    @Override
    public boolean saveLink(History history, Task task) {
        return dao.saveLink(history, task);
    }

    @Override
    public ObservableList<TaskExt> getByHistories(ObservableList<History> histories, TaskSelectType taskSelectType) {
        return dao.getByHistories(histories, taskSelectType);
    }
}
