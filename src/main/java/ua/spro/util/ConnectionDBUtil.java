package ua.spro.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ua.spro.entity.DBConnection;

import java.io.*;
import java.net.*;
import java.util.Observable;

public class ConnectionDBUtil extends Observable {

    private  String url;
//    = "jdbc:mysql://192.168.1.34:3306/abo?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";
    private  String login;
//    = "remoteUser";
    private  String password;
//    = "password";

    private static final String fileName = "connections.ser";
    private static final String filePath = "sys/";
    private static File file;
    private DBConnection connection;

    private static ConnectionDBUtil uniqueInstance = new ConnectionDBUtil();

    private ConnectionDBUtil() {
        fileSetup();
        connection = loadSavedConnectionsList().get(0);
        if(connection != null){
            url = connection.getFullURL();
            login = connection.getUser();
            password = connection.getPassword();
        }
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

    private void fileSetup(){
        file = new File(filePath);
        if(!file.exists()){
            file.mkdir();
        }
        file = new File(filePath, fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static ObservableList<DBConnection> loadSavedConnectionsList(){
        ObservableList<DBConnection> list = FXCollections.observableArrayList();
        try{
            FileInputStream fis = new FileInputStream(file);
            if(fis.available() == 0) return list;
            ObjectInputStream ois = new ObjectInputStream(fis);
            boolean check=true;
            while (check) {
                try {
                    Object object = ois.readObject();
                    list.add((DBConnection) object);
                }catch(EOFException ex){
                    check = false;
                }
            }
        }catch (IOException e){
            System.err.println("IO exception");
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return list;
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

    public static boolean saveConnectionsToFile(ObservableList<DBConnection> list, DBConnection currentConnection){
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        if(currentConnection != null) {
            list.remove(currentConnection);
            list.add(0, currentConnection);
        }
        try (FileOutputStream fos = new FileOutputStream(file)) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for(DBConnection connection: list) {
                oos.writeObject(connection);
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
            return false;
        }catch (IOException ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }


}
