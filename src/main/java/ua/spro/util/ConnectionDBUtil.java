package ua.spro.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class ConnectionDBUtil {

    private static String url = "jdbc:mysql://192.168.1.34:3306/abo?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";
    private static String login = "remoteUser";
    private static String password = "password";

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


    public static String getCurrentIp(){
        URL whatismyip = null;
        String ip = null; //you get the IP as a String
        try {
            whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = null;
            in = new BufferedReader(new InputStreamReader(
            whatismyip.openStream()));
            ip = in.readLine();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(ip);
        return ip;
    }


}
