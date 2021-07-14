package gpse.example.web.comments;

import gpse.example.domain.documents.Document;
import gpse.example.domain.documents.DocumentServiceImpl;
import gpse.example.domain.documents.comments.Answer;
import gpse.example.domain.documents.comments.Comment;
import gpse.example.domain.email.*;
import gpse.example.domain.exceptions.*;
import gpse.example.domain.users.User;
import gpse.example.domain.users.UserServiceImpl;
import gpse.example.web.JSONResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The CommentController class handles the requests from the frontend and
 * conducts the corresponding backend actions.
 */

@RestController
@CrossOrigin("http://localhost:8088")
@RequestMapping("/api")
public class CommentController {

    private static final String USER_ID = "userID";
    private static final String DOCUMENT_ID = "documentID";
    private static final String DOCUMENT_NOT_FOUND_MESSAGE = "Document could not be found";
    private static final int STATUS_CODE_DOCUMENT_NOT_FOUND = 453;
    private static final int STATUS_CODE_COMMENT_NOT_FOUND = 457;
    private static final int STATUS_CODE_OK = 200;
    private static final String REQUEST_WAS_SUCCESSFUL = "Request was successful";
    private static final String SPACE = " ";

    private final UserServiceImpl userService;
    private final DocumentServiceImpl documentService;

    /**
     * The default constructor which initialises the services by autowiring.
     *  @param userService     the userService
     * @param documentService the documentService
     */
    @Autowired
    public CommentController(final UserServiceImpl userService,
                             final DocumentServiceImpl documentService) {
        this.userService = userService;
        this.documentService = documentService;
    }

    /**
     * The Post Request used for posting comments on a document.
     * @param commentPostRequest the content of the comment to be posted
     * @param userID the author of the document to be posted
     * @param documentID the document where the comment will be posted
     * @return if the response was successful
     */
    @PostMapping("/user/{userID}/documents/{documentID:\\d+}/comments")
    public JSONResponseObject commentOnDocument(final @RequestBody CommentPostRequest commentPostRequest,
                                                final @PathVariable(USER_ID) String userID,
                                                final @PathVariable(DOCUMENT_ID) long documentID)
                throws TemplateNameNotFoundException, MessageGenerationException {
        final EmailManagement emailManagement = new EmailManagement();
        final JSONResponseObject jsonResponseObject = new JSONResponseObject();
        try {
            final Document document = documentService.getDocument(documentID);
            final User user = userService.getUser(userID);
            document.addComment(new Comment(commentPostRequest.getContent(), userID,
                    user.getFirstname() + SPACE + user.getLastname()));
            emailManagement.sendNewCommentEmail(user, userService.getUser(document.getOwner()), document);
            documentService.addDocument(document);

        } catch (DocumentNotFoundException e) {
            jsonResponseObject.setStatus(STATUS_CODE_DOCUMENT_NOT_FOUND);
            jsonResponseObject.setMessage(DOCUMENT_NOT_FOUND_MESSAGE);
            return jsonResponseObject;
        }
        jsonResponseObject.setStatus(STATUS_CODE_OK);
        jsonResponseObject.setMessage(REQUEST_WAS_SUCCESSFUL);
        return jsonResponseObject;
    }

    /**
     * The Post Request used for posting answers on a comment.
     * @param commentPostRequest the content of the answer
     * @param userID the author of the answer
     * @param documentID the document where the comment lies
     * @param commentID the comment where the answer will be posted
     * @return if the request was successful
     */
    @PostMapping("/user/{userID}/documents/{documentID:\\d+}/comments/{commentID:\\d+}/answers")
    public JSONResponseObject answerOnComment(final @RequestBody CommentPostRequest commentPostRequest,
                                              final @PathVariable(USER_ID) String userID,
                                              final @PathVariable(DOCUMENT_ID) long documentID,
                                              final @PathVariable("commentID") long commentID)
                throws TemplateNameNotFoundException, MessageGenerationException {
        final EmailManagement emailManagement = new EmailManagement();
        final JSONResponseObject jsonResponseObject = new JSONResponseObject();
        try {
            final Document document = documentService.getDocument(documentID);
            final User user = userService.getUser(userID);
            final Optional<Comment> optionalComment = document.searchComment(commentID);

            if (optionalComment.isPresent()) {
                final Comment comment = optionalComment.get();
                comment.addAnswer(new Answer(commentPostRequest.getContent(), userID,
                        user.getFirstname() + SPACE + user.getLastname()));
                documentService.addDocument(document);
                emailManagement.sendAnswerEmail(user, userService.getUser(comment.getAuthorID()), document);
                jsonResponseObject.setStatus(STATUS_CODE_OK);
                jsonResponseObject.setMessage(REQUEST_WAS_SUCCESSFUL);

            } else {
                jsonResponseObject.setStatus(STATUS_CODE_COMMENT_NOT_FOUND);
                jsonResponseObject.setMessage("Comment could not be found");
            }
            return jsonResponseObject;
        } catch (DocumentNotFoundException e) {
            jsonResponseObject.setStatus(STATUS_CODE_DOCUMENT_NOT_FOUND);
            jsonResponseObject.setMessage(DOCUMENT_NOT_FOUND_MESSAGE);
            return jsonResponseObject;
        }
    }

    /**
     * The Get Request used for getting the comments.
     * @param documentID the ID used for getting the comments
     * @param userID is the ID of the user
     * @return the comments and their answers
     */
    @GetMapping("/user/{userID}/documents/{documentID:\\d+}/comments")
    public CommentsGetResponse getCommentsFromDocument(
        final @PathVariable(DOCUMENT_ID) long documentID, final @PathVariable String userID) {

        try {
            final Document document = documentService.getDocument(documentID);
            final List<String> user = new ArrayList<>();
            user.add(userID);

            if (userID.equals(document.getOwner()) || document.getSignatoryManagement().hasSignatories(user)) {
                return new CommentsGetResponse(document.getCommentList());
            }
            return new CommentsGetResponse();
        } catch (DocumentNotFoundException e) {
            return new CommentsGetResponse();
        }
    }
}
