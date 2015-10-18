package dasniko.ozark.react;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.mvc.Models;
import javax.mvc.annotation.Controller;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

/**
 * @author Niko Köbler, http://www.n-k.de, @dasniko
 */
@Controller
@Path("/react")
public class ReactController {

    @Inject
    private Models models;

    @Inject
    private ReactService service;

    private React react;
    private ObjectMapper mapper;

    @PostConstruct
    public void init() {
        react = new React();
        mapper = new ObjectMapper();
    }

    @GET
    public String index() throws Exception {
        List<Comment> comments = service.getComments();
        String commentBox = react.render("renderServer", comments);
        String data = mapper.writeValueAsString(comments);
        models.put("content", commentBox);
        models.put("data", data);
        return "react.jsp";
    }
}
