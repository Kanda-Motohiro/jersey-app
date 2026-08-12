package com.generated;


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
@Path("/hello")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2026-08-11T21:41:58.569195509+09:00[Asia/Tokyo]", comments = "Generator version: 7.4.0")
public interface HelloResource {

    /**
     * 
     *
     * @param name 
     * @return A successful response returning plain text greeting
     */
    @GET
    @Produces({ "text/plain" })
    String hello(@QueryParam("name") @DefaultValue("Jetty + Jersey")   String name);

}
