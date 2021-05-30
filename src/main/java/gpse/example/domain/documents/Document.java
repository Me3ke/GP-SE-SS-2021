package gpse.example.domain.documents;

import gpse.example.domain.signature.AdvancedSignature;
import gpse.example.domain.signature.Signatory;
import gpse.example.domain.signature.SignatureType;
import gpse.example.domain.users.User;

import javax.persistence.*;
import java.io.*;
import java.security.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The model for the document responsible for initialising the necessary details about the document file.
 */
@Entity
public class Document {

    //needed for verification
    private static final String SIGNING_ALGORITHM = "SHA256withRSA";
    /**
     * The documentMetaData containing the identifier as well as other information.
     * The path leading to the document.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @OneToOne
    private DocumentMetaData documentMetaData;

    @OneToMany
    private List<Signatory> signatories = new ArrayList<>();

    @OneToMany
    private List<AdvancedSignature> advancedSignatures = new ArrayList<>();

    @OneToMany
    private List<Signatory> readers = new ArrayList<>();

    /*
    @Column
    private File documentFile;

     */

    @Column
    private String documentType;

    @Column
    private SignatureType signatureType = SignatureType.NO_SIGNATURE;

    @Lob
    private byte[] data;

    @Column
    private boolean orderRelevant;

    @Column
    private LocalDateTime endDate;

    @Column
    private DocumentState state;

    public Document() {
    }

    /**
     * The constructor for a document with a given path to a file.
     * Additionally formats some of the metadata. Uses placeholders for
     * metaUserID, upload timestamp and documentID, for later implementation.
     * Also has to be checked for harmful content in the future.
     * This works only if documentTitle has no dot.
     *
     * @param ownerID     an ID referring to the owner of the envelope this document is a part of.
     * @param documentPutRequest
     * @param signatories The list of signatories for a document.
     * @param readers     The list of readers for a document.
     * @throws IOException throws the exception if filepath was invalid.
     */
    public Document(final DocumentPutRequest documentPutRequest, final List<Signatory> signatories,
                    final String ownerID, final List<Signatory> readers) {
        this.signatories = signatories;
        this.readers = readers;
        this.documentType = documentPutRequest.getType();
        this.data = documentPutRequest.getData();
        this.documentMetaData = new DocumentMetaData(LocalDateTime.now(), documentPutRequest.getTitle(),
             documentPutRequest.getLastModified(), this.data.length, ownerID);
        this.endDate = documentPutRequest.getEndDate();
        this.orderRelevant = documentPutRequest.isOrderRelevant();
    }

    /**
     * adds a new user as a signatory to the signatory list.
     *
     * @param signatory the user object that is needed as a signatory
     */
    public void addSignatory(final User signatory) {
        signatories.add(new Signatory(signatory));
    }

    /**
     * adds a new user as a reader to the reader list.
     *
     * @param reader the user object that is needed as a reader
     */
    public void addReader(final User reader) {
        readers.add(new Signatory(reader));
    }

    /**
     * the Method to add an advanced signature for a specific user to the document.
     *
     * @param user      the user that signs the document
     * @param signature the signature that has been made
     * @param index     the index of the key the signature has been made with
     */
    public void advancedSignature(final String user, final byte[] signature, final int index) {
        boolean userIsSignatory = false;
        for (int i = 0; i < signatories.size(); i++) {
            if (signatories.get(i).getUser().getEmail().equals(user)) {
                userIsSignatory = true;
                advancedSignatures.add(new AdvancedSignature(user, signature, index));
                setSigned(i);
            }
        }
        if (!userIsSignatory) {
            System.out.println("The user is not a signatory for this document");
        }
    }

