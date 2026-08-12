package com.example;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Path("/getenv")
@Produces(MediaType.APPLICATION_JSON)
public class GetEnvResourceImpl {

    @GET
    public Map<String, Object> getenv(
            @Context SecurityContext securityContext,
            @Context Request request,
            @Context HttpHeaders httpHeaders,
            @Context UriInfo uriInfo,
            @Context HttpServletRequest httpServletRequest) {

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("securityContext", buildSecurityContext(securityContext));
        payload.put("request", buildRequest(request, uriInfo));
        payload.put("httpHeaders", buildHttpHeaders(httpHeaders));
        payload.put("httpServletRequest", buildHttpServletRequest(httpServletRequest));
        return payload;
    }

    private Map<String, Object> buildSecurityContext(SecurityContext securityContext) {
        Map<String, Object> data = new LinkedHashMap<>();
        if (securityContext == null) {
            data.put("present", false);
            return data;
        }

        data.put("present", true);
        data.put("authenticationScheme", securityContext.getAuthenticationScheme());
        data.put("secure", securityContext.isSecure());
        data.put("userPrincipal", securityContext.getUserPrincipal() == null ? null : securityContext.getUserPrincipal().getName());
        return data;
    }

    private Map<String, Object> buildRequest(Request request, UriInfo uriInfo) {
        Map<String, Object> data = new LinkedHashMap<>();
        if (request == null) {
            data.put("present", false);
            return data;
        }

        data.put("present", true);
        data.put("method", request.getMethod());
        if (uriInfo != null) {
            data.put("absolutePath", uriInfo.getAbsolutePath().toString());
            data.put("requestUri", uriInfo.getRequestUri().toString());
            data.put("path", uriInfo.getPath());
        }
        return data;
    }

    private Map<String, Object> buildHttpHeaders(HttpHeaders httpHeaders) {
        Map<String, Object> data = new LinkedHashMap<>();
        if (httpHeaders == null) {
            data.put("present", false);
            return data;
        }

        data.put("present", true);
        data.put("acceptableMediaTypes", toStringList(httpHeaders.getAcceptableMediaTypes()));
        data.put("acceptLanguages", toStringList(httpHeaders.getAcceptableLanguages()));
        data.put("contentType", httpHeaders.getMediaType());
        data.put("requestHeaders", new LinkedHashMap<String, List<String>>() {{
            for (Map.Entry<String, List<String>> entry : httpHeaders.getRequestHeaders().entrySet()) {
                put(entry.getKey(), new ArrayList<>(entry.getValue()));
            }
        }});
        return data;
    }

    private Map<String, Object> buildHttpServletRequest(HttpServletRequest request) {
        Map<String, Object> data = new LinkedHashMap<>();
        if (request == null) {
            data.put("present", false);
            return data;
        }

        data.put("present", true);
        data.put("remoteAddr", request.getRemoteAddr());
        data.put("remoteHost", request.getRemoteHost());
        data.put("remotePort", request.getRemotePort());
        data.put("localAddr", request.getLocalAddr());
        data.put("localName", request.getLocalName());
        data.put("localPort", request.getLocalPort());
        data.put("serverName", request.getServerName());
        data.put("serverPort", request.getServerPort());
        data.put("scheme", request.getScheme());
        data.put("method", request.getMethod());
        data.put("protocol", request.getProtocol());
        data.put("requestURI", request.getRequestURI());
        data.put("contextPath", request.getContextPath());
        data.put("servletPath", request.getServletPath());
        data.put("pathInfo", request.getPathInfo());
        data.put("queryString", request.getQueryString());
        data.put("isSecure", request.isSecure());
        data.put("characterEncoding", request.getCharacterEncoding());
        data.put("contentType", request.getContentType());
        data.put("contentLength", request.getContentLength());
        data.put("headerNames", new ArrayList<>(Collections.list(request.getHeaderNames())));
        data.put("headers", new LinkedHashMap<String, List<String>>() {{
            for (java.util.Enumeration<String> names = request.getHeaderNames(); names.hasMoreElements();) {
                String name = names.nextElement();
                put(name, Collections.list(request.getHeaders(name)));
            }
        }});
        return data;
    }

    private List<String> toStringList(List<?> values) {
        List<String> result = new ArrayList<>();
        if (values == null) {
            return result;
        }
        for (Object value : values) {
            result.add(String.valueOf(value));
        }
        return result;
    }
}
