package com.example;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class HelloResource {
    @GET
    @Path("hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@QueryParam("name") @DefaultValue("Jetty + Jersey") String name) {
        return "Hello " + name;
    }

    @GET
    @Path("world")
    @Produces(MediaType.TEXT_PLAIN)
    public String world() {
        return "World";
    }
}
