package com.example;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResourceImpl {

    @GET
    public List<Book> listBooks() {
        return BookDatabase.findAll();
    }

    @POST
    public Book addBook(Book book) {
        if (book == null) {
            throw new BadRequestException("Book payload is required");
        }
        if (book.getTitle() == null || book.getTitle().isBlank()) {
            throw new BadRequestException("Book title is required");
        }
        if (book.getAuthor() == null || book.getAuthor().isBlank()) {
            throw new BadRequestException("Book author is required");
        }
        return BookDatabase.insert(book);
    }
}
