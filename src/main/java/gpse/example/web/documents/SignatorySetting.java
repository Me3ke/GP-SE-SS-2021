package gpse.example.web.documents;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gpse.example.domain.exceptions.SignatureTypeFromIntegerException;
import gpse.example.domain.signature.Signatory;
import gpse.example.domain.signature.SignatureType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The class used to send the current settings of a signatory to the frontend.
 */
public class SignatorySetting {

    private String email;
    private int signatureType;
    private boolean status;
    private boolean remind;
    private int reminderTiming;
    private String signedOn;

    /**
     * The standard constructor.
     *
     * @param signatory the relating signatory.
     */
    public SignatorySetting(final Signatory signatory) {
        this.email = signatory.getEmail();
        this.signatureType = signatory.getSignatureType().toInteger();
        this.status = signatory.isStatus();
        this.remind = signatory.getReminder() != -1;
        this.reminderTiming = signatory.getReminder();
        if (signatory.isStatus()) {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            this.signedOn = signatory.getSignedOn().format(formatter);
        } else {
            this.signedOn = "";
        }
    }

    /**
     * Empty Constructor for objects created from JSON.
     */
    public SignatorySetting() {
    }

    /**
     * the method used to convert the String, that we get from the frontend to LocalDateTime.
     *
     * @return the time the user signed on as LocalDateTime
     */
    public LocalDateTime convertSignedOn() {
        if (signedOn.equals("")) {
            return null;
        }
        try {
            return LocalDateTime.parse(signedOn, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (DateTimeParseException dtpe) {
            return LocalDateTime.parse(signedOn, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public int getSignatureType() {
        return signatureType;
    }

    /**
     * The Method used to convert the int that represents the signature Type to the enum representation.
     *
     * @return the enum representation of the signaturetype.
     */
    @JsonIgnore
    public SignatureType getSignatureTypeAsEnum() {
        try {
            return SignatureType.fromInteger(signatureType);
        } catch (SignatureTypeFromIntegerException e) {
            return SignatureType.NO_SIGNATURE;
        }
    }

    public void setSignatureType(final int signatureType) {
        this.signatureType = signatureType;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(final boolean status) {
        this.status = status;
    }

    public boolean isRemind() {
        return remind;
    }

    public void setRemind(final boolean remind) {
        this.remind = remind;
    }

    public int getReminderTiming() {
        return reminderTiming;
    }

    public void setReminderTiming(final int reminderTiming) {
        this.reminderTiming = reminderTiming;
    }

    public String getSignedOn() {
        return signedOn;
    }

    public void setSignedOn(final String signedOn) {
        this.signedOn = signedOn;
    }
}
