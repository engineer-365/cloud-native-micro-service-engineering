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
import org.engineer365.test.RestTestBase;
import org.engineer365.platform.user.api.bean.Account;
import org.engineer365.platform.user.api.enums.AccountType;
import org.engineer365.platform.user.api.req.AccountAuthReq;
import org.engineer365.platform.user.api.req.CreateAccountByEmailReq;
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
@ContextConfiguration(classes = AccountApiV1RestController.class)
@WebMvcTest(AccountApiV1RestController.class)
public class AccountApiV1RestControllerTest extends RestTestBase {

    @MockBean
    private UserApiV1Service service;

    @Test
    void test_getAccount_got() {

        getThenExpectOk(null, "/platform/user/api/v1/rest/account/_/{accountId}", UuidHelper.shortUuid());

        String id = UuidHelper.shortUuid();

        var account = Account.builder().id(id).type(AccountType.EMAIL).version(123).credential("engineers@engineer-365")
                .createdAt(new Date()).updatedAt(new Date()).build();

        when(this.service.getAccount(id)).thenReturn(account);

        getThenExpectOk(account, "/platform/user/api/v1/rest/account/_/{accountId}", id);
    }

    @Test
    void test_createAccountByEmail() {
        var req = CreateAccountByEmailReq.builder().email("engineers@engineer-365").userId(UuidHelper.shortUuid())
                .password("blah").build();

        var account = Account.builder().id(UuidHelper.shortUuid()).credential(req.getEmail()).type(AccountType.EMAIL)
                .userId(req.getUserId()).version(345).createdAt(new Date()).updatedAt(new Date()).build();

        when(this.service.createAccountByEmail(req)).thenReturn(account);

        postThenExpectOk(req, account, "/platform/user/api/v1/rest/account/createAccountByEmail");
    }

    @Test
    void test_getAccountByEmail() {
        getThenExpectOk(null, "/platform/user/api/v1/rest/account/getAccountByEmail?email=xxx");

        String email = "engineers@engineer-365";

        var account = Account.builder().id(UuidHelper.shortUuid()).type(AccountType.EMAIL).version(123)
                .credential(email).createdAt(new Date()).updatedAt(new Date()).build();

        when(this.service.getAccountByEmail(email)).thenReturn(account);

        getThenExpectOk(account, "/platform/user/api/v1/rest/account/getAccountByEmail?email={email}", email);
    }

    @Test
    void test_authByAccount() {
        var req = AccountAuthReq.builder().accountId(UuidHelper.shortUuid()).password("blah").build();

        String pseudoToken = UuidHelper.shortUuid();

        when(this.service.authByAccount(req)).thenReturn(pseudoToken);

        postThenExpectOk(req, pseudoToken, "/platform/user/api/v1/rest/account/authByAccount");
    }

}
