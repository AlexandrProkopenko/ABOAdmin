package ua.spro.entity.save;

import ua.spro.entity.User;

import java.io.Serializable;
import java.time.LocalDate;

public class StatisticSavedSettings implements Serializable {

    private int userId;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public StatisticSavedSettings(int userId, LocalDate dateFrom, LocalDate dateTo) {
        this.userId = userId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public StatisticSavedSettings() {
        this.userId = 1;
        this.dateFrom = LocalDate.now().minusMonths(1);
        this.dateTo = LocalDate.now();
    }

    public StatisticSavedSettings(User currentUser) {
        this.userId = currentUser.getUserId();
        this.dateFrom = LocalDate.now().minusMonths(1);
        this.dateTo = LocalDate.now();
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "StatisticSavedSettings{" +
                "userId=" + userId +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                '}';
    }
}
