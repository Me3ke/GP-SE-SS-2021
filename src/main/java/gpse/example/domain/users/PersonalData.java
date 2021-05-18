package gpse.example.domain.users;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * the class that should contain all data concerning a user that is not necessarily needed.
 */
@Entity
public class PersonalData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private String street;

    @Column
    private int houseNumber;

    @Column
    private int postCode;

    @Column
    private int phoneNumber;

    @Column
    private String homeTown;

    @Column
    private String country;

    @Column
    private LocalDate birthday;

    /**
     * the standard constructor for personal data.
     * @param street
     * @param houseNumber
     * @param postCode
     * @param homeTown
     * @param country
     * @param birthday
     * @param phoneNumber
     */
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

    protected PersonalData() {

    }

    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(final int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(final int postCode) {
        this.postCode = postCode;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(final String homeTown) {
        this.homeTown = homeTown;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(final LocalDate birthday) {
        this.birthday = birthday;
    }
}
