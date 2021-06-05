package gpse.example.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gpse.example.domain.users.PersonalData;

import java.time.LocalDate;

/**
 * A Container for all userdata sent from frontend.
 */
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

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

    public int getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(final int housenumber) {
        this.housenumber = housenumber;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(final int postcode) {
        this.postcode = postcode;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(final String hometown) {
        this.hometown = hometown;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(final String birthday) {
        this.birthday = birthday;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(final String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * generating an personaldata object from its data.
     * @return the personaldata object
     */
    @JsonIgnore
    public PersonalData generatePersonalData() {
        final PersonalData personalData = new PersonalData();
        if (housenumber != -1) {
            personalData.setHouseNumber(housenumber);
        }
        if (postcode != -1) {
            personalData.setPostCode(postcode);
        }
        if (hometown != null) {
            personalData.setHomeTown(hometown);
        }
        if (country != null) {
            personalData.setCountry(country);
        }
        if (birthday != null) {
            personalData.setBirthday(LocalDate.parse(birthday));
        }
        if (phonenumber != null) {
            personalData.setPhoneNumber(phonenumber);
        }
        if (street != null) {
            personalData.setStreet(street);
        }
        return personalData;
    }
}
