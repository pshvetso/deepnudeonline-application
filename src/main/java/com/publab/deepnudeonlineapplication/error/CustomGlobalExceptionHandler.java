package com.publab.deepnudeonlineapplication.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        final String msg = "Constraint violation";

        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + ": " + violation.getMessage());
        }

        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, msg, errors);
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    //CommonsMultipartResolver
    /*@ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("message", e.getCause().getMessage());
        return "redirect:/uploadStatus";

    }*/
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
    @ResponseBody
    protected ResponseEntity<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex,
                                                                            final HttpServletRequest request,
                                                                            final HttpServletResponse response) throws IOException {
        String msg = "Error sending message.";
        final String SizeLimitExceededException = "org.apache.tomcat.util.http.fileupload.FileUploadBase$SizeLimitExceededException";
        //org.apache.tomcat.util.http.fileupload.FileUploadBase$SizeLimitExceededException: the request was rejected because its size (520771) exceeds the configured maximum (51200)
        if(ex.getCause().getMessage().substring(0, SizeLimitExceededException.length()).equals(SizeLimitExceededException)) {
            msg = "Error sending message: attachment size too large.";
        }

        List<String> errors = Collections.singletonList(msg);
        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, msg, errors);
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

}