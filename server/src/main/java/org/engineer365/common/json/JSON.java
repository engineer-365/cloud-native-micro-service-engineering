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
package org.engineer365.common.json;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Json工具类
 */
public class JSON {

  public static final ThreadLocal<JacksonHelper> MAPPER
    = ThreadLocal.withInitial(() -> new JacksonHelper(JacksonHelper.buildMapper()));

  /** 序列化成易读格式的JSON字符串 */
  public static String pretty(Object object) {
    return to(object, true);
  }

  /** 序列化成压缩格式的JSON字符串 */
  public static String to(Object object) {
    return to(object, false);
  }

  /**
   * 序列化成指定格式的JSON字符串
   *
   * @param object 待序列化成JSON字符串的对象
   * @param pretty true表示转成易读格式，否则转成压缩格式
   */
  public static String to(Object object, boolean pretty) {
    return MAPPER.get().to(object, pretty);
  }

  /**
   * 把JSON反序列化成指定类型的对象
   *
   * @param text  JSON字符串
   * @param clazz 指定类型
   */
  public static <T> T from(String json, Class<T> clazz) {
    return MAPPER.get().from(json, clazz);
  }


  /**
   * 把JSON反序列化成指定类型引用的对象，通常用于generic对象
   *
   * @param text  JSON字符串
   * @param clazz 指定类型引用
   */
  public static <T> T from(String json, TypeReference<T> typeReference) {
    return MAPPER.get().from(json, typeReference);
  }

}
