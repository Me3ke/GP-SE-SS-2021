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
    private String text;

    @Column
    private String authorID;

    @Column
    private String authorName;

    @Column
    private LocalDateTime creationDate;

    /**
     * The constructor responsible for creating an answer object.
     *
     * @param text       the text displayed in the answer
     * @param authorID   the id of the author who wrote the answer
     * @param authorName the name of the author who wrote the answer
     */
    public Answer(String text, String authorID, String authorName) {
        this.text = text;
        this.authorID = authorID;
        this.authorName = authorName;
        this.creationDate = LocalDateTime.now();
    }

    protected Answer() {

    }

    public String getText() {
        return text;
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
}
