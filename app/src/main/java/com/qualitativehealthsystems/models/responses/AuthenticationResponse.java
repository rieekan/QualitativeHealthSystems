package com.qualitativehealthsystems.models.responses;

/**
 * Created by bryan on 11/21/2015.
 */
public class AuthenticationResponse {
    public boolean IsAuthenticated;


    public class User {
        public String _UserName;
        public String _Password;
    }
}
