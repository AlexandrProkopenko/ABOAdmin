package ua.spro.service.jdbc;

import javafx.collections.ObservableList;
import ua.spro.dao.ActionStatisticDAO;
import ua.spro.dao.jdbc.ActionStatisticDAOImpl;
import ua.spro.entity.statistic.ActionStatistic;
import ua.spro.service.ActionStatisticService;

import java.time.LocalDateTime;

public class ActionStatisticServiceImpl implements ActionStatisticService {

    private ActionStatisticDAO dao;

    public ActionStatisticServiceImpl() {
        dao = new ActionStatisticDAOImpl();
    }

    @Override
    public ObservableList<ActionStatistic> getDataByUserId(Integer userId) {
       return dao.getDataByUserId(userId);
    }

    @Override
    public ObservableList<ActionStatistic> getDataByUserIdAndDate(Integer userId, LocalDateTime from, LocalDateTime to) {
      return   dao.getDataByUserIdAndDate(userId, from, to);
    }

    @Override
    public ObservableList<ActionStatistic> getDataByDate(LocalDateTime from, LocalDateTime to) {
        return dao.getDataByDate(from, to);
    }
}
