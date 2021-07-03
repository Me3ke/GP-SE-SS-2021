package gpse.example.domain.documents;

import gpse.example.domain.signature.Signatory;
import gpse.example.domain.signature.SignatureType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The class responsible for handling the signatories associated to the document.
 */
@Entity
public class SignatoryManagement {

    /**
     * The id to identify the signatoryManagement; Is the same as the document id.
     */
    @Id
    @Column
    protected long id;

    /**
     * The list of signatories for this document.
     */
    @OneToMany(
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    protected List<Signatory> signatories;

    public SignatoryManagement(final long id, final List<Signatory> signatories) {
        this.id = id;
        this.signatories = signatories;
    }

    public SignatoryManagement() {

    }


    /**
     * adds a new user as a signatory to the signatory list.
     *
     * @param signatory     the user object that is needed as a signatory
     * @param signatureType the signatureType the signatory refers to
     */
    public void addSignatory(final String signatory, final SignatureType signatureType) {
        signatories.add(new Signatory(signatory, signatureType));
    }

    /**
     * The filter method for document signatories.
     *
     * @param signatories The list of signatories by which should be filtered.
     * @return true if this document contains one of the signatories of the filter.
     */
    public boolean hasSignatories(final List<String> signatories) {
        if (signatories == null) {
            return true;
        }
        for (final Signatory signatory : this.signatories) {
            if (signatories.contains(signatory.getEmail())) {
                return true;
            }
        }
        return false;
    }

    /**
     * The filter method for document readers.
     *
     * @param readers The list of reader by which should be filtered.
     * @return true if this document contains one of the readers of the filter.
     */
    public boolean hasReaders(final List<String> readers) {
        for (final String reader : readers) {
            for (final Signatory signatory : getReaders()) {
                if (signatory.getEmail().equals(reader)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * The filter method for signed.
     *
     * @param signed a boolean specifying the filter if the document is signed.
     * @return true if this document corresponds to the filter.
     */
    public boolean hasSigned(final boolean signed) {
        for (final Signatory signatory : this.signatories) {
            if (signed == signatory.isStatus()) {
                return true;
            }
        }
        return false;
    }


    /**
     * The method that returns the first signatory, that hasn't signed or reviewed.
     *
     * @return the first signatory, that hasn't signed or reviewed from any given list.
     */
    public Signatory getCurrentSignatory() {
        for (final Signatory signatory : signatories) {
            if (!signatory.isStatus()) {

                return signatory;

            }
        }
        return null;
    }

    /**
     * returns a list of only those signatories that have review as their signature Type.
     *
     * @return a list of only those signatories that have review as their signature Type
     */
    public List<Signatory> getReaders() {
        final List<Signatory> readers = new ArrayList<>();
        for (final Signatory signatory : signatories) {
            if (signatory.getSignatureType().equals(SignatureType.REVIEW)) {
                readers.add(signatory);
            }
        }
        return readers;
    }

    /**
     * returns a list of only those signatories that have simple signature as their signature Type.
     *
     * @return a list of only those signatories that have simple signature as their signature Type
     */
    public List<Signatory> getSimpleSignatories() {
        final List<Signatory> simpleSignatories = new ArrayList<>();
        for (final Signatory signatory : signatories) {
            if (signatory.getSignatureType().equals(SignatureType.SIMPLE_SIGNATURE)) {
                simpleSignatories.add(signatory);
            }
        }
        return simpleSignatories;
    }

    /**
     * returns a list of only those signatories that have advanced signature as their signature Type.
     *
     * @return a list of only those signatories that have advanced signature as their signature Type
     */
    public List<Signatory> getAdvancedSignatories() {
        final List<Signatory> advancedSignatories = new ArrayList<>();
        for (final Signatory signatory : signatories) {
            if (signatory.getSignatureType().equals(SignatureType.ADVANCED_SIGNATURE)) {
                advancedSignatories.add(signatory);
            }
        }
        return advancedSignatories;
    }

    public void setSigned(final int index) {
        signatories.get(index).setStatus(true);
    }

    public List<Signatory> getSignatories() {
        return signatories;
    }

    public void setSignatories(final List<Signatory> signatories) {
        this.signatories = signatories;
    }

}
