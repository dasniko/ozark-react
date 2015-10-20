package dasniko.ozark.react;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

/**
 * @author Niko Köbler, http://www.n-k.de, @dasniko
 */
@Path("/books.json")
public class BookController {

    @Inject
    private BookService service;

    @GET
    public List<Book> getBooks() {
        return service.getBooks();
    }

    @POST
    public List<Book> addBook(Book book) {
        return service.addBook(book);
    }

}