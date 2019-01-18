package ua.spro.entity.client;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class History {

    private IntegerProperty id;
    private ObjectProperty <LocalDateTime> dateTime;
    private StringProperty comment;
    private IntegerProperty userId;

    public History(Integer id, LocalDateTime dateTime, String comment, Integer userId) {
        this.id = new SimpleIntegerProperty(this, "id", id);
        this.dateTime = new SimpleObjectProperty<>(this, "dateTime", dateTime);
        this.comment = new SimpleStringProperty(this, "comment",  comment);
        this.userId = new SimpleIntegerProperty(this, "userId", userId);
}

    public History(LocalDateTime dateTime, String comment, Integer userId) {
        this.dateTime = new SimpleObjectProperty<>(this, "dateTime", dateTime);
        this.comment = new SimpleStringProperty(this, "comment",  comment);
        this.userId = new SimpleIntegerProperty(this, "userId", userId);
        id = new SimpleIntegerProperty(this,"id");
    }

    public int getUserId() {
        return userId.get();
    }

    public void setUserId(Integer userId) {
        this.userId.setValue(userId);
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public ObjectProperty<LocalDateTime> dateTimeProperty() {
        return dateTime;
    }

    public Integer getId() {
        return id.get();
    }

    public void setId(Integer id) {
        this.id.setValue(id);
    }

    public LocalDateTime getDateTime() {
        return dateTime.get();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime.setValue(dateTime);
    }

    public String getComment() {
        return comment.getValue();
    }

    public void setComment(String comment) {
        this.comment.setValue(comment);
    }

    @Override
    public String toString() {
        return "History{" +
                "id=" + id.get() +
                ", dateTime=" + dateTime.get() +
                ", comment=" + comment.get() +
                ", userId=" + userId.get() +
                '}';
    }
}
