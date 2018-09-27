package ua.spro.entity;

import javafx.beans.property.*;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;

public class Client {

private IntegerProperty id;
private StringProperty childName;
private DoubleProperty age;
private ObjectProperty<LocalDate> birthday;
private StringProperty parentName;
private StringProperty phone;
private StringProperty location;
private IntegerProperty departmentId;
private IntegerProperty statusId;


    public Client(Integer id, String childName,  LocalDate birthday, String parentName, String phone, String location, Integer departmentId, Integer statusId) {
        this.id = new SimpleIntegerProperty(this, "id", id);
        this.childName = new SimpleStringProperty(this, "childName", childName);
        this.age = new SimpleDoubleProperty(this, "age", calculateAgeByBirthday(birthday));
        this.birthday = new SimpleObjectProperty<>(this, "birthday", birthday);
        this.parentName = new SimpleStringProperty(this, "parentName", parentName);
        this.phone =new SimpleStringProperty(this, "phone",  phone);
        this.location = new SimpleStringProperty(this, "location",  location);
        this.departmentId = new SimpleIntegerProperty(this, "departmentId" , departmentId);
        this.statusId = new SimpleIntegerProperty(this, " statusId",  statusId);
    }

    public Client( String childName, Double age, String parentName, String phone, String location, Integer departmentId, Integer statusId) {
        this.childName = new SimpleStringProperty(this, "childName", childName);
        this.age = new SimpleDoubleProperty(this, "age", age);
        this.birthday = new SimpleObjectProperty<>(this, "birthday", calculateBirthdayByAge(age));
        this.parentName = new SimpleStringProperty(this, "parentName", parentName);
        this.phone =new SimpleStringProperty(this, "phone",  phone);
        this.location = new SimpleStringProperty(this, "location",  location);
        this.departmentId = new SimpleIntegerProperty(this, "departmentId" , departmentId);
        this.statusId = new SimpleIntegerProperty(this, " statusId",  statusId);
        this.id = new SimpleIntegerProperty(this, "id");
    }

    public Client( String childName, LocalDate birthday, String parentName, String phone, String location, Integer departmentId, Integer statusId) {
        this.childName = new SimpleStringProperty(this, "childName", childName);
        this.age = new SimpleDoubleProperty(this, "age", calculateAgeByBirthday(birthday));
        this.birthday = new SimpleObjectProperty<>(this, "birthday", birthday);
        this.parentName = new SimpleStringProperty(this, "parentName", parentName);
        this.phone =new SimpleStringProperty(this, "phone",  phone);
        this.location = new SimpleStringProperty(this, "location",  location);
        this.departmentId = new SimpleIntegerProperty(this, "departmentId" , departmentId);
        this.statusId = new SimpleIntegerProperty(this, " statusId",  statusId);
        this.id = new SimpleIntegerProperty(this, "id");
    }


    private Double calculateAgeByBirthday(LocalDate birthday){
        int year = birthday.getYear();
        int month = birthday.getMonthValue();
        LocalDate now = LocalDate.now();
        double result =  (double) ((now.getYear()- year ) + (double)(now.getMonthValue()-month)*1/12);
        DecimalFormat newFormat = new DecimalFormat("##.#");
//        String age = newFormat.format(result);
//        System.out.println(result);
//        System.out.println(newFormat.format(result));

        char c;
        String age = newFormat.format(result);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <age.length(); i++) {
            c = age.charAt(i);
            if(c == ',') c = '.';
            sb.append(c);
        }
            age = sb.toString();
        try{
            result = Double.parseDouble(age);
        }catch (Exception e){
            System.out.println("Неможливо перетворити вік в число!");
            e.printStackTrace();
        }

        return result;
    }

    private LocalDate calculateBirthdayByAge(Double age){
        double ageDouble = age;
        LocalDate result;

        LocalDate now = LocalDate.now();
        int year = (now.getYear() - (int) ageDouble);
        int ageInt = (int) ageDouble;
        int month = 1 + (int)(( ageDouble%ageInt *10)*1.3);
        if(month < now.getMonthValue()){
            month = now.getMonthValue() - month;

        }else{
            year-=1;
            month = 12 + (now.getMonthValue() - month);


        }
        result = LocalDate.of(year, month, 1);
//        System.out.println(year + " " + month);
        return result;
    }

    public Double getAge() {
        return age.getValue();
    }

    public void setAge(Double age) {
        this.age.set(age);
        birthday.set(calculateBirthdayByAge(age));
    }



    public Integer getDepartmentId() {
        return departmentId.getValue();
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId.setValue(departmentId);
    }

    public Integer getStatusId() {
        return statusId.get();
    }

    public void setStatusId(Integer statusId) {
        this.statusId.setValue(statusId);
    }

    public Integer getId() {
        return id.getValue();
    }

    public void setId(Integer id) {
        System.out.println(this.id);
        this.id.setValue(id);
    }

    public String getChildName() {
        return childName.get();
    }

    public void setChildName(String childName) {
        this.childName.setValue(childName);
    }

    public LocalDate getBirthday() {
        return birthday.getValue();
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
        setAge(calculateAgeByBirthday(birthday));
    }

    public String getParentName() {
        return parentName.getValue();
    }

    public void setParentName(String parentName) {
        this.parentName.setValue(parentName);
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.setValue(phone);
    }

    public String getLocation() {
        return location.getValue();
    }

    public void setLocation(String location) {
        this.location.setValue(location);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id.get() +
                ", childName='" + childName.get() + '\'' +
                ", age='" + age.get() + '\'' +
                ", birthday=" + birthday.get() +
                ", parentName='" + parentName.get() + '\'' +
                ", phone='" + phone.get() + '\'' +
                ", location='" + location.get() + '\'' +
                ", departmentId=" + departmentId.get() +
                ", statusId=" + statusId.get() +
                '}';
    }
}
