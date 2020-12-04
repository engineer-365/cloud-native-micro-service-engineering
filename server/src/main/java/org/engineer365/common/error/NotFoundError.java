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
 * 对应HTTP NOT_FOUND
 */
public class NotFoundError extends GenericError {

  public enum Code {
    OTHER,
    ENTITY_NOT_FOUND
  }

  private static final long serialVersionUID = -6244828726201482351L;

  public NotFoundError(Enum<?> code, String noteFormat, Object... noteParams) {
    super(HttpStatus.NOT_FOUND, code, noteFormat, noteParams);
  }

  public NotFoundError(Enum<?> code, String note) {
    super(HttpStatus.NOT_FOUND, code, note);
  }

  public NotFoundError(Enum<?> code) {
    super(HttpStatus.NOT_FOUND, code);
  }

  public NotFoundError(Enum<?> code, Throwable cause, String noteFormat, Object... noteParams) {
    super(HttpStatus.NOT_FOUND, code, cause, noteFormat, noteParams);
  }

  public NotFoundError(Enum<?> code, Throwable cause, String note) {
    super(HttpStatus.NOT_FOUND, code, cause, note);
  }

  public NotFoundError(Enum<?> code, Throwable cause) {
    super(HttpStatus.NOT_FOUND, code, cause);
  }

}
