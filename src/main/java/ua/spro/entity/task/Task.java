package ua.spro.entity.task;

import javafx.beans.property.*;

import java.time.LocalDateTime;

public class Task {

    private IntegerProperty id;
    private ObjectProperty<LocalDateTime> endDate;
    private IntegerProperty executorId;
    private BooleanProperty done;


    public Task(Integer id, LocalDateTime endDate, Integer executorId, Boolean done) {
        this.id = new SimpleIntegerProperty(this, "id", id);
        this.endDate = new SimpleObjectProperty<>(this, "endDate", endDate);
        this.executorId = new SimpleIntegerProperty(this, "executorId", executorId);
        this.done = new SimpleBooleanProperty(this, "done", done);
    }

    public Task(LocalDateTime endDate, Integer executorId, Boolean done) {
        this.id = new SimpleIntegerProperty(this, "id");
        this.endDate = new SimpleObjectProperty<>(this, "endDate", endDate);
        this.executorId = new SimpleIntegerProperty(this, "executorId", executorId);
        this.done = new SimpleBooleanProperty(this, "done", done);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public LocalDateTime getEndDate() {
        return endDate.get();
    }

    public ObjectProperty<LocalDateTime> endDateProperty() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate.set(endDate);
    }

    public int getExecutorId() {
        return executorId.get();
    }

    public IntegerProperty executorIdProperty() {
        return executorId;
    }

    public void setExecutorId(int executorId) {
        this.executorId.set(executorId);
    }

    public boolean isDone() {
        return done.get();
    }

    public BooleanProperty doneProperty() {
        return done;
    }

    public void setDone(boolean done) {
        this.done.set(done);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", endDate=" + endDate.get() +
                ", executorId=" + executorId.get() +
                ", done=" + done.get() +
                '}';
    }
}
