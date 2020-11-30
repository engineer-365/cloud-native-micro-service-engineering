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

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.engineer365.common.error.InternalError;
import org.engineer365.common.misc.StringHelper;

/**
 * Jackson工具类。
 * 通常使用@org.engineer365.common.json.JSON，不直接使用这个类。
 */
@lombok.Getter
public class JacksonHelper {

  public final ObjectMapper mapper;

  JacksonHelper(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  public static ObjectMapper buildMapper() {
    var r = new ObjectMapper();
    initMapper(r);
    return r;
  }

  public static void initMapper(ObjectMapper mapper) {
    var dateModule = new SimpleModule();
    dateModule.addSerializer(Date.class, new DateSerializer());
    dateModule.addDeserializer(Date.class, new DateDeserialize());
    mapper.registerModule(dateModule);

    mapper.setSerializationInclusion(Include.NON_NULL);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    mapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
  }

  /**
   * 把JSON反序列化成指定类型的对象
   *
   * @param text  JSON字符串
   * @param clazz 指定类型
   */
  public <T> T from(String text, Class<T> clazz) {
    if (StringHelper.isBlank(text)) {
      return null;
    }

    try {
      return getMapper().readValue(text, clazz);
    } catch (IOException e) {
      throw new InternalError(null, e);
    }
  }

  /**
   * 把JSON反序列化成指定类型引用的对象，通常用于generic对象
   *
   * @param text  JSON字符串
   * @param clazz 指定类型引用
   */
  public <T> T from(String text, TypeReference<T> typeReference) {
    if (StringHelper.isBlank(text)) {
      return null;
    }

    try {
      return getMapper().readValue(text, typeReference);
    } catch (IOException e) {
      throw new InternalError(null, e);
    }
  }

  /** 序列化成易读格式的JSON字符串 */
  public String pretty(Object object) {
    return to(object, true);
  }

  /** 序列化成压缩格式的JSON字符串 */
  public String to(Object object) {
    return to(object, false);
  }

  /**
   * 序列化成指定格式的JSON字符串
   *
   * @param object 待序列化成JSON字符串的对象
   * @param pretty true表示转成易读格式，否则转成压缩格式
   */
  public String to(Object object, boolean pretty) {
    if (object == null) {
      return null;
    }

    try {
      if (pretty) {
        return getMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);
      }
      return getMapper().writeValueAsString(object);
    } catch (IOException e) {
      throw new InternalError(null, e);
    }
  }
}
