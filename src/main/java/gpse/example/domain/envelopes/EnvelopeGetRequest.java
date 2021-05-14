package gpse.example.domain.envelopes;

import gpse.example.domain.documents.DocumentState;
import gpse.example.domain.signature.SignatureType;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The envelopeGetRequest is a CommandPattern class to specify the the Rest-Api
 * for a Get request on Envelopes.
 */
public class EnvelopeGetRequest {

    /**
     * Everything that you dont want to be filtered has to be either
     * "" if it is from String type, 0 if it is from long type or
     * null if it is any other type. If these conditions are not fulfilled
     * the filter method will throw a Null pointer Exception.
     */
    //TODO sort
    private String titleFilter;
    private String nameFilter;
    private long envelopeIDFilter;
    private DocumentState stateFilter;
    private String ownerIDFilter;
    private LocalDateTime creationDateFilterFrom;
    private LocalDateTime creationDateFilterTo;
    private LocalDateTime endDateFilterFrom;
    private LocalDateTime endDateFilterTo;
    private SignatureType signatureTypeFilter;
    private String dataType;
    private List<String> signatoryIDs;
    private List<String> readerIDs;
    private boolean signed;
    private boolean read;
    private int pageLimit;
    private int page;
    private String sort;

    public String getNameFilter() {
        return nameFilter;
    }

    public void setNameFilter(final String nameFilter) {
        this.nameFilter = nameFilter;
    }

    public String getTitleFilter() {
        return titleFilter;
    }

    public void setTitleFilter(final String titleFilter) {
        this.titleFilter = titleFilter;
    }

    public long getEnvelopeIDFilter() {
        return envelopeIDFilter;
    }

    public void setEnvelopeIDFilter(final long envelopeIDFilter) {
        this.envelopeIDFilter = envelopeIDFilter;
    }

    public DocumentState getStateFilter() {
        return stateFilter;
    }

    public void setStateFilter(final DocumentState stateFilter) {
        this.stateFilter = stateFilter;
    }

    public String getOwnerIDFilter() {
        return ownerIDFilter;
    }

    public void setOwnerIDFilter(final String ownerIDFilter) {
        this.ownerIDFilter = ownerIDFilter;
    }

    public LocalDateTime getCreationDateFilterFrom() {
        return creationDateFilterFrom;
    }

    public void setCreationDateFilterFrom(final LocalDateTime creationDateFilterFrom) {
        this.creationDateFilterFrom = creationDateFilterFrom;
    }

    public LocalDateTime getCreationDateFilterTo() {
        return creationDateFilterTo;
    }

    public void setCreationDateFilterTo(final LocalDateTime creationDateFilterTo) {
        this.creationDateFilterTo = creationDateFilterTo;
    }

    public LocalDateTime getEndDateFilterFrom() {
        return endDateFilterFrom;
    }

    public void setEndDateFilterFrom(final LocalDateTime endDateFilterFrom) {
        this.endDateFilterFrom = endDateFilterFrom;
    }

    public LocalDateTime getEndDateFilterTo() {
        return endDateFilterTo;
    }

    public void setEndDateFilterTo(final LocalDateTime endDateFilterTo) {
        this.endDateFilterTo = endDateFilterTo;
    }

    public SignatureType getSignatureTypeFilter() {
        return signatureTypeFilter;
    }

    public void setSignatureTypeFilter(final SignatureType signatureTypeFilter) {
        this.signatureTypeFilter = signatureTypeFilter;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(final String dataType) {
        this.dataType = dataType;
    }

    public List<String> getSignatoryIDs() {
        return signatoryIDs;
    }

    public void setSignatoryIDs(final List<String> signatoryIDs) {
        this.signatoryIDs = signatoryIDs;
    }

    public List<String> getReaderIDs() {
        return readerIDs;
    }

    public void setReaderIDs(final List<String> readerIDs) {
        this.readerIDs = readerIDs;
    }

    public int getPageLimit() {
        return pageLimit;
    }

    public void setPageLimit(final int pageLimit) {
        this.pageLimit = pageLimit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(final int page) {
        this.page = page;
    }

    public boolean isSigned() {
        return signed;
    }

    public void setSigned(final boolean signed) {
        this.signed = signed;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(final boolean read) {
        this.read = read;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
