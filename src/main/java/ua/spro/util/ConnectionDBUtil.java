package ua.spro.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Observable;

public class ConnectionDBUtil extends Observable {

    private  String url = "jdbc:mysql://192.168.1.34:3306/abo?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";
    private  String login = "remoteUser";
    private  String password = "password";

    private static ConnectionDBUtil uniqueInstance = new ConnectionDBUtil();

    private ConnectionDBUtil() {
    }

    public static ConnectionDBUtil getInstance(){
        return uniqueInstance;
    }


    public  String getUrl() {
        return url;
    }

    public  void setUrl(String url) {
        this.url = url;
        setChanged();
        notifyObservers();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
        setChanged();
        notifyObservers();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        setChanged();
        notifyObservers();
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
