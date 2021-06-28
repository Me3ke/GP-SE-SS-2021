package gpse.example.domain.documents.comments;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Class responsible for handling the answers.
 */
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Lob
    private String content;

    @Column
    private String authorID;

    @Column
    private String authorName;

    @Column
    private LocalDateTime creationDate;

    /**
     * The constructor responsible for creating an answer object.
     *
     * @param content       the text displayed in the answer
     * @param authorID   the id of the author who wrote the answer
     * @param authorName the name of the author who wrote the answer
     */
    public Answer(final String content, final String authorID, final String authorName) {
        this.content = content;
        this.authorID = authorID;
        this.authorName = authorName;
        this.creationDate = LocalDateTime.now();
    }

    protected Answer() {

    }

    public String getContent() {
        return content;
    }

    public String getAuthorID() {
        return authorID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public long getId() {
        return id;
    }
}
