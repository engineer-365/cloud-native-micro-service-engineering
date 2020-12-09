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
package org.engineer365.common.service;

import org.engineer365.common.error.GenericError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.opentest4j.AssertionFailedError;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.BeforeEach;


/**
 * Service单元测试的基类，封装了一些浅层次的对Mockito的使用方法。
 *
 * Service单元测试的目的是验证service代码的逻辑正确性，包括和dao的参数/结果的传递。
 */
@Disabled
public abstract class ServiceTestBase {


  @BeforeEach
  public void beforeEach() {
    MockitoAnnotations.openMocks(this);
  }

  public static boolean matchBoolean(boolean that) {
    return ArgumentMatchers.booleanThat(b -> {
      Assertions.assertEquals(Boolean.valueOf(that), b);
      return true;
    });
  }

  public static String matchString(String that) {
    return ArgumentMatchers.argThat((String actual) -> that.equals(actual));
  }

  public static int matchInt(int that) {
    return ArgumentMatchers.intThat(i -> {
      Assertions.assertEquals(Integer.valueOf(that), i);
      return true;
    });
  }

  @SuppressWarnings("unchecked")
  public static <T extends GenericError> T assertThrows (Class<T> expectedType, Enum<?> code, Executable executable) {
      try {
        executable.execute();
      } catch (Throwable actualException) {
        if (expectedType.isInstance(actualException)) {
          return (T) actualException;
        }

        // UnrecoverableExceptions.rethrowIfUnrecoverable(actualException);

        throw new AssertionFailedError("caught unexpected exception", expectedType, actualException.getClass(), actualException);
      }

      throw new AssertionFailedError("no expected exception throw", expectedType, "null");
    }

}
