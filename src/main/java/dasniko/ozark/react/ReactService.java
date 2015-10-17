package dasniko.ozark.react;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Niko Köbler, http://www.n-k.de, @dasniko
 */
@ApplicationScoped
public class ReactService {

    private List<Comment> comments = new ArrayList<>();

    @PostConstruct
    public void init() {
        comments.add(new Comment("John Doe", "This is just a comment."));
        comments.add(new Comment("Ada Lovelace", "Ada Comment"));
        comments.add(new Comment("Claude Shannon", "Claude Comment"));
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Comment> addComment(Comment comment) {
        comments.add(comment);
        return comments;
    }

}
