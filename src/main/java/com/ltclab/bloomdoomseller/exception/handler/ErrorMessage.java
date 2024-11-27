package com.ltclab.bloomdoomseller.exception.handler;

import java.time.LocalDateTime;

public record ErrorMessage(
        LocalDateTime localDateTime,
        String message,
        String path
) {
}
