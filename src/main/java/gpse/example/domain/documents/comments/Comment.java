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

    @OneToMany(
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    private final List<Answer> answers = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long commentID;
    @Column
    private String authorID;
    @Lob
    private String content;

    @Column
    private LocalDateTime creationDate;
    @Column
    private String authorName;

    /**
     * The constructor responsible for creating a comment object.
     *
     * @param content    the text displayed in the comment
     * @param authorID   the id of the author who wrote the comment
     * @param authorName the name of author who wrote the comment
     */
    public Comment(final String content, final String authorID, final String authorName) {
        this.content = content;
        this.authorID = authorID;
        this.authorName = authorName;
        this.creationDate = LocalDateTime.now();
    }

    protected Comment() {
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

    public List<Answer> getAnswers() {
        return answers;
    }

    public long getCommentID() {
        return commentID;
    }

    public void addAnswer(final Answer answer) {
        answers.add(answer);
    }
}
