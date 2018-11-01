package ua.spro.entity;

import java.io.Serializable;

public class User implements Serializable {
    private Integer userId;
    private String login;
    private String password;

    public User() {
    }

    public User(SubUser subUser) {
        userId = subUser.getUserId();
        login = subUser.getLogin();
        password = subUser.getPassword();
    }



    public User(Integer userId, String login, String password) {
        this.userId = userId;
        this.login = login;
        this.password = password;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return getLogin();
    }
}
