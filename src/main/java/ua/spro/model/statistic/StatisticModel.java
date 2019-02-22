package ua.spro.model.statistic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ua.spro.entity.User;
import ua.spro.entity.statistic.ActionStatistic;
import ua.spro.service.ActionStatisticService;
import ua.spro.service.jdbc.ActionStatisticServiceImpl;
import ua.spro.util.ConnectionDBUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class StatisticModel implements StatisticModelInterface {

   private ObservableList<ActionStatistic> dataList;
   private ActionStatisticService actionStatisticService;
   public final static LocalDateTime MIN_DATE = LocalDateTime.of(2019,1, 1, 0, 0);
   public final static LocalDateTime MAX_DATE = LocalDateTime.now();

    public StatisticModel() {
        dataList = FXCollections.observableArrayList();
        actionStatisticService = new ActionStatisticServiceImpl();
        if(ConnectionDBUtil.getInstance().isConnected()){
            dataList.addAll(actionStatisticService.getDataByDate(MIN_DATE, MAX_DATE));
        }
    }

    @Override
    public ObservableList<ActionStatistic> getDataList() {
        return dataList;
    }

    @Override
    public boolean getData(User user) {
        System.out.println("statistic model   getData " + user);

        return false;
    }

    @Override
    public boolean getData(LocalDate from, LocalDate to) {
        System.out.println("statistic model   getData " + " from " + from + " to " + to);
        dataList.clear();
        LocalTime timeFrom, timeTo;
        timeFrom = LocalTime.of(00,00,0);
        timeTo = LocalTime.of(23,59,59);
        System.out.println(timeFrom + "   " + timeTo);
        dataList.addAll( actionStatisticService.getDataByDate( LocalDateTime.of(from, timeFrom) , LocalDateTime.of(to, timeTo)) );
        return false;
    }

    @Override
    public boolean getData(User user, LocalDate from, LocalDate to) {
        System.out.println("statistic model   getData " + user + " from " + from + " to " + to);
        dataList.clear();
        LocalTime timeFrom, timeTo;
        timeFrom = LocalTime.of(00,00,0);
        timeTo = LocalTime.of(23,59,59);
        System.out.println(timeFrom + "   " + timeTo);
        if(user.getLogin().equals("Всі")){
            dataList.addAll( actionStatisticService.getDataByDate( LocalDateTime.of(from, timeFrom) , LocalDateTime.of(to, timeTo)) );
        }else {
            dataList.addAll(actionStatisticService.getDataByUserIdAndDate(user.getUserId(), LocalDateTime.of(from, timeFrom), LocalDateTime.of(to, timeTo)));
        }
        return false;
    }

    @Override
    public void reloadLists() {
        if(ConnectionDBUtil.getInstance().isConnected()){
            dataList.clear();
            dataList.addAll(actionStatisticService.getDataByDate(MIN_DATE, MAX_DATE));
        }
    }
}
