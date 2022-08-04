package com.nepxion.discovery.guide.service.context;

import java.util.HashMap;
import java.util.Map;

public class MyContext{
    private static final ThreadLocal<MyContext> THREAD_LOCAL = new ThreadLocal<MyContext>() {
        @Override
        protected MyContext initialValue() {
            return new MyContext();
        }
    };

    public static MyContext getCurrentContext() {
        return THREAD_LOCAL.get();
    }

    public static void clearCurrentContext() {
        THREAD_LOCAL.remove();
    }

    private Map<String, String> attributes = new HashMap<>();

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}
