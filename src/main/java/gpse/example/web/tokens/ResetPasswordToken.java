package gpse.example.web.tokens;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * ResetPasswordToken class Referencing an user when he lost password.
 */
@Entity
public class ResetPasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String token;

    @Column
    private LocalDateTime createdDate;

    @Column
    //@JoinColumn(name = "USER_ID")
    private String userId;

    /**
     * Generate token with reference to an user.
     * @param userId referenced user
     */
    public ResetPasswordToken(final String userId) {
        this.userId = userId;
        this.createdDate = LocalDateTime.now();
        this.token = UUID.randomUUID().toString();
    }

    public ResetPasswordToken() {

    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String user) {
        this.userId = user;
    }
}
