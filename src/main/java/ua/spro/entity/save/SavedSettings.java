package ua.spro.entity.save;

import ua.spro.entity.User;
import ua.spro.entity.task.TaskSelectType;

import java.io.Serializable;


public class SavedSettings implements Serializable {

    private Screen screen;
    private AdminSavedSettings adminSavedSettings;
    private StatisticSavedSettings statisticSavedSettings;

    public SavedSettings(Screen screen, AdminSavedSettings adminSavedSettings, StatisticSavedSettings statisticSavedSettings) {
        this.screen = screen;
        this.adminSavedSettings = adminSavedSettings;
        this.statisticSavedSettings = statisticSavedSettings;
    }

    public SavedSettings() {
        screen = Screen.ADMIN_SCENE;
        adminSavedSettings = new AdminSavedSettings();
        statisticSavedSettings = new StatisticSavedSettings();
    }

    public SavedSettings(User currentUser) {
        screen = Screen.ADMIN_SCENE;
        adminSavedSettings = new AdminSavedSettings(currentUser);
        statisticSavedSettings = new StatisticSavedSettings(currentUser);
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public AdminSavedSettings getAdminSavedSettings() {
        return adminSavedSettings;
    }

    public void setAdminSavedSettings(AdminSavedSettings adminSavedSettings) {
        this.adminSavedSettings = adminSavedSettings;
    }

    public StatisticSavedSettings getStatisticSavedSettings() {
        return statisticSavedSettings;
    }

    public void setStatisticSavedSettings(StatisticSavedSettings statisticSavedSettings) {
        this.statisticSavedSettings = statisticSavedSettings;
    }

    @Override
    public String toString() {
        return "SavedSettings{" +
                "screen=" + screen +
                ", adminSavedSettings=" + adminSavedSettings +
                ", statisticSavedSettings=" + statisticSavedSettings +
                '}';
    }
}
