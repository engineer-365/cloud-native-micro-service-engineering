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
 * 对应HTTP UNAUTHORIZED
 */
public class UnauthorizedError extends GenericError {

  /**
   *
   */
  private static final long serialVersionUID = -6453279321344702468L;

  public UnauthorizedError(String messageFormat, Object... params) {
    super(HttpStatus.UNAUTHORIZED, messageFormat, params);
  }

  public UnauthorizedError(String message) {
    super(HttpStatus.UNAUTHORIZED, message);
  }

  public UnauthorizedError(HttpStatus status) {
    super(HttpStatus.UNAUTHORIZED);
  }

  public UnauthorizedError(Exception cause, String messageFormat, Object... params) {
    super(cause, HttpStatus.UNAUTHORIZED, messageFormat, params);
  }

  public UnauthorizedError(Exception cause, String message) {
    super(cause, HttpStatus.UNAUTHORIZED, message);
  }

  public UnauthorizedError(Exception cause) {
    super(cause, HttpStatus.UNAUTHORIZED);
  }

}
