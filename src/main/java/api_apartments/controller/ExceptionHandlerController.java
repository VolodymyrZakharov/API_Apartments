package api_apartments.controller;

import api_apartments.dto.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponseDTO> handleError(ResponseStatusException ex) {

        return ResponseEntity
                .status(ex.getStatus())
                .body(ErrorResponseDTO.builder()
                        .status(ex.getStatus())
                        .message(ex.getReason())
                        .build()
                );
    }
}
