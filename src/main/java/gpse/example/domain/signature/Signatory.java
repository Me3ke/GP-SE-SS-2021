package gpse.example.domain.signature;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * the class that models a signatory for a document.
 */
@Entity
public class Signatory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private String email;

    @Column
    private boolean status;

    @Column
    private LocalDateTime signedOn;

    @Column
    private SignatureType signatureType;


    /**
     * -1 = no reminder.
     */
    @Column
    private int reminder;

    /**
     * Default constructor for a Signatory. Status is initialized with false.
     * @param email the user that has to sign the corresponding document.
     * @param signatureType the signatureType the signatory refers to.
     */
    public Signatory(final String email, final SignatureType signatureType) {
        this.email = email;
        this.status = false;
        this.reminder = -1;
        this.signatureType = signatureType;
    }

    protected Signatory() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public boolean isStatus() {
        return status;
    }

    /**
     * Sets the status for a signatory. If the status is true
     * the signedOn gets the moment of status change as time.
     * @param status the new status.
     */
    public void setStatus(final boolean status) {
        if (status) {
            this.signedOn = LocalDateTime.now();
        }
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public int getReminder() {
        return reminder;
    }

    public void setReminder(int reminder) {
        this.reminder = reminder;
    }

    public LocalDateTime getSignedOn() {
        return signedOn;
    }

    public SignatureType getSignatureType() {
        return signatureType;
    }

    public void setSignatureType(final SignatureType signatureType) {
        this.signatureType = signatureType;
    }

    public ProtoSignatory toProtoSignatory() {
        return new ProtoSignatory(this.email, this.signatureType.toInteger());
    }
}
