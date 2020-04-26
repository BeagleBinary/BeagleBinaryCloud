package com.BeagleBinary.Dtos;

import com.BeagleBinary.Models.UserModel;

public class UserDto {

    public String id;
    public String userName;
    public String firstName;
    public String lastName;
    public String emailAddress;
    public String phoneNumber;
    public String password;
    public String deviceIdString;

    public UserDto(){ }


    public UserDto(UserModel userModel)
    {
        this.id = userModel.getId();
        this.userName = userModel.getUserName();
        this.firstName = userModel.getFirstName();
        this.lastName = userModel.getLastName();
        this.emailAddress = userModel.getEmailAddress();
        this.phoneNumber = userModel.getPhoneNumber();
        this.password = userModel.getPassword();
        this.deviceIdString = userModel.getDeviceIdString();
    }
}
