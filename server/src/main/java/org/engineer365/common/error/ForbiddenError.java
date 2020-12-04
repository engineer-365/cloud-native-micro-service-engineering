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
 * 对应HTTP FORBIDDEN
 */
public class ForbiddenError extends GenericError {

  public static enum Code {
    OTHER,
    CONTEXT_NOT_FOUND,
    USER_ID_NOT_FOUND_IN_CONTEXT,
  }

  /**
   *
   */
  private static final long serialVersionUID = -4509198240300211902L;

  public ForbiddenError(Enum<?> code, String noteFormat, Object... noteParams) {
    super(HttpStatus.FORBIDDEN, code, noteFormat, noteParams);
  }

  public ForbiddenError(Enum<?> code, String note) {
    super(HttpStatus.FORBIDDEN, code, note);
  }

  public ForbiddenError(Enum<?> code) {
    super(HttpStatus.FORBIDDEN, code);
  }

  public ForbiddenError(Enum<?> code, Throwable cause, String noteFormat, Object... noteParams) {
    super(HttpStatus.FORBIDDEN, code, cause, noteFormat, noteParams);
  }

  public ForbiddenError(Enum<?> code, Throwable cause, String note) {
    super(HttpStatus.FORBIDDEN, code, cause, note);
  }

  public ForbiddenError(Enum<?> code, Throwable cause) {
    super(HttpStatus.FORBIDDEN, code, cause);
  }
}
