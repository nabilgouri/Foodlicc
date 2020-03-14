package com.example.foodlicc.model;

public class User {
    private String Email;
    private String familly_name;
    private String name;
    private String password;
    private String pic;
    private String phone;


    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public User() {
    }
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String name, String password, String phone) {
        this.name = name;
        this.password = password;
        this.phone = phone;
    }



    public User(String email, String familly_name, String name, String password,String phone) {
        Email = email;
        this.familly_name = familly_name;
        this.name = name;
        this.password = password;
        this.phone=phone;

    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public void setFamilly_name(String familly_name) {
        this.familly_name = familly_name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getFamilly_name() {
        return familly_name;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }
}
