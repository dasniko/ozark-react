package dasniko.ozark.react;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Niko Köbler, http://www.n-k.de, @dasniko
 */
@ApplicationScoped
public class BookService {

    private List<Book> books = new ArrayList<>();

    @PostConstruct
    public void init() {
        books.add(new Book("George Orwell", "1984"));
        books.add(new Book("Douglas Adams", "Hitchhiker’s Guide to the Galaxy"));
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Book> addBook(Book book) {
        books.add(book);
        return books;
    }

}
