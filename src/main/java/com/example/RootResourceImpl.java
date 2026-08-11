package com.example;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

import jakarta.servlet.ServletContext;

import java.util.List;
import java.util.Collections;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class RootResourceImpl {

    @Context
    private ServletContext servletContext;

    @GET
    public List<String> root() {
        Object attr = servletContext.getAttribute("app.paths");
        if (attr instanceof List) {
            //noinspection unchecked
            return (List<String>) attr;
        }
        return Collections.emptyList();
    }
}
