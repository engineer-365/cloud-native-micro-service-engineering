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

import java.util.Map;

import org.springframework.http.HttpStatus;

/**
 * 所有异常类的基类。
 * 一般不直接使用，一般直接使用它的某个子类。
 */
@lombok.Getter
public class GenericError extends RuntimeException {

  private static final long serialVersionUID = -6956880450354038826L;

  /**
   * 错误代码。
   */
  HttpStatus status;

  /**
   * 错误消息的参数
   */
  Object[] params;

  /**
   * 无级联异常时抛出的异常，可以定义消息格式和消息参数
   *
   * @param status 错误代码
   * @param messageFormat 消息格式，用于String.format(messageFormat, params
   * @param params 消息参数，用于String.format(messageFormat, params
   */
  protected GenericError(HttpStatus status, String messageFormat, Object... params) {
    super(String.format(messageFormat, params));

    this.status = status;
    this.params = params;
  }

  /**
   * 无级联异常时抛出的异常，只可以传递简单消息文字
   *
   * @param status 错误代码
   * @param message 肩带消息文字
   */
  protected GenericError(HttpStatus status, String message) {
    super(message);

    this.status = status;
    this.params = new Object[0];
  }

  /**
   * 无级联异常时抛出的异常，不用传递简单消息文字，消息文字直接使用错误代码所表示的错误消息
   *
   * @param status 错误代码
   */
  protected GenericError(HttpStatus status) {
    this(status, status.getReasonPhrase());
  }

  /**
   * 带有级联异常时抛出的异常，可以定义消息格式和消息参数
   *
   * @param cause 级联异常
   * @param status 错误代码
   * @param messageFormat 消息格式，用于String.format(messageFormat, params
   * @param params 消息参数，用于String.format(messageFormat, params
   */
  protected GenericError(Exception cause, HttpStatus status, String messageFormat, Object... params) {
    super(String.format(messageFormat, params), cause);

    this.status = status;
    this.params = params;
  }

  /**
   * 带有级联异常时抛出的异常，只可以传递简单消息文字
   *
   * @param cause 级联异常
   * @param status 错误代码
   * @param message 肩带消息文字
   */
  protected GenericError(Exception cause, HttpStatus status, String message) {
    super(message, cause);

    this.status = status;
    this.params = new Object[0];
  }

  /**
   * 带有级联异常时抛出的异常，不用传递简单消息文字，消息文字直接使用错误代码所表示的错误消息
   *
   * @param cause 级联异常
   * @param status 错误代码
   */
  protected GenericError(Exception cause, HttpStatus status) {
    this(status, status.getReasonPhrase(), cause);
  }

  /**
   * 导出成Map。不包括级联异常。对于RESTful API，这个map就是HTTP response body
   * @return
   */
  public Map<String, Object> toMap() {
    return Map.of(
      "status", status.name(),
      "params", params,
      "message", getMessage()
    );
  }

}
