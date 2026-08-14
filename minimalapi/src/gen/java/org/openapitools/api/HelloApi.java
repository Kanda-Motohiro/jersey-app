package org.openapitools.api;

import org.openapitools.api.HelloApiService;
import org.openapitools.api.factories.HelloApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;


import java.util.Map;
import java.util.List;
import org.openapitools.api.NotFoundException;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;

import javax.servlet.ServletConfig;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.validation.constraints.*;
import javax.validation.Valid;

@Path("/hello")


@io.swagger.annotations.Api(description = "the hello API")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2026-08-14T16:15:39.269699308+09:00[Asia/Tokyo]", comments = "Generator version: 7.24.0")
public class HelloApi  {
   private final HelloApiService delegate;

   public HelloApi(@Context ServletConfig servletContext) {
      HelloApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("HelloApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (HelloApiService) Class.forName(implClass).getDeclaredConstructor().newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }

      if (delegate == null) {
         delegate = HelloApiServiceFactory.getHelloApi();
      }

      this.delegate = delegate;
   }

    @javax.ws.rs.GET
    
    
    @Produces({ "text/plain" })
    @io.swagger.annotations.ApiOperation(value = "Returns a greeting message", notes = "", response = String.class, tags={ "Hello", })
    @io.swagger.annotations.ApiResponses(value = {
        @io.swagger.annotations.ApiResponse(code = 200, message = "A successful response returning plain text greeting", response = String.class)
    })
    public Response hello(@ApiParam(value = "", defaultValue = "Jetty + Jersey") @DefaultValue("Jetty + Jersey") @QueryParam("name")  String name,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.hello(name, securityContext);
    }
}
