package gpse.example.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gpse.example.domain.users.PersonalData;

import java.time.LocalDate;

public class UserSignUpCmd {

    private String username;
    private String firstname;
    private String lastname;
    private String password;

    private String street;
    private int housenumber;
    private int postcode;
    private String hometown;
    private String country;
    private String birthday;
    private String phonenumber;

    public UserSignUpCmd() {
        housenumber = -1;
        postcode = -1;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(int housenumber) {
        this.housenumber = housenumber;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public PersonalData getPersonalData() {
        PersonalData personalData = new PersonalData();
        if(housenumber != -1)
            personalData.setHouseNumber(housenumber);
        if(postcode != -1)
            personalData.setPostCode(postcode);
        if(!hometown.isEmpty())
            personalData.setHomeTown(hometown);
        if(!country.isEmpty())
            personalData.setCountry(country);
        if(!birthday.isEmpty())
            personalData.setBirthday(LocalDate.parse(birthday));
        if(!phonenumber.isEmpty())
            personalData.setPhoneNumber(phonenumber);
        if(!street.isEmpty())
            personalData.setStreet(street);

        return personalData;
    }
}
