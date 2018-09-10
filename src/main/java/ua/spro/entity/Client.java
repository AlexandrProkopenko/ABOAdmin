package ua.spro.entity;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;

public class Client {

private Integer id;
private String childName;
private String age;
private LocalDate birthday;
private String parentName;
private String phone;
private String location;
private Integer departmentId;
private Integer statusId;


    public Client(Integer id, String childName,  LocalDate birthday, String parentName, String phone, String location, Integer departmentId, Integer statusId) {
        this.id = id;
        this.childName = childName;
        this.age = calculateAgeByBirthday(birthday);
        this.birthday = birthday;
        this.parentName = parentName;
        this.phone = phone;
        this.location = location;
        this.departmentId = departmentId;
        this.statusId = statusId;
    }

    private String calculateAgeByBirthday(LocalDate birthday){
        int year = birthday.getYear();
        int month = birthday.getMonthValue();
        LocalDate now = LocalDate.now();
        double result =  (double) ((now.getYear()- year ) + (double)(now.getMonthValue()-month)*1/12);
        String formattedDouble = new DecimalFormat("#0.0").format(result);
//        System.out.println(formattedDouble);
        return formattedDouble;
    }

    private LocalDate calculateBirthdayByAge(String age){
        double ageDouble = 0;
        LocalDate result;
        char c;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <age.length(); i++) {
            c = age.charAt(i);
            if(c == ',') c = '.';
            sb.append(c);
        }
            age = sb.toString();
        try{
            ageDouble = Double.parseDouble(age);
        }catch (Exception e){
            System.out.println("Неможливо перетворити вік в число!");
            e.printStackTrace();
        }
        LocalDate now = LocalDate.now();
        int year = (now.getYear() - (int) ageDouble);
        int ageInt = (int) ageDouble;
        int month = 1 + (int)(( ageDouble%ageInt *10)*1.3);
        if(month < now.getMonthValue()){
            month = now.getMonthValue() - month;
            result = LocalDate.of(year,  month, 1);
        }else{
            year-=1;
            month = 12 + (now.getMonthValue() - month);
            result = LocalDate.of(year, month, 1);
        }
        result = LocalDate.of(year, month, 1);
//        System.out.println(year + " " + month);
        return result;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
        birthday = calculateBirthdayByAge(age);
    }

    public Client( String childName, String age, String parentName, String phone, String location, Integer departmentId, Integer statusId) {
        this.childName = childName;
        this.age = age;
        this.parentName = parentName;
        this.phone = phone;
        this.location = location;
        this.departmentId = departmentId;
        this.statusId = statusId;
        birthday = calculateBirthdayByAge(age);
    }

    public Client( String childName, LocalDate birthday, String parentName, String phone, String location, Integer departmentId, Integer statusId) {
        this.childName = childName;
        this.birthday = birthday;
        this.parentName = parentName;
        this.phone = phone;
        this.location = location;
        this.departmentId = departmentId;
        this.statusId = statusId;
        age = calculateAgeByBirthday(birthday);
    }

    public Client(Integer id, String childName, String age, String parentName, String phone, String location) {
        this.id = id;
        this.childName = childName;
        this.age = age;
        this.parentName = parentName;
        this.phone = phone;
        this.location = location;
        birthday = calculateBirthdayByAge(age);
    }

    public Client(Integer id, String childName, LocalDate birthday, String parentName, String phone, String location) {
        this.id = id;
        this.childName = childName;
        this.birthday = birthday;
        this.parentName = parentName;
        this.phone = phone;
        this.location = location;
        age = calculateAgeByBirthday(birthday);
    }

    public Client(String childName, LocalDate birthday, String parentName, String phone, String location) {
        this.childName = childName;
        this.birthday = birthday;
        this.parentName = parentName;
        this.phone = phone;
        this.location = location;
        age = calculateAgeByBirthday(birthday);
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
        setAge(calculateAgeByBirthday(birthday));
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", childName='" + childName + '\'' +
                ", age='" + age + '\'' +
                ", birthday=" + birthday +
                ", parentName='" + parentName + '\'' +
                ", phone='" + phone + '\'' +
                ", location='" + location + '\'' +
                ", departmentId=" + departmentId +
                ", statusId=" + statusId +
                '}';
    }
}
