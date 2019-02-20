package ua.spro.entity.save;

import ua.spro.entity.User;
import ua.spro.entity.task.TaskSelectType;

import java.io.Serializable;
import java.time.LocalDate;

public class AdminSavedSettings implements Serializable {

    private boolean checkboxTasks;
    private FilterDate filterDate;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private TaskSelectType clientSelectType;
    private int authorId;
    private int executorId;
    private int statusId;
    private int departmentId;
    private TaskSelectType taskSelectType;
    private boolean checkboxNewTask;
    private int setTaskExecutor;
    private int setStatusId;
    private int setDepartmentId;

    public AdminSavedSettings(boolean checkboxTasks, FilterDate filterDate, LocalDate dateFrom, LocalDate dateTo,
                              TaskSelectType clientSelectType, int authorId, int executorId, int statusId, int departmentId,
                              TaskSelectType taskSelectType, boolean checkboxNewTask, int setTaskExecutor, int setStatusId, int setDepartmentId) {
        this.checkboxTasks = checkboxTasks;
        this.filterDate = filterDate;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.clientSelectType = clientSelectType;
        this.authorId = authorId;
        this.executorId = executorId;
        this.statusId = statusId;
        this.departmentId = departmentId;
        this.taskSelectType = taskSelectType;
        this.checkboxNewTask = checkboxNewTask;
        this.setTaskExecutor = setTaskExecutor;
        this.setStatusId = setStatusId;
        this.setDepartmentId = setDepartmentId;
    }

    public AdminSavedSettings(User currentUser) {
        this.checkboxTasks = false;
        this.filterDate = FilterDate.DAY;
        this.dateFrom = LocalDate.now();
        this.dateTo = LocalDate.now();
        this.clientSelectType = TaskSelectType.UNDONE;
        this.authorId = currentUser.getUserId();
        this.executorId = currentUser.getUserId();
        this.statusId = 1;
        this.departmentId = 1;
        this.taskSelectType = TaskSelectType.UNDONE;
        this.checkboxNewTask = true;
        this.setTaskExecutor = currentUser.getUserId();
        this.setStatusId = 1;
        this.setDepartmentId = 1;
    }

    public AdminSavedSettings() {
        this.checkboxTasks = false;
        this.filterDate = FilterDate.DAY;
        this.dateFrom = LocalDate.now();
        this.dateTo = LocalDate.now();
        this.clientSelectType = TaskSelectType.UNDONE;
        this.authorId = 1;
        this.executorId = 1;
        this.statusId = 1;
        this.departmentId = 1;
        this.taskSelectType = TaskSelectType.UNDONE;
        this.checkboxNewTask = true;
        this.setTaskExecutor = 1;
        this.setStatusId = 1;
        this.setDepartmentId = 1;
    }

    public boolean getCheckboxTasks() {
        return checkboxTasks;
    }

    public void setCheckboxTasks(boolean checkboxTasks) {
        this.checkboxTasks = checkboxTasks;
    }

    public FilterDate getFilterDate() {
        return filterDate;
    }

    public void setFilterDate(FilterDate filterDate) {
        this.filterDate = filterDate;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public TaskSelectType getClientSelectType() {
        return clientSelectType;
    }

    public void setClientSelectType(TaskSelectType clientSelectType) {
        this.clientSelectType = clientSelectType;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getExecutorId() {
        return executorId;
    }

    public void setExecutorId(int executorId) {
        this.executorId = executorId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public TaskSelectType getTaskSelectType() {
        return taskSelectType;
    }

    public void setTaskSelectType(TaskSelectType taskSelectType) {
        this.taskSelectType = taskSelectType;
    }

    public boolean getCheckboxNewTask() {
        return checkboxNewTask;
    }

    public void setCheckboxNewTask(boolean checkboxNewTask) {
        this.checkboxNewTask = checkboxNewTask;
    }

    public int getSetTaskExecutor() {
        return setTaskExecutor;
    }

    public void setSetTaskExecutor(int setTaskExecutor) {
        this.setTaskExecutor = setTaskExecutor;
    }

    public int getSetStatusId() {
        return setStatusId;
    }

    public void setSetStatusId(int setStatusId) {
        this.setStatusId = setStatusId;
    }

    public int getSetDepartmentId() {
        return setDepartmentId;
    }

    public void setSetDepartmentId(int setDepartmentId) {
        this.setDepartmentId = setDepartmentId;
    }

    @Override
    public String toString() {
        return "AdminSavedSettings{" +
                "checkboxTasks=" + checkboxTasks +
                ", filterDate=" + filterDate +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", clientSelectType=" + clientSelectType +
                ", authorId=" + authorId +
                ", executorId=" + executorId +
                ", statusId=" + statusId +
                ", departmentId=" + departmentId +
                ", taskSelectType=" + taskSelectType +
                ", checkboxNewTask=" + checkboxNewTask +
                ", setTaskExecutor=" + setTaskExecutor +
                ", setStatusId=" + setStatusId +
                ", setDepartmentId=" + setDepartmentId +
                '}';
    }
}
