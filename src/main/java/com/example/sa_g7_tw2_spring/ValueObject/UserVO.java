package com.example.sa_g7_tw2_spring.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class UserVO {

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


    private UserVO(userVOBuilder builder) {
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
    public static class userVOBuilder{

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

        public userVOBuilder setAccount(String account) { //setAccount
            this.account = account;
            return this;
        }

        public userVOBuilder setUserName(String userName) { //setUserName
            this.userName = userName;
            return this;
        }
        public userVOBuilder setPassword(String password) { //setPassword
            this.password = password;
            return this;
        }
        public userVOBuilder setGender(String gender) { //setGender
            this.gender = gender;
            return this;
        }

        public userVOBuilder setAge(int age) { //setAge
            this.age = age;
            return this;
        }
        public userVOBuilder setPhone(String phone) { //setPhone
            this.phone = phone;
            return this;
        }
        public userVOBuilder setFamilyID(String familyID) { //setFamilyID
            this.familyID = familyID;
            return this;
        }
        public userVOBuilder setFamilyName(String familyName) { //setFamilyName
            this.familyName = familyName;
            return this;
        }
        public userVOBuilder setFamilyPhone(String familyPhone) { //setFamilyPhone
            this.familyPhone = familyPhone;
            return this;
        }
        public userVOBuilder setToken(String token) { //setToken
            this.token = token;
            return this;
        }

        public UserVO build(){
            return new UserVO(this);
        } //build

    }

}
