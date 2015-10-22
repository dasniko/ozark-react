package dasniko.ozark.react;

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
    private BookService service;

    @GET
    public String index() throws Exception {
        List<Book> books = service.getBooks();
        models.put("data", books);
        return "react:react.jsp?function=renderServer";
    }
}
