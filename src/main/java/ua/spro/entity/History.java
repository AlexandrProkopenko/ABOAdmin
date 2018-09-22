package ua.spro.entity;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class History {

    private IntegerProperty id;
    private ObjectProperty <LocalDateTime> dateTime;
    private StringProperty comment;

    public History(Integer id, LocalDateTime dateTime, String comment) {
        this.id = new SimpleIntegerProperty(this, "id", id);
        this.dateTime = new SimpleObjectProperty<>(this, "dateTime", dateTime);
        this.comment = new SimpleStringProperty(this, "comment",  comment);
    }

    public History(LocalDateTime dateTime, String comment) {
        this.dateTime = new SimpleObjectProperty<>(this, "dateTime", dateTime);
        this.comment = new SimpleStringProperty(this, "comment",  comment);
        id = new SimpleIntegerProperty(this,"id");
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
                ", comment='" + comment.get() + '\'' +
                '}';
    }
}
