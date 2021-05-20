package gpse.example.domain.users;

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
    private String confirmationToken;

    @Column
    private LocalDateTime createdDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    /**
     * Constructor of ConfirmationToken setting an user the creation date and a rndom string as the token.
     * @param user
     */
    public ConfirmationToken(User user) {
        this.user = user;
        this.createdDate = LocalDateTime.now();
        this.confirmationToken = UUID.randomUUID().toString();
    }

    public ConfirmationToken() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
