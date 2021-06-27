package gpse.example.web.documents;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Class modeling the guestTokens.
 */
@Entity
public class GuestToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String token;

    @Column
    private LocalDateTime createdDate;

    @Column
    private String username;

    @Column
    private long documentId;

    /**
     * Constructor of Guest Token to generate a GuestToken which contains username and documentId.
     * @param username username of the GuestUser (Emailaddress)
     * @param documentId document that is accessable for the Guest
     */
    public GuestToken(String username, long documentId) {
        this.username = username;
        this.documentId = documentId;
        this.createdDate = LocalDateTime.now();
        this.token = UUID.randomUUID().toString();
    }

    public GuestToken() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(long documentId) {
        this.documentId = documentId;
    }
}
