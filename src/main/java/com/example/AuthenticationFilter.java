package com.example;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.jvnet.libpam.PAM;
import org.jvnet.libpam.PAMException;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.toLowerCase().startsWith("basic ")) {
            abortWithUnauthorized(requestContext);
            return;
        }

        String base64Credentials = authHeader.substring("basic ".length()).trim();
        byte[] credDecoded;
        try {
            credDecoded = Base64.getDecoder().decode(base64Credentials);
        } catch (IllegalArgumentException e) {
            abortWithUnauthorized(requestContext);
            return;
        }

        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        final String[] values = credentials.split(":", 2);

        if (values.length != 2) {
            abortWithUnauthorized(requestContext);
            return;
        }

        String username = values[0];
        String password = values[1];

        try {
            // Authenticate using PAM login service
            new PAM("login").authenticate(username, password);
        } catch (PAMException e) {
            abortWithUnauthorized(requestContext);
        }
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        requestContext.abortWith(
            Response.status(Response.Status.UNAUTHORIZED)
                .header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"hello\"")
                .entity("Authentication required")
                .build()
        );
    }
}