    /**
     * the method used to verify a signature for a specific user, by checking all public keys a user has.
     *
     * @param user the user who relates to the signature that needs to be checked
     * @return true, if one of the public keys matches with the signature.If that is not the case we return false.
     */
    public boolean verifySignature(final User user) {

        final AdvancedSignature signatureInfo = getUsersSignature(user.getEmail());
        boolean valid = false;
        if (signatureInfo != null) {
            final byte[] signature = signatureInfo.getSignature();
            try {
                final Signature sign = Signature.getInstance(SIGNING_ALGORITHM);
                final byte[] id = this.documentMetaData.getIdentifier().getBytes();
                final PublicKey publicKey = user.getPublicKey();
                sign.initVerify(publicKey);
                sign.update(id);
                valid = sign.verify(signature);
            } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException exception) {
                exception.printStackTrace();
            }
        }
        return valid;
    }

    private AdvancedSignature getUsersSignature(final String user) {
        for (final AdvancedSignature advancedSignature : advancedSignatures) {
            if (advancedSignature.getUserEmail().equals(user)) {
                return advancedSignature;
            }
        }
        return null;
    }

    //--------- Filter methods--------

    /**
     * The filter method for document titles.
     *
     * @param titleFilter a String specifying the filter.
     * @return true if this document contains the titleFilter.
     */
    public boolean hasTitle(final String titleFilter) {
        return this.getDocumentTitle().contains(titleFilter);
    }

    /**
     * The filter method for document signatureTypes.
     *
     * @param signatureTypeFilter the signatureType specifying the filter.
     * @return true if this document has this signature type.
     */
    public boolean hasSignatureType(final SignatureType signatureTypeFilter) {
        if (signatureTypeFilter == null) {
            return true;
        }
        return this.signatureType.equals(signatureTypeFilter);
    }

    /**
     * The filter method for document states.
     *
     * @param documentStateFilter the state specifying the filter.
     * @return true if this document has this state.
     */
    public boolean hasState(final DocumentState documentStateFilter) {
        if (documentStateFilter == null) {
            return true;
        }
        return this.state.equals(documentStateFilter);
    }

    /**
     * The filter method for document titles.
     *
     * @param endDateFrom a Date which specifies the earliest moment.
     * @param endDateTo   a Date which specifies the latest moment.
     * @return true if this document is in between these endDates.
     */
    public boolean hasEndDate(final LocalDateTime endDateFrom, final LocalDateTime endDateTo) {
        if (endDateFrom == null && endDateTo == null) {
            return true;
        } else if (endDateFrom == null) {
            return this.endDate.isBefore(endDateTo);
        } else if (endDateTo == null) {
            return this.endDate.isAfter(endDateFrom);
        } else {
            return this.endDate.isAfter(endDateFrom) && this.endDate.isBefore(endDateTo);
        }
    }

    /**
     * The filter method for document data types.
     *
     * @param dataType a String specifying the datatypeFilter.
     * @return true if this document is from this type, or contains it.
     */
    public boolean hasDataType(final String dataType) {
        return this.documentType.contains(dataType);
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
            if (signatories.contains(signatory.getUser().getEmail())) {
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
        if (readers == null) {
            return true;
        }
        for (final Signatory reader : this.readers) {
            if (readers.contains(reader.getUser().getEmail())) {
                return true;
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
     * The filter method for read.
     *
     * @param read a boolean specifying the filter if the document has been read.
     * @return true if this document corresponds to the filter.
     */
    public boolean hasRead(final boolean read) {
        for (final Signatory reader : this.readers) {
            if (read == reader.isStatus()) {
                return true;
            }
        }
        return false;
    }

    //--------- Getter and Setter --------

    public long getId() {
        return id;
    }

    public String getOwner() {
        return documentMetaData.getMetaUserID();
    }

    public byte[] getData() {
        return Arrays.copyOf(data, data.length);
    }

    public void setSigned(final int index) {
        signatories.get(index).setStatus(true);
    }

    public DocumentMetaData getDocumentMetaData() {
        return documentMetaData;
    }

    public String getDocumentTitle() {
        return documentMetaData.getMetaDocumentTitle();
    }

    public String getDocumentType() {
        return documentType;
    }

    public SignatureType getSignatureType() {
        return signatureType;
    }

    public void setSignatureType(final SignatureType signatureType) {
        this.signatureType = signatureType;
    }

    public List<AdvancedSignature> getAdvancedSignatures() {
        return advancedSignatures;
    }

    public List<Signatory> getSignatories() {
        return signatories;
    }

    public List<Signatory> getReaders() {
        return readers;
    }

    public boolean isOrderRelevant() {
        return orderRelevant;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setOrderRelevant(final boolean orderRelevant) {
        this.orderRelevant = orderRelevant;
    }

    public void setEndDate(final LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public DocumentState getState() {
        return state;
    }

    public void setState(final DocumentState documentState) {
        this.state = documentState;
    }
}

