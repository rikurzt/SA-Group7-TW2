package com.example.sa_g7_tw2_spring.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class UserVO2 {

    private final String account;
    private final String userName;
    private final String password;
    private final String gender;
    private final int age;
    private final String phone;
    private final String familyID;
    private final String familyName;
    private final String familyPhone;
    private final String token;


    public String getAccount() {
        return account;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getFamilyID() {
        return familyID;
    }

    public String getFamilyName() {
        return familyID;
    }

    public String getFamilyPhone() {
        return familyPhone;
    }

    public String getToken() {
        return token;
    }


    private UserVO2(userVO2Builder builder) {
        this.account=builder.account;
        this.userName=builder.userName;
        this.password=builder.password;
        this.gender=builder.gender;
        this.age=builder.age;
        this.phone=builder.phone;
        this.familyID=builder.familyID;
        this.familyName=builder.familyName;
        this.familyPhone=builder.familyPhone;
        this.token=builder.token;
    }

    //Builder Class
    public static class userVO2Builder{

        // required parameters
        private String account;
        private String userName;
        private String password;
        private String gender;
        private int age;
        private String phone;
        private String familyID;
        private String familyName;
        private String familyPhone;
        private String token;
//        public userVO2Builder(){ //constructor
//
//        }

        public userVO2Builder setAccount(String account) { //setAccount
            this.account = account;
            return this;
        }

        public userVO2Builder setUserName(String userName) { //setUserName
            this.userName = userName;
            return this;
        }
        public userVO2Builder setPassword(String password) { //setPassword
            this.password = password;
            return this;
        }
        public userVO2Builder setGender(String gender) { //setGender
            this.gender = gender;
            return this;
        }

        public userVO2Builder setAge(int age) { //setAge
            this.age = age;
            return this;
        }
        public userVO2Builder setPhone(String phone) { //setPhone
            this.phone = phone;
            return this;
        }
        public userVO2Builder setFamilyID(String familyID) { //setFamilyID
            this.familyID = familyID;
            return this;
        }
        public userVO2Builder setFamilyName(String familyName) { //setFamilyName
            this.familyName = familyName;
            return this;
        }
        public userVO2Builder setFamilyPhone(String familyPhone) { //setFamilyPhone
            this.familyPhone = familyPhone;
            return this;
        }
        public userVO2Builder setToken(String token) { //setToken
            this.token = token;
            return this;
        }

        public UserVO2 build(){
            return new UserVO2(this);
        } //build

    }

}
