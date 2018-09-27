package ua.spro.entity;

import java.io.Serializable;

public class DBConnection implements Serializable {

    private String name="";
    private String host;
    private String dataBase;
    private String port;
    private String user;
    private String password;
    private String url;
    private String addToURL = "?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";

    public DBConnection(String name, String host, String dataBase, String port, String user, String password, String url) {
        this.name = name;
        this.host = host;
        this.dataBase = dataBase;
        this.port = port;
        this.user = user;
        this.password = password;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddToURL() {
        return addToURL;
    }

    public void setAddToURL(String addToURL) {
        this.addToURL = addToURL;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getFullURL(){
        return url + addToURL;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        DBConnection connection = (DBConnection)obj;
        if (connection != null)
        return this.getName().equals(connection.getName());
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
