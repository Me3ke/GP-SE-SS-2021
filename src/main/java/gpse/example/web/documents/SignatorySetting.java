package gpse.example.web.documents;

import gpse.example.domain.signature.Signatory;
import gpse.example.domain.signature.SignatureType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The class used to send the current settings of a signatory to the frontend.
 */
public class SignatorySetting {

    private String username;
    private SignatureType signatureType;
    private boolean status;
    private boolean remind;
    private int reminderTiming;
    private String signedOn;

    /**
     * The standard constructor.
     * @param signatory the relating signatory.
     */
    public SignatorySetting(final Signatory signatory) {
        this.username = signatory.getUser().getUsername();
        this.signatureType = signatory.getSignatureType();
        this.status = signatory.isStatus();
        this.remind = (signatory.getReminder() == -1);
        this.reminderTiming = signatory.getReminder();
        if (signatory.isStatus()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            this.signedOn = signatory.getSignedOn().format(formatter);
        } else {
            this.signedOn = "";
        }
    }

    public LocalDateTime convertSignedOn() {
        return LocalDateTime.parse(signedOn, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public SignatureType getSignatureType() {
        return signatureType;
    }

    public void setSignatureType(final SignatureType signatureType) {
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
