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
package org.engineer365.platform.user.app.dao;

import org.engineer365.common.dao.jpa.DAOTestBase;
import org.engineer365.common.misc.UuidHelper;
import org.engineer365.platform.user.api.enums.AccountType;
import org.engineer365.platform.user.app.entity.AccountEO;
import org.engineer365.platform.user.app.entity.UserEO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountDAOTest extends DAOTestBase<AccountEO, String, AccountDAO> {

  @Autowired
  public AccountDAOTest(AccountDAO dao) {
    super(dao);
  }

  @Test
  void test_getByCredentialAndType_happy() {
    var u = UserEO.builder().name("n").fullName("fn").root(false).id(UuidHelper.shortUuid()).build();
    getEntityManager().persist(u);

    // 匹配
    var a1 = AccountEO.builder().credential("c").user(u).type(AccountType.EMAIL).password("p1")
        .id(UuidHelper.shortUuid()).build();
    a1 = importEntity(a1);

    // type不匹配
    var a2 = AccountEO.builder().credential("c").user(u).type(AccountType.MOBILE).password("p2")
        .id(UuidHelper.shortUuid()).build();
    a2 = importEntity(a2);

    // credential不匹配
    var a3 = AccountEO.builder().credential("c-X").user(u).type(AccountType.EMAIL).password("p3")
        .id(UuidHelper.shortUuid()).build();
    a3 = importEntity(a3);

    // credential和type都不匹配
    var a4 = AccountEO.builder().credential("c-X").user(u).type(AccountType.MOBILE).password("p4")
        .id(UuidHelper.shortUuid()).build();
    a4 = importEntity(a4);

    var actual = dao().getByCredentialAndType("c", AccountType.EMAIL);
    assertEntity(a1, actual);
  }

}
