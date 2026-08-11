package com.example;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.HEAD;
import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.PATCH;

import org.glassfish.jersey.server.ApplicationHandler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListingServletContainer extends org.glassfish.jersey.servlet.ServletContainer {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            ApplicationHandler handler = getApplicationHandler();
            Set<Class<?>> classes = handler.getConfiguration().getClasses();
            Set<String> paths = new HashSet<>();

            for (Class<?> c : classes) {
                // collect base paths from class and its interfaces
                Set<String> basePaths = new HashSet<>();
                Path classPath = c.getAnnotation(Path.class);
                if (classPath != null) basePaths.add(classPath.value());

                for (Class<?> iface : c.getInterfaces()) {
                    Path ifacePath = iface.getAnnotation(Path.class);
                    if (ifacePath != null) basePaths.add(ifacePath.value());
                }

                boolean classHasHttpMethod = false;

                for (Method m : c.getDeclaredMethods()) {
                    boolean methodHasHttp = isHttpMethodPresent(m);

                    // check equivalent methods on interfaces for annotations
                    List<Method> ifaceMethods = findInterfaceMethods(c, m);
                    for (Method im : ifaceMethods) {
                        if (!methodHasHttp) methodHasHttp = isHttpMethodPresent(im);
                    }

                    Path methodPath = m.getAnnotation(Path.class);
                    if (methodPath == null) {
                        for (Method im : ifaceMethods) {
                            Path ip = im.getAnnotation(Path.class);
                            if (ip != null) {
                                methodPath = ip;
                                break;
                            }
                        }
                    }

                    if (methodHasHttp) classHasHttpMethod = true;

                    if (methodPath != null) {
                        String mp = methodPath.value();
                        if (!basePaths.isEmpty()) {
                            for (String b : basePaths) {
                                paths.add(clean(combine(b, mp)));
                            }
                        } else {
                            paths.add(clean(mp));
                        }
                    }
                }

                // If class or its interfaces define a base path and there are HTTP methods,
                // include the base path itself.
                if (!basePaths.isEmpty() && classHasHttpMethod) {
                    for (String b : basePaths) paths.add(clean(b));
                }

                // Also handle case where the registered resource is an interface type itself
                if (c.isInterface()) {
                    // inspect its declared methods
                    boolean ifaceHasHttp = false;
                    for (Method im : c.getDeclaredMethods()) {
                        if (isHttpMethodPresent(im)) ifaceHasHttp = true;
                        Path mp = im.getAnnotation(Path.class);
                        if (mp != null) {
                            Path p = c.getAnnotation(Path.class);
                            if (p != null) {
                                paths.add(clean(combine(p.value(), mp.value())));
                            } else {
                                paths.add(clean(mp.value()));
                            }
                        }
                    }
                    Path p = c.getAnnotation(Path.class);
                    if (p != null && ifaceHasHttp) paths.add(clean(p.value()));
                }
            }

            // convert to list and store in servlet context
            List<String> list = new ArrayList<>(paths);
            config.getServletContext().setAttribute("app.paths", list);
        } catch (Exception e) {
            throw new ServletException("Failed to collect resource paths", e);
        }
    }

    private boolean isHttpMethodPresent(Method m) {
        return m.getAnnotation(GET.class) != null
                || m.getAnnotation(POST.class) != null
                || m.getAnnotation(PUT.class) != null
                || m.getAnnotation(DELETE.class) != null
                || m.getAnnotation(HEAD.class) != null
                || m.getAnnotation(OPTIONS.class) != null
                || m.getAnnotation(PATCH.class) != null;
    }

    private List<Method> findInterfaceMethods(Class<?> implClass, Method method) {
        List<Method> result = new ArrayList<>();
        for (Class<?> iface : implClass.getInterfaces()) {
            try {
                Method im = iface.getMethod(method.getName(), method.getParameterTypes());
                result.add(im);
            } catch (NoSuchMethodException ignored) {
            }
        }
        return result;
    }

    private String combine(String base, String method) {
        if (base == null || base.isEmpty()) {
            return method;
        }
        if (method == null || method.isEmpty()) {
            return base;
        }
        String b = base.endsWith("/") ? base.substring(0, base.length() - 1) : base;
        String m = method.startsWith("/") ? method.substring(1) : method;
        return b + "/" + m;
    }

    private String clean(String s) {
        if (s == null) return "";
        String cleaned = s.replaceAll("^/+", "").replaceAll("/+$", "");
        return cleaned.isEmpty() ? "/" : cleaned;
    }
}
