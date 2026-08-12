package com.generated;

import com.example.model.Book;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;


import java.io.InputStream;
import java.util.Map;
import java.util.List;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid;

/**
* Represents a collection of functions to interact with the API endpoints.
*/
@Path("/books")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2026-08-11T21:41:58.569195509+09:00[Asia/Tokyo]", comments = "Generator version: 7.4.0")
public interface BooksResource {

    /**
     * 
     *
     * @param book 
     * @return A successful response returning the created book
     */
    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    Book addBook(@Valid @NotNull Book book);


    /**
     * 
     *
     * @return A successful response returning all books
     */
    @GET
    @Produces({ "application/json" })
    List<Book> listBooks();

}
