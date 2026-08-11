package com.example;

import com.generated.HelloResource;

public class HelloResourceImpl implements HelloResource {

    @Override
    public String hello(String name) {
        return "Hello " + name;
    }
}
