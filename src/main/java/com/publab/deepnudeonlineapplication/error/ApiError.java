package com.publab.deepnudeonlineapplication.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@RequiredArgsConstructor
class ApiError {
    private final HttpStatus status;
    private final String message;
    private final List<String> errors;
}
