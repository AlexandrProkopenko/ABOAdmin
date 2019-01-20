package ua.spro.entity.task;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

public class TaskExt extends Task {

    private StringProperty comment;
    private IntegerProperty authorId;

    public TaskExt(Integer id, LocalDateTime endDate, Integer executorId, Boolean done, String comment, Integer authorId) {
        super(id, endDate, executorId, done);
        this.comment = new SimpleStringProperty(this, "comment", comment);
        this.authorId = new SimpleIntegerProperty(this, "authorId", authorId);
    }

    public TaskExt(LocalDateTime endDate, Integer executorId, Boolean done, String comment, Integer authorId) {
        super(endDate, executorId, done);
        this.comment = new SimpleStringProperty(this, "comment", comment);
        this.authorId = new SimpleIntegerProperty(this, "authorId", authorId);
    }

    public TaskExt(Integer id, LocalDateTime endDate, Integer executorId, Boolean done) {
        super(id, endDate, executorId, done);
    }

    public TaskExt(LocalDateTime endDate, Integer executorId, Boolean done) {
        super(endDate, executorId, done);
    }

    public String getComment() {
        return comment.get();
    }

    public StringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    public int getAuthorId() {
        return authorId.get();
    }

    public IntegerProperty authorIdProperty() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId.set(authorId);
    }

    @Override
    public String toString() {

        return super.toString() +
                "TaskExt{" +
                "comment=" + comment +
                ", authorId=" + authorId +
                '}';
    }
}
