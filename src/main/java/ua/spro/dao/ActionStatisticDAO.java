package ua.spro.dao;

import javafx.collections.ObservableList;
import ua.spro.entity.statistic.ActionStatistic;

import java.time.LocalDateTime;

public interface ActionStatisticDAO {

    ObservableList<ActionStatistic> getDataByUserId(Integer userId);

    ObservableList<ActionStatistic> getDataByUserIdAndDate(Integer userId, LocalDateTime from, LocalDateTime to);

    ObservableList<ActionStatistic> getDataByDate(LocalDateTime from, LocalDateTime to);
}
