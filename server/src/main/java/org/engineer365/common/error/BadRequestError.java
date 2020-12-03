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
package org.engineer365.common.error;

import org.springframework.http.HttpStatus;

/**
 * 对应HTTP BAD_REQUEST
 */
public class BadRequestError extends GenericError {

  private static final long serialVersionUID = -7899971579299772118L;

  public enum Code {
    OTHER,
    METHOD_ARGUMENT_NOT_VALID,
    CONSTRAINT_VIOLATION,
    WRONG_FORMAT,
  }

  public BadRequestError(Enum<?> code, String noteFormat, Object... noteParams) {
    super(HttpStatus.BAD_REQUEST, code, noteFormat, noteParams);
  }

  public BadRequestError(Enum<?> code, String note) {
    super(HttpStatus.BAD_REQUEST, code, note);
  }

  public BadRequestError(Enum<?> code) {
    super(HttpStatus.BAD_REQUEST, code);
  }

  public BadRequestError(Enum<?> code, Throwable cause, String noteFormat, Object... noteParams) {
    super(HttpStatus.BAD_REQUEST, code, cause, noteFormat, noteParams);
  }

  public BadRequestError(Enum<?> code, Throwable cause, String note) {
    super(HttpStatus.BAD_REQUEST, code, cause, note);
  }

  public BadRequestError(Enum<?> code, Throwable cause) {
    super(HttpStatus.BAD_REQUEST, code, cause);
  }

}
