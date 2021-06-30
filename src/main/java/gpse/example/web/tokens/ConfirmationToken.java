package gpse.example.web.tokens;

import gpse.example.domain.users.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


/**
 * the confirmationtoken-class.
 */
@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String token;

    @Column
    private LocalDateTime createdDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    /**
     * Constructor of ConfirmationToken setting an user the creation date and a random string as the token.
     * @param user the user the token should relate to
     */
    public ConfirmationToken(final User user) {
        this.user = user;
        this.createdDate = LocalDateTime.now();
        this.token = UUID.randomUUID().toString();
    }

    public ConfirmationToken() {

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

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }
}
