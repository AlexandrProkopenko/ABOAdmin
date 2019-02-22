package ua.spro.entity.unit;

import ua.spro.entity.User;

public class ConnectedUnit {

    private Integer id;
    private String name;
    private boolean updated;

    public ConnectedUnit(User user) {
        name = user.getLogin() + System.currentTimeMillis();
        updated = true;
    }

    public ConnectedUnit(Integer id, String name, boolean updated) {
        this.id = id;
        this.name = name;
        this.updated = updated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "ConnectedUnit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", updated=" + updated +
                '}';
    }
}
