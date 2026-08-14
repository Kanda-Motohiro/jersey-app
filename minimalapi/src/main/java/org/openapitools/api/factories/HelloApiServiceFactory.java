package org.openapitools.api.factories;

import org.openapitools.api.HelloApiService;
import org.openapitools.api.impl.HelloApiServiceImpl;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2026-08-14T16:15:39.269699308+09:00[Asia/Tokyo]", comments = "Generator version: 7.24.0")
public class HelloApiServiceFactory {
    private static final HelloApiService service = new HelloApiServiceImpl();

    public static HelloApiService getHelloApi() {
        return service;
    }
}
