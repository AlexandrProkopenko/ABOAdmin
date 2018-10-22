package ua.spro.entity;

public class SubUser extends User {

    private String secondPassword;

    public SubUser(String login, String password, String secondPassword) {
        super(login, password);
        this.secondPassword = secondPassword;
    }

    public SubUser(Integer userId, String login, String password, String secondPassword) {
        super(userId, login, password);
        this.secondPassword = secondPassword;
    }

    public String getSecondPassword() {
        return secondPassword;
    }

    public void setSecondPassword(String secondPassword) {
        this.secondPassword = secondPassword;
    }


}
