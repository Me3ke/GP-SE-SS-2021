package gpse.example.web.comments;

import gpse.example.domain.documents.comments.Comment;

import java.util.List;

/**
 * The class responsible for containing the comments
 * of the response.
 */
public class CommentsGetResponse {

    private List<Comment> comments;

    public CommentsGetResponse() {
    }

    public CommentsGetResponse(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
