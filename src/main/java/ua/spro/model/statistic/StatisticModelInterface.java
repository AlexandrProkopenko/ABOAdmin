package ua.spro.model.statistic;

import javafx.collections.ObservableList;
import ua.spro.entity.User;
import ua.spro.entity.statistic.ActionStatistic;

import java.time.LocalDate;

public interface StatisticModelInterface {

    ObservableList<ActionStatistic> getDataList();

    boolean getData(User user);

    boolean getData(LocalDate from, LocalDate to);

    boolean getData(User user, LocalDate from, LocalDate to);


    void reloadLists();
}
