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
package org.engineer365.common.misc;

import java.util.Collection;
import java.util.Objects;

import com.google.common.collect.Lists;

/**
 * 字符串操作的工具类
 */
public class StringHelper {

  /** 数组的toString */
  public static <T> String toString(T[] array) {
    if (array == null) {
      return null;
    }
    return Lists.newArrayList(array).toString();
  }

  /**
   * 把多个字符串用指定分隔符连接
   *
   * @param separator 分隔
   * @param texts 待连接的多个字符串
   */
  public static <T> String join(String separator, Collection<T> texts) {
    return join(separator, texts.toArray(new String[texts.size()]));
  }

  /**
   * 数组的toString，用指定分隔符连接
   *
   * @param separator 分隔符
   * @param array 数组
   */
  public static <T> String join(String separator, T[] array) {
    var r = new StringBuilder(array.length * 64);
    var isFirst = true;
    for (var obj : array) {
      if (isFirst) {
        isFirst = false;
      } else {
        r.append(separator);
      }
      r.append(Objects.toString(obj));
    }
    return r.toString();
  }

  /**
   * 是否是null或全是空白字符串
   */
  public static boolean isBlank(String str) {
    return (str == null || str.length() == 0 || str.trim().length() == 0);
  }

  /**
   * 是否不会null而且不全是空白字符串
   */
  public static boolean notBlank(String str) {
    return !isBlank(str);
  }

}
