package com.example;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Configuration;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
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
            @Context ResourceInfo resourceInfo,
            @Context Configuration configuration,
            @Context ServletConfig servletConfig,
            @Context ServletContext servletContext,
            @Context HttpServletRequest httpServletRequest) {

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("uriInfo", buildUriInfo(uriInfo));
        payload.put("httpHeaders", buildHttpHeaders(httpHeaders));
        payload.put("request", buildRequest(request, uriInfo));
        payload.put("securityContext", buildSecurityContext(securityContext));
        payload.put("resourceInfo", buildResourceInfo(resourceInfo));
        payload.put("configuration", buildConfiguration(configuration));

        payload.put("httpServletRequest", buildHttpServletRequest(httpServletRequest));
        payload.put("servletConfig", buildServletConfig(servletConfig));
        payload.put("servletContext", buildServletContext(servletContext));
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

    private Map<String, Object> buildUriInfo(UriInfo uriInfo) {
        Map<String, Object> data = new LinkedHashMap<>();
        if (uriInfo == null) {
            data.put("present", false);
            return data;
        }

        data.put("present", true);
        data.put("absolutePath", uriInfo.getAbsolutePath().toString());
        data.put("baseUri", uriInfo.getBaseUri().toString());
        data.put("requestUri", uriInfo.getRequestUri().toString());
        data.put("path", uriInfo.getPath());
        data.put("matchedResources", toStringList(uriInfo.getMatchedResources()));

        Map<String, List<String>> pathParameters = new LinkedHashMap<>();
        for (Map.Entry<String, List<String>> entry : uriInfo.getPathParameters().entrySet()) {
            pathParameters.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        data.put("pathParameters", pathParameters);

        Map<String, List<String>> queryParameters = new LinkedHashMap<>();
        for (Map.Entry<String, List<String>> entry : uriInfo.getQueryParameters().entrySet()) {
            queryParameters.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        data.put("queryParameters", queryParameters);
        return data;
    }

    private Map<String, Object> buildResourceInfo(ResourceInfo resourceInfo) {
        Map<String, Object> data = new LinkedHashMap<>();
        if (resourceInfo == null) {
            data.put("present", false);
            return data;
        }

        data.put("present", true);
        if (resourceInfo.getResourceClass() != null) {
            data.put("resourceClass", resourceInfo.getResourceClass().getName());
        }
        Method method = resourceInfo.getResourceMethod();
        if (method != null) {
            data.put("methodName", method.getName());
            data.put("returnType", method.getReturnType().getName());
            data.put("parameterTypes", toStringList(Arrays.asList(method.getParameterTypes())));
        }
        return data;
    }

    private Map<String, Object> buildConfiguration(Configuration configuration) {
        Map<String, Object> data = new LinkedHashMap<>();
        if (configuration == null) {
            data.put("present", false);
            return data;
        }

        data.put("present", true);
        List<String> propertyNames = new ArrayList<>();
        for (String name : configuration.getPropertyNames()) {
            propertyNames.add(name);
        }
        data.put("propertyNames", propertyNames);
        return data;
    }

    private Map<String, Object> buildServletConfig(ServletConfig servletConfig) {
        Map<String, Object> data = new LinkedHashMap<>();
        if (servletConfig == null) {
            data.put("present", false);
            return data;
        }

        data.put("present", true);
        data.put("servletName", servletConfig.getServletName());
        List<String> initParameterNames = new ArrayList<>();
        Map<String, String> initParameters = new LinkedHashMap<>();
        Enumeration<String> names = servletConfig.getInitParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            initParameterNames.add(name);
            initParameters.put(name, servletConfig.getInitParameter(name));
        }
        data.put("initParameterNames", initParameterNames);
        data.put("initParameters", initParameters);
        return data;
    }

    private Map<String, Object> buildServletContext(ServletContext servletContext) {
        Map<String, Object> data = new LinkedHashMap<>();
        if (servletContext == null) {
            data.put("present", false);
            return data;
        }

        data.put("present", true);
        data.put("contextPath", servletContext.getContextPath());

        List<String> initParameterNames = new ArrayList<>();
        Map<String, String> initParameters = new LinkedHashMap<>();
        Enumeration<String> initNames = servletContext.getInitParameterNames();
        while (initNames.hasMoreElements()) {
            String name = initNames.nextElement();
            initParameterNames.add(name);
            initParameters.put(name, servletContext.getInitParameter(name));
        }
        data.put("initParameterNames", initParameterNames);
        data.put("initParameters", initParameters);

        List<String> attributeNames = new ArrayList<>();
        Enumeration<String> attributeNamesEnum = servletContext.getAttributeNames();
        while (attributeNamesEnum.hasMoreElements()) {
            attributeNames.add(attributeNamesEnum.nextElement());
        }
        data.put("attributeNames", attributeNames);

        data.put("realPath", servletContext.getRealPath("/"));
        data.put("serverInfo", servletContext.getServerInfo());
        data.put("majorVersion", servletContext.getMajorVersion());
        data.put("minorVersion", servletContext.getMinorVersion());
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
