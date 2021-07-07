package gpse.example.domain.documents;

import gpse.example.domain.documents.comments.Comment;
import gpse.example.domain.signature.AdvancedSignature;
import gpse.example.domain.signature.Signatory;
import gpse.example.domain.signature.SignatureType;
import gpse.example.web.documents.DocumentPutRequest;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    protected long id;


    /**
     * The object that contains metadata of the document, like the upload-date or the document-hash.
     */
    @OneToOne(
        orphanRemoval = true,
        cascade = CascadeType.ALL
    )
    protected DocumentMetaData documentMetaData;

    /**
     * The list of signatories for this document.
     */
    @OneToMany(
        fetch = FetchType.EAGER,
        orphanRemoval = true,
        cascade = CascadeType.ALL
    )
    protected List<Signatory> signatories;

    /**
     * The list of all advanced signatures that have been made so far.
     */
    @OneToMany(
        orphanRemoval = true,
        cascade = CascadeType.ALL)
    protected final List<AdvancedSignature> advancedSignatures = new ArrayList<>();

    /**
     * If a document is an updated version, the last version will be saved here.
     */
    @OneToOne(targetEntity = Document.class, fetch = FetchType.LAZY)
    protected Document previousVersion;

    /**
     * The datatype of the document.
     */
    @Column
    protected String documentType;

    /**
     * The byte-array representing the data of the document.
     */
    @Lob
    protected byte[] data;

    /**
     * order Relevant indicates whether the order in which the signatories sign is important or not.
     */
    @Column
    protected boolean orderRelevant;

    /**
     * The endDate describes the deadline for the process of this document.
     */
    @Column
    protected LocalDateTime endDate;

    /**
     * The document state, describes in which phase of the process the document currently is.
     * It can be either OPEN, READ or CLOSED.
     */
    @Column
    protected DocumentState state;

    @OneToMany(
        orphanRemoval = true,
        cascade = CascadeType.ALL)
    private final List<Comment> commentList = new ArrayList<>();

    @Column
    private long processEmailTemplateId;

    @Column
    private String linkToDocumentview;

    public Document() {
    }

    /**
     * The constructor for a document with a given path to a file.
     * Additionally formats some of the metadata. Uses placeholders for
     * metaUserID, upload timestamp and documentID, for later implementation.
     * Also has to be checked for harmful content in the future.
     * This works only if documentTitle has no dot.
     *
     * @param ownerID            an ID referring to the owner of the envelope this document is a part of.
     * @param documentPutRequest the requestBody of the request stated to generate this document
     * @param signatories        The list of signatories for a document.
     */
    public Document(final DocumentPutRequest documentPutRequest, final List<Signatory> signatories,
                    final String ownerID) {
        this.signatories = signatories;
        this.documentType = documentPutRequest.getDataType();
        this.data = documentPutRequest.getData();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.documentMetaData = new DocumentMetaData(LocalDateTime.now(), documentPutRequest.getTitle(),
            /*LocalDateTime.parse(documentPutRequest.getLastModified(), formatter),*/ this.data.length, ownerID);
        this.endDate = LocalDateTime.parse(documentPutRequest.getEndDate(), formatter);
        this.orderRelevant = documentPutRequest.isOrderRelevant();
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
     * the Method to add an advanced signature for a specific user to the document.
     *
     * @param user      the user that signs the document
     * @param signature the signature that has been made
     */
    public void advancedSignature(final String user, final String signature) {
        for (int i = 0; i < signatories.size(); i++) {
            if (signatories.get(i).getEmail().equals(user)) {
                advancedSignatures.add(new AdvancedSignature(user, signature.getBytes()));
                setSigned(i);
            }
        }
    }

    /**
     * The getHistory method gets all previous versions of a document.
     *
     * @return a list of all previous versions starting with the given document and ending with the first version.
     */
    public List<Document> getHistory() {
        Document temp = this;
        final List<Document> history = new ArrayList<>();
        while (temp != null) {
            history.add(temp);
            temp = temp.getPreviousVersion();
        }
        return history;
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
     * The filter method for read.
     *
     * @param read a boolean specifying the filter if the document has been read.
     * @return true if this document corresponds to the filter.
     */
    public boolean hasRead(final boolean read) {
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

    public List<AdvancedSignature> getAdvancedSignatures() {
        return advancedSignatures;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public List<Signatory> getSignatories() {
        return signatories;
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

    public void addComment(final Comment comment) {
        this.commentList.add(comment);
    }

    /**
     * the method searching for a specific comment in the list.
     *
     * @param commentID the ID of the comment the method is looking for
     * @return the comment if found, otherwise an empty object
     */
    public Optional<Comment> searchComment(final long commentID) {
        for (final Comment comment : commentList) {
            if (comment.getCommentID() == commentID) {
                return Optional.of(comment);
            }
        }
        return Optional.empty();
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

    public void setSignatories(final List<Signatory> signatories) {
        this.signatories = signatories;
    }

    public Document getPreviousVersion() {
        return previousVersion;
    }

    public void setPreviousVersion(final Document previousVersion) {
        this.previousVersion = previousVersion;
    }

    public long getProcessEmailTemplateId() {
        return processEmailTemplateId;
    }

    public void setProcessEmailTemplateId(final long processEmailTemplateId) {
        this.processEmailTemplateId = processEmailTemplateId;
    }

    public String getLinkToDocumentview() {
        return linkToDocumentview;
    }

    public void setLinkToDocumentview(String linkToDocumentview) {
        this.linkToDocumentview = linkToDocumentview;
    }
}
