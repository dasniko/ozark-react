package dasniko.ozark.react;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

/**
 * @author Niko Köbler, http://www.n-k.de, @dasniko
 */
@Path("/comments.json")
public class CommentController {

    @Inject
    private ReactService service;

    @GET
    public List<Comment> getComments() {
        return service.getComments();
    }

    @POST
    public List<Comment> addComment(Comment comment) {
        return service.addComment(comment);
    }

}