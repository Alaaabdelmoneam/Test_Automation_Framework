package org.blazedemo.pages.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginCredentials {
    private String email;
    private String password;

    public LoginCredentials(){}


    public LoginCredentials(String email, String password){
        this.email = email;
        this.password = password;
    }
}
