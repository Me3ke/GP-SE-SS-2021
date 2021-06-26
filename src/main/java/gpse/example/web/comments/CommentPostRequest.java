package gpse.example.web.comments;

/**
 * The class responsible for containing the contents of
 * the comment to be posted on the backend.
 */
public class CommentPostRequest {

    private String content;

    public CommentPostRequest(String content) {
        this.content = content;
    }

    public CommentPostRequest() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
