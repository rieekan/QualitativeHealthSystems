package com.qualitativehealthsystems.models.requests;

/**
 * Created by bryan on 11/21/2015.
 */
public class AuthenticationRequest {
    public String UserName;
    public String Password;

    public AuthenticationRequest() {

    }

    public AuthenticationRequest(String userName, String password) {
        UserName = userName;
        Password = password;
    }
}
