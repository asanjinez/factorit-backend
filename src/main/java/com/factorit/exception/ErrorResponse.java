package com.factorit.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        String code,
        String message,
        int status,
        String path,
        LocalDateTime timestamp
) {
}
