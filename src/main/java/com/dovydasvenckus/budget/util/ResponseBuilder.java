package com.dovydasvenckus.budget.util;

import org.springframework.http.ResponseEntity;

import java.net.URI;

public class ResponseBuilder {

    public static ResponseEntity created(String url, Long id) {
        return ResponseEntity
                .created(buildURI(url, id))
                .build();
    }

    private static String addSeparator(String url) {
        return url.endsWith("/") ? url : url + "/";
    }

    private static URI buildURI(String uri, Long id) {
        return URI.create(addSeparator(uri) + id);
    }
}
