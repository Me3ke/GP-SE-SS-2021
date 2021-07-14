package gpse.example.domain.documents;

import gpse.example.domain.documents.comments.Comment;
import gpse.example.domain.signature.AdvancedSignature;
import gpse.example.domain.signature.Signatory;
import gpse.example.web.documents.DocumentPutRequest;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * The model for the document responsible for initialising the necessary details about the document file.
 */
@Entity
public class Document {

    /**
     * The id to identify the document.
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
     * The object responsible for managing the signatories.
     */
    @OneToOne(
        orphanRemoval = true,
        cascade = CascadeType.ALL
    )
    protected SignatoryManagement signatoryManagement;

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
     * The byte-array representing the data of the document.
     */
    @Lob
    protected byte[] data;

    @OneToMany(
        orphanRemoval = true,
        cascade = CascadeType.ALL)
    private final List<Comment> commentList = new ArrayList<>();

    @OneToOne(
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    protected SignatureProcessData signatureProcessData;

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
        this.signatoryManagement = new SignatoryManagement(signatories);
        this.data = documentPutRequest.getData();
        this.documentMetaData = new DocumentMetaData(LocalDateTime.now(), documentPutRequest.getTitle(),
            this.data.length, ownerID);
        this.documentMetaData.setDocumentType(documentPutRequest.getDataType());
        this.signatureProcessData = new SignatureProcessData(documentPutRequest);
    }


    /**
     * the Method to add an advanced signature for a specific user to the document.
     *
     * @param user      the user that signs the document
     * @param signature the signature that has been made
     */
    public void advancedSignature(final String user, final String signature) {
        if (!signatureProcessData.getState().equals(DocumentState.ARCHIVED)) {
            for (int i = 0; i < signatoryManagement.getSignatories().size(); i++) {
                if (signatoryManagement.getSignatories().get(i).getEmail().equals(user)) {
                    advancedSignatures.add(new AdvancedSignature(user, signature.getBytes()));
                    signatoryManagement.setSigned(i);
                }
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

    public DocumentMetaData getDocumentMetaData() {
        return documentMetaData;
    }

    public String getDocumentTitle() {
        return documentMetaData.getMetaDocumentTitle();
    }

    public String getDocumentType() {
        return documentMetaData.getDocumentType();
    }

    public List<AdvancedSignature> getAdvancedSignatures() {
        return advancedSignatures;
    }

    public List<Comment> getCommentList() {
        return commentList;
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

    /**
     * The method used to change the signatories of this document.
     *
     * @param signatories the list with the new signatories.
     */
    public void setSignatories(final List<Signatory> signatories) {
        if (!signatureProcessData.getState().equals(DocumentState.ARCHIVED)) {
            this.signatoryManagement.signatories.clear();
            this.signatoryManagement.signatories.addAll(signatories);
        }
    }

    public Document getPreviousVersion() {
        return previousVersion;
    }

    /**
     * The method used for new versions that are uploaded, to save the last version as the previous one for the new
     * document.  If the document is archived, nothing happens.
     *
     * @param previousVersion the last version of this document.
     */
    public void setPreviousVersion(final Document previousVersion) {
        if (!signatureProcessData.getState().equals(DocumentState.ARCHIVED)) {
            this.previousVersion = previousVersion;
        }
    }


    public String getLinkToDocumentView() {
        return documentMetaData.getLinkToDocumentView();
    }

    public SignatoryManagement getSignatoryManagement() {
        return signatoryManagement;
    }

    public void setLinkToDocumentView(final String linkToDocumentView) {
        documentMetaData.setLinkToDocumentView(linkToDocumentView);
    }

    public SignatureProcessData getSignatureProcessData() {
        return signatureProcessData;
    }
}
