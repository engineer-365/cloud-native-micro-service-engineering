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
package org.engineer365.common;

import java.util.Set;


import org.engineer365.common.error.ForbiddenError;
import org.engineer365.common.misc.StringHelper;
import org.engineer365.common.security.Role;
import org.engineer365.common.bean.Dumpable;

/**
 * 当前API请求的上下文（主要是用户和权限等）
 */
@lombok.Getter
@lombok.Setter
public class RequestContext extends Dumpable {

  static final ThreadLocal<RequestContext> CURRENT = new ThreadLocal<>();

  String userId;

  Set<Role> roles;

  public static RequestContext bind(RequestContext ctx) {
    var old = current();
    CURRENT.set(ctx);
    LogTrace.setContext(ctx);

    return old;
  }

  public static RequestContext copy(RequestContext that) {
    var r = new RequestContext();

    if (that != null) {
      r.setUserId(that.getUserId());
      r.setRoles(that.getRoles());
    }

    return r;
  }

  public static void clear() {
    LogTrace.clear();
    CURRENT.remove();
  }

  public static RequestContext current() {
    return CURRENT.get();
  }

  public static RequestContext load(boolean ensureHasUserId) {
    var r = current();
    if (r == null) {
      throw new ForbiddenError(ForbiddenError.Code.CONTEXT_NOT_FOUND);
    }

    if (ensureHasUserId && StringHelper.isBlank(r.getUserId())) {
      throw new ForbiddenError(ForbiddenError.Code.USER_ID_NOT_FOUND_IN_CONTEXT);
    }

    return r;
  }

}
