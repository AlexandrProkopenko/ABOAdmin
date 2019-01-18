package ua.spro.entity.statistic;

import javafx.beans.property.*;

import java.time.LocalDateTime;

public class ActionStatistic {

    private StringProperty author;
    private ObjectProperty< LocalDateTime> dateTime;
    private StringProperty clientDescription;
    private StringProperty comment;

    public ActionStatistic(String author, LocalDateTime dateTime, String clientDescription, String comment) {
        this.author = new SimpleStringProperty(this, "userId", author);
        this.dateTime = new SimpleObjectProperty<>(this, "dateTime", dateTime);
        this.clientDescription = new SimpleStringProperty(this, "clientDescription",  clientDescription);
        this.comment = new SimpleStringProperty(this, "comment",  comment);
    }

    public String getAuthor() {
        return author.get();
    }

    public StringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public LocalDateTime getDateTime() {
        return dateTime.get();
    }

    public ObjectProperty<LocalDateTime> dateTimeProperty() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime.set(dateTime);
    }

    public String getClientDescription() {
        return clientDescription.get();
    }

    public StringProperty clientDescriptionProperty() {
        return clientDescription;
    }

    public void setClientDescription(String clientDescription) {
        this.clientDescription.set(clientDescription);
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

    @Override
    public String toString() {
        return "ActionStatistic{" +
                "author=" + author +
                ", dateTime=" + dateTime +
                ", clientDescription=" + clientDescription +
                ", comment=" + comment +
                '}';
    }
}
