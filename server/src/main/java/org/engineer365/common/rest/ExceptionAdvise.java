/*
 * MIT License
 *
 * Copyright (c) 2020 engineer365.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.engineer365.common.rest;

import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.validation.BindException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.engineer365.common.error.BadRequestError;
import org.engineer365.common.error.GenericError;
import org.engineer365.common.error.InternalServerError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 拦截处理RestController抛出的异常。
 * 抛出的异常输出成HTTP response body。
 *
 * @see org.engineer365.common.error.GenericError#toMap()
 */
@lombok.extern.slf4j.Slf4j
@ControllerAdvice(annotations = { RestController.class })
public class ExceptionAdvise {


  @ExceptionHandler(Throwable.class)
  public ResponseEntity<Map<String,Object>> handleError(Throwable ex) {
    var err = normalizeError(ex);

    var status = err.getStatus();
    if (err.getStatus() == HttpStatus.INTERNAL_SERVER_ERROR) {
      log.error(ex.getMessage(), ex);
    } else {
      log.warn(ex.getMessage(), ex);
    }

    return new ResponseEntity<>(err.toMap(), status);
  }

  public static GenericError normalizeError(Throwable ex) {
    if (ex instanceof GenericError) {
      return ((GenericError)ex);
    }

    // TODO: handle InvocationTargetException and root cause
    if (ex instanceof MethodArgumentNotValidException) {
       List<ObjectError> objErrors = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();

      var errors = objErrors.stream().map(objErr -> {
        return String.format("%s: %s", List.of(objErr.getArguments()), objErr.getDefaultMessage());
      }).collect(Collectors.toList());

      return new BadRequestError(BadRequestError.Code.METHOD_ARGUMENT_NOT_VALID, ex, "method argument not valid", errors);
    }

    if (ex instanceof ConstraintViolationException) {
      Set<ConstraintViolation<?>> violations = ((ConstraintViolationException) ex).getConstraintViolations();

      var errors = violations.stream().map(violation-> {
        return String.format("%s: %s", violation.getPropertyPath(), violation.getMessage());
      }).collect(Collectors.toList());

      return new BadRequestError(BadRequestError.Code.CONSTRAINT_VIOLATION, ex, "constraint violated", errors);
    }

    if (ex instanceof BindException ||
        ex instanceof HttpMessageNotReadableException ||
        ex instanceof MissingServletRequestParameterException ||
        ex instanceof MissingServletRequestPartException ||
        ex instanceof TypeMismatchException) {
      return new BadRequestError(BadRequestError.Code.OTHER);
    }

    if (ex instanceof HttpMediaTypeNotAcceptableException) {
      return new GenericError(HttpStatus.NOT_ACCEPTABLE, GenericError.Code.OTHER, ex);
    }
    if (ex instanceof HttpRequestMethodNotSupportedException) {
      return new GenericError(HttpStatus.METHOD_NOT_ALLOWED, GenericError.Code.OTHER, ex);
    }
    if (ex instanceof HttpMediaTypeNotSupportedException) {
      return new GenericError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, GenericError.Code.OTHER, ex);
    }

    if (ex instanceof AsyncRequestTimeoutException) {
      return new GenericError(HttpStatus.REQUEST_TIMEOUT, GenericError.Code.OTHER, ex);
    }

    return new InternalServerError(InternalServerError.Code.OTHER, ex);
  }

}
