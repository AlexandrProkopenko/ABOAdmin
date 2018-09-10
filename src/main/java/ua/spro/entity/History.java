package ua.spro.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class History {

    private Integer id;
    private LocalDateTime dateTime;
    private String comment;

    public History(Integer id, LocalDateTime dateTime, String comment) {
        this.id = id;
        this.dateTime = dateTime;
        this.comment = comment;
    }

    public History(LocalDateTime dateTime, String comment) {
        this.dateTime = dateTime;
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", comment='" + comment + '\'' +
                '}';
    }
}
