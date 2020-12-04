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

/**
 * 代表由外部第三方服务引发的异常
 */
public class ThirdPartyError extends InternalServerError {

  public enum Code {
    OTHER
  }

  private static final long serialVersionUID = 2392404936091322485L;

  public ThirdPartyError(Enum<?> code, String noteFormat, Object... noteParams) {
    super(code, noteFormat, noteParams);
  }

  public ThirdPartyError(Enum<?> code, String note) {
    super(code, note);
  }

  public ThirdPartyError(Enum<?> code, Throwable cause, String noteFormat, Object... noteParams) {
    super(code, cause, noteFormat, noteParams);
  }

  public ThirdPartyError(Enum<?> code, Throwable cause, String note) {
    super(code, cause, note);
  }

  public ThirdPartyError(Enum<?> code, Throwable cause) {
    super(code, cause);
  }

}
