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
package org.engineer365.common.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import org.springframework.context.annotation.Configuration;
import org.engineer365.common.json.JacksonHelper;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
@lombok.Getter
@lombok.Setter
@EnableWebMvc
@org.springframework.context.annotation.Import({
  ExceptionAdvise.class,
  RequestContextInterceptor.class
})
public class RestConfig implements WebMvcConfigurer {

  @Autowired
  RequestContextInterceptor requestContextInterceptor;

  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    for (int i = converters.size() - 1; i >= 0; i--) {
      var converter = converters.get(i);
      if (converter instanceof MappingJackson2HttpMessageConverter) {
        converters.remove(i);
      }
    }

    var mapper = JacksonHelper.buildMapper();
    var jacksonConverter = new MappingJackson2HttpMessageConverter(mapper);

    converters.add(0, jacksonConverter);
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    var ci = getRequestContextInterceptor();
    registry.addInterceptor(ci).addPathPatterns(ci.getPathPatterns());
  }

}
