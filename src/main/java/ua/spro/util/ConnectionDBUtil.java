package ua.spro.util;

public class ConnectionDBUtil {

    private static String url = "jdbc:mysql://localhost:3306/abo?serverTimezone=UTC&useSSL=false";
    private static String login = "root";
    private static String password = "1111";

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        ConnectionDBUtil.url = url;
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        ConnectionDBUtil.login = login;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        ConnectionDBUtil.password = password;
    }
}
