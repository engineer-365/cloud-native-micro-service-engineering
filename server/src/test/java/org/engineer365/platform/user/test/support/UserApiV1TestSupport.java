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
package org.engineer365.platform.user.test.support;

import org.engineer365.platform.user.api.UserApiV1;
import org.engineer365.platform.user.api.bean.Account;
import org.engineer365.platform.user.api.bean.User;
import org.engineer365.platform.user.api.req.AccountAuthReq;
import org.engineer365.platform.user.api.req.CreateAccountByEmailReq;
import org.engineer365.platform.user.api.req.CreateUserByEmailReq;
import org.engineer365.test.IntegrationTestBase;
import org.engineer365.test.TestContainersFactory;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;

public class UserApiV1TestSupport extends IntegrationTestBase implements UserApiV1 {

  @Rule
  @Container
  final static DockerComposeContainer<?> containers = TestContainersFactory.DEFAULT.build();

  public UserApiV1TestSupport() {
    super("platform/user/api/v1/rest");
  }

  @Override
  public DockerComposeContainer<?> containers() {
    return UserApiV1TestSupport.containers;
  }

  @BeforeEach
  public void beforeEach() {
    truncateTable("user_user");
    truncateTable("user_account");
  }

  @Override
  public Account createUserByEmail(CreateUserByEmailReq req) {
    return null;
  }

  @Override
  public Account createAccountByEmail(CreateAccountByEmailReq req) {
    return null;
  }

  @Override
  public String authByAccount(AccountAuthReq areq) {
    return null;
  }

  @Override
  public User getUser(String userId) {
    var resp = when().get("user/_/{userId}", userId);
    assertResponse(resp);

    String contentLength = resp.header("content-length");
    if ("0".equals(contentLength)) {
      return null;
    }
    return resp.as(User.class);
  }

  @Override
  public Account getAccount(String accountId) {
    return null;
  }

  @Override
  public Account getAccountByEmail(String email) {
    return null;
  }

}
