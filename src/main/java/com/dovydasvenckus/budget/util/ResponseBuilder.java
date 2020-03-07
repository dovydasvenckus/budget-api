package com.dovydasvenckus.budget.util;

import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.UUID;

public class ResponseBuilder {

    public static ResponseEntity created(String url, UUID id) {
        return ResponseEntity
                .created(buildURI(url, id))
                .build();
    }

    private static String addSeparator(String url) {
        return url.endsWith("/") ? url : url + "/";
    }

    private static URI buildURI(String uri, UUID id) {
        return URI.create(addSeparator(uri) + id.toString());
    }
}
