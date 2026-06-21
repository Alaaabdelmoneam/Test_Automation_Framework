package org.blazedemo.pages.dto;

import lombok.Getter;
import lombok.Setter;

import static org.blazedemo.utils.actions.ElementsActions.click;

@Getter @Setter
public class RegistrationData {
    private String title;
    private String name;
    private String email;
    private String password;
    private String birthDay;
    private String birthYear;
    private String birthMonth;
    private boolean newsletterSubscription;
    private boolean offersSubscription;
    private String firstName;
    private String lastName;
    private String company;
    private String firstAddress;
    private String secondAddress;

    private String country;
    private String state;
    private String city;
    private String zipcode;
    private String mobileNumber;

    public RegistrationData(
            String title, String name, String email, String password,
            String birthDay, String birthYear, String birthMonth,
            boolean newsletterSubscription, boolean offersSubscription,
            String firstName, String lastName,
            String company, String firstAddress, String secondAddress,
            String country, String state, String city, String zipcode, String mobileNumber)
    {
        this.title = title;
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthDay = birthDay;
        this.birthYear = birthYear;
        this.birthMonth = birthMonth;
        this.newsletterSubscription = newsletterSubscription;
        this.offersSubscription = offersSubscription;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.firstAddress = firstAddress;
        this.secondAddress = secondAddress;
        this.country = country;
        this.state = state;
        this.city = city;
        this.zipcode = zipcode;
        this.mobileNumber = mobileNumber;
    }
}
