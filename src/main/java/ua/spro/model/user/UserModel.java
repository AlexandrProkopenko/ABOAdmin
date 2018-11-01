package ua.spro.model.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ua.spro.entity.SubUser;
import ua.spro.entity.User;
import ua.spro.service.UserService;
import ua.spro.service.impl.UserServiceImpl;
import ua.spro.util.ConnectionDBUtil;

import java.io.*;
import java.util.Observable;

public class UserModel extends Observable implements UserModelInterface  {

    private UserState userState;
    private User currentUser;
    private ObservableList<User> users;
    private UserService userService;
    private User noUser;
    private static final String fileName = "users.ser";
    private static final String filePath = "sys/";
    private static File file;

    public UserModel() {
//        addObserver(observer);
        noUser = new User(1, "null", null);
        userState = UserState.NOT_ENTERED;

        userService = new UserServiceImpl();
        users = FXCollections.observableArrayList();
        if(ConnectionDBUtil.getInstance().isConnected()) {
            users.addAll(userService.getAll());
            System.out.println(users);
        }else {
            users.add(noUser);
        }
        fileSetup();

        currentUser = loadUserFromFile();
        System.out.println(currentUser);
            if (!currentUser.getLogin().equals("null")) {

                changeState(UserState.ENTERED);
            }
        System.out.println(currentUser.getUserId() + " " + currentUser.getLogin() + " " + currentUser.getPassword());
        System.out.println(users.get(0).getUserId() + " " + users.get(0).getLogin() + " " + users.get(0).getPassword());
        if(currentUser != noUser) {
            setCurrentUserId();
        }
        System.out.println("UserModel Construction compleet");
    }

    private boolean setCurrentUserId(){
        if(currentUser == null) return false;
        System.out.println(users);
        System.out.println(currentUser);
        for(User user: users){
            System.out.println(user.getUserId()+ " " + user.getLogin() + " " + user.getPassword());
//            System.out.println(currentUser.getUserId()+ " " + user.getLogin() + " " + user.getPassword());
            if(currentUser.getLogin().equals(user.getLogin()) && currentUser.getPassword().equals(user.getPassword())){
                currentUser.setUserId(user.getUserId());
                return true;
            }
        }
        return false;
    }

    @Override
    public void changeState(UserState state){
        userState = state;
        setChanged();
        notifyObservers();
        System.out.println("UserModel :Change state  " + userState);
    }

    public void changeState(){
        setChanged();
        notifyObservers();
        System.out.println("UserModel :Change state  " + userState);
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

    private boolean saveUserToFile(User user){
        try (FileOutputStream fos = new FileOutputStream(file)) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(user);
        }catch (FileNotFoundException e){
            e.printStackTrace();
            return false;
        }catch (IOException ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    private User loadUserFromFile(){
        User user = noUser;
        try{
            FileInputStream fis = new FileInputStream(file);
            if(fis.available() == 0) return user;
            ObjectInputStream ois = new ObjectInputStream(fis);


            boolean check=true;
            while (check) {
                try {
                    user = (User)ois.readObject();
                }catch(EOFException ex){
                    check = false;
                }
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
        System.err.println("IO exception");
        e.printStackTrace();
        }
        if(user == null) return noUser;

        return user;
    }

    @Override
    public ObservableList<User> getAllUsers() {
        return users;
    }

    @Override
    public Integer save(User user) {



        return userService.save(user);
    }

    @Override
    public boolean update(SubUser subUser) {
//        System.out.println(subUser.getUserId());
        if(subUser.getPassword().equals(subUser.getSecondPassword())){
//            System.out.println("Current user id" + currentUser.getUserId() + " login "+currentUser.getLogin()+" password " + currentUser.getPassword());
//            currentUser = userService.getUserWithId(currentUser);
            subUser.setUserId(currentUser.getUserId());
//            System.out.println("Current user id" + currentUser.getUserId() + " login "+currentUser.getLogin()+" password " + currentUser.getPassword());
            User newUser = new User(subUser);
//            System.out.println("UserModel \nid = "+newUser.getUserId() + "\nlogin = "+ newUser.getLogin()+"\npassword =" + newUser.getPassword());
//            System.out.println(newUser.getUserId() + "new login  " + newUser.getLogin()+ "  newPassword " + newUser.getPassword());
            if(userService.update(newUser)){

                users.clear();
                users.addAll(userService.getAll());
                setCurrentUser(newUser);
                changeState(UserState.ENTERED);
                return true;
            }
        }
        return false;
    }

    @Override
    public UserState getUserState() {
        return userState;
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void setCurrentUser(User user) {
        currentUser = user;
        if(currentUser != noUser) {
            setCurrentUserId();
        }
        saveUserToFile(currentUser);
    }

    @Override
    public boolean checkAccess(User user) {
        for(User u: users){
            if(u.getLogin().equals(user.getLogin()) && u.getPassword().equals(user.getPassword())){
                setCurrentUser(user);
                changeState(UserState.ENTERED);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean exit() {
        setCurrentUser(noUser);
        changeState(UserState.NOT_ENTERED);
        return false;
    }

    @Override
    public boolean editing() {
        changeState(UserState.EDITING);
        return true;
    }

    @Override
    public boolean createUser(SubUser subUser) {
        if(subUser.getPassword().equals(subUser.getSecondPassword())) {
            User newUser = new User(subUser);
            if( userService.save(newUser) != null) {
                setCurrentUser(newUser);
                users.clear();
                users.addAll(userService.getAll());
                System.out.println(users);
                changeState(UserState.ENTERED);
                return true;
            }
        }

        return false;
    }
}
