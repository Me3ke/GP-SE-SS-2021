package gpse.example.domain;

import java.time.LocalDate;

public class PersonalData {
    private String street;
    private int houseNumber;
    private int postCode;
    private int phoneNumber;
    private String homeTown;
    private String country;
    private LocalDate birthday;

    public PersonalData(final String street, final int houseNumber, final int postCode,
                        final String homeTown, final String country, final LocalDate birthday,
                        final int phoneNumber) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.postCode = postCode;
        this.phoneNumber = phoneNumber;
        this.homeTown = homeTown;
        this.country = country;
        this.birthday = birthday;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
