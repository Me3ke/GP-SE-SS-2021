package gpse.example.domain.documents.comments;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for handling the comments.
 */
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Lob
    private String text;

    @Column
    private String authorID;

    @Column
    private LocalDateTime creationDate;

    @OneToMany(
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    private List<Answer> answerList = new ArrayList<>();

    /**
     * The constructor responsible for creating a comment object.
     *
     * @param text     the text displayed in the comment
     * @param authorID the author who wrote the comment
     */
    public Comment(String text, String authorID) {
        this.text = text;
        this.authorID = authorID;
        this.creationDate = LocalDateTime.now();
    }

    protected Comment() {
    }

    public String getText() {
        return text;
    }

    public String getAuthorID() {
        return authorID;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }
}
