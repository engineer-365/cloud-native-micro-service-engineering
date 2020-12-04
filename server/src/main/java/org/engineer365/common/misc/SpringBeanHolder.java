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

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.engineer365.common.error.InternalServerError;
import org.springframework.stereotype.Component;

/**
 * 用于某些特殊场景下获取Spring Bean实例的工具。大部分场景下使用@Autowired等注解，应该避免使用这个工具。
 */
@Component
public class SpringBeanHolder implements ApplicationContextAware {

  private static ApplicationContext context;

  public static <T> T getBean(Class<T> clazz) {
    return context.getBean(clazz);
  }

  public static <T> T getBean(String name, Class<T> clazz) {
    return context.getBean(name, clazz);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    if (context != null) {
      throw new InternalServerError(InternalServerError.Code.OTHER, "context already initialized");
    }
    SpringBeanHolder.context = applicationContext;
  }

}
