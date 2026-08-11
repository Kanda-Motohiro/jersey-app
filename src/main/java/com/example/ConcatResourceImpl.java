package com.example;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/concat")
public class ConcatResourceImpl {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> concat(List<String> values) {
        if (values == null) {
            values = List.of();
        }
        String out = values.stream().collect(Collectors.joining());
        return Map.of("out", out);
    }
}
