package ua.spro.service;

import javafx.collections.ObservableList;
import ua.spro.entity.statistic.ActionStatistic;

import java.time.LocalDateTime;

public interface ActionStatisticService {

    ObservableList<ActionStatistic> getDataByUserId(Integer userId);

    ObservableList<ActionStatistic> getDataByUserIdAndDate(Integer userId, LocalDateTime from, LocalDateTime to);

    ObservableList<ActionStatistic> getDataByDate(LocalDateTime from, LocalDateTime to);
}
