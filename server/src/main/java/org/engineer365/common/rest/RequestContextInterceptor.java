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

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.engineer365.common.RequestContext;
import org.engineer365.common.security.Role;

/**
 */
@lombok.Getter
@lombok.Setter
@Component
public class RequestContextInterceptor implements AsyncHandlerInterceptor {

  @Value("${org.engineer365.common.rest.request-context.interceptor.path-patterns}")
  String pathPatterns;

  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
    buildContext(req);
    return true;
  }

  public void buildContext(HttpServletRequest req) {
    var ctx = new RequestContext();
    ctx.setUserId(resolveUserId(req));
    ctx.setRoles(resolveRoles(req));

    RequestContext.bind(ctx);
  }

  @Override
  public void afterConcurrentHandlingStarted(HttpServletRequest req, HttpServletResponse response, Object handler)
      throws Exception {
    RequestContext.clear();
  }

  @Override
  public void afterCompletion(HttpServletRequest req, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    RequestContext.clear();
  }

  @Override
  public void postHandle(HttpServletRequest req, HttpServletResponse response, Object handler,
    org.springframework.web.servlet.ModelAndView modelAndView) {
    RequestContext.clear();
  }

  public static String resolveUserId(HttpServletRequest req) {
    return req.getHeader(HttpHeader.USER_ID);
  }

  public static Set<Role> resolveRoles(HttpServletRequest req) {
    String roleNames = req.getHeader(HttpHeader.ROLES);
    return Role.parse(roleNames);
  }

}
