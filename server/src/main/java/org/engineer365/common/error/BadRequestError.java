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

  public BadRequestError(String messageFormat, Object... params) {
    super(HttpStatus.BAD_REQUEST, messageFormat, params);
  }

  public BadRequestError(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }

  public BadRequestError(HttpStatus status) {
    super(HttpStatus.BAD_REQUEST);
  }

  public BadRequestError(Exception cause, String messageFormat, Object... params) {
    super(cause, HttpStatus.BAD_REQUEST, messageFormat, params);
  }

  public BadRequestError(Exception cause, String message) {
    super(cause, HttpStatus.BAD_REQUEST, message);
  }

  public BadRequestError(Exception cause) {
    super(cause, HttpStatus.BAD_REQUEST);
  }

}
