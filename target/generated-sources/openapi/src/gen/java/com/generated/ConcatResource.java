package com.generated;

import com.example.model.Concat200Response;

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
@Path("/concat")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2026-08-11T21:41:58.569195509+09:00[Asia/Tokyo]", comments = "Generator version: 7.4.0")
public interface ConcatResource {

    /**
     * 
     *
     * @param requestBody 
     * @return A successful response returning JSON with the concatenated string
     */
    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    Concat200Response concat(@Valid @NotNull List<String> requestBody);

}
