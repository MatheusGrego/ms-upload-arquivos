package com.msuploadarquivos.controllers;

import com.msuploadarquivos.model.response.ErrorResponse;
import com.msuploadarquivos.exceptions.TooManyFilesException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
public class FileUploadExceptionController {

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handlerTamanhoMaximo(MaxUploadSizeExceededException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.PAYLOAD_TOO_LARGE.value(), "Permitimos arquivos até " + e.getMaxUploadSize() + " MB!");
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(errorResponse);
    }

    @ExceptionHandler(value = MultipartException.class)
    public ResponseEntity<ErrorResponse> handlerArquivoPermitido(MultipartException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), "Tipo de arquivo não permitido!");
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(value = TooManyFilesException.class)
    public ResponseEntity<ErrorResponse> handlerVariosArquivos(TooManyFilesException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Não permitimos mais de um arquivo!");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
