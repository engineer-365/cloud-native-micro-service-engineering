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
package org.engineer365.platform.user.app.controller;

import org.engineer365.common.misc.UuidHelper;
import org.engineer365.common.rest.RestTestBase;
import org.engineer365.platform.user.api.bean.Account;
import org.engineer365.platform.user.api.bean.User;
import org.engineer365.platform.user.api.enums.AccountType;
import org.engineer365.platform.user.api.req.CreateUserByEmailReq;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;

import java.util.Date;

import org.springframework.test.context.ContextConfiguration;
import org.engineer365.platform.user.app.service.UserApiV1Service;
import org.junit.jupiter.api.Test;

/**
 * @author
 *
 */
@ContextConfiguration(classes = UserApiV1RestController.class)
@WebMvcTest(UserApiV1RestController.class)
public class UserApiV1RestControllerTest extends RestTestBase {

    @MockBean
    private UserApiV1Service service;

    @Test
    void test_getUser() {
        getThenExpectOk(null, "/platform/user/api/v1/rest/user/_/1234567890");

        String id = UuidHelper.shortUuid();

        var user = new User();
        user.setId(id);
        user.setFullName("fn");
        user.setVersion(123);
        user.setPrimaryAccountId(UuidHelper.shortUuid());
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());

        when(this.service.getUser(id)).thenReturn(user);

        getThenExpectOk(user, "/platform/user/api/v1/rest/user/_/{userId}", id);
    }

    @Test
    void test_createUserByEmail() {
        var req = new CreateUserByEmailReq();
        req.setEmail("engineers@engineer-365");
        req.setFullName("engineer-365 engineers");
        req.setName("engineers");
        req.setPassword("blah");

        var account = new Account();
        account.setId(UuidHelper.shortUuid());
        account.setCredential(req.getEmail());
        account.setType(AccountType.EMAIL);
        account.setUserId(UuidHelper.shortUuid());
        account.setVersion(234);
        account.setCreatedAt(new Date());
        account.setUpdatedAt(new Date());

        when(this.service.createUserByEmail(req)).thenReturn(account);

        postThenExpectOk(req, account, "/platform/user/api/v1/rest/user/createUserByEmail");
    }

}
