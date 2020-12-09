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
package org.engineer365.platform.user.app.service;

import org.engineer365.platform.user.api.enums.AccountType;
import org.engineer365.platform.user.api.enums.ErrorCode;
import org.engineer365.platform.user.api.req.AccountAuthReq;
import org.engineer365.platform.user.api.req.CreateAccountByEmailReq;
import org.engineer365.platform.user.app.dao.AccountDAO;
import org.engineer365.platform.user.app.entity.AccountEO;
import org.engineer365.platform.user.app.entity.UserEO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.engineer365.common.error.BadRequestError;
import org.engineer365.common.error.NotFoundError;
import org.engineer365.common.service.ServiceTestBase;


public class AccountServiceTest extends ServiceTestBase {

    @Mock
    AccountDAO accountDao;

    @InjectMocks
    AccountService target;

    @Test
    void test_checkRequestWithAccount_ACCOUNT_NOT_FOUND() {
        assertThrows (NotFoundError.class, ErrorCode.ACCOUNT_NOT_FOUND,
            () -> this.target.checkRequestWithAccount(null, new AccountAuthReq()));
        assertThrows (NotFoundError.class, ErrorCode.ACCOUNT_NOT_FOUND,
            () -> this.target.checkRequestWithAccount(null, null));
    }

    @Test
    void test_checkRequestWithAccount_PASSWORD_NOT_MATCHES() {
        var a = AccountEO.builder().password("abc").build();
        var req = AccountAuthReq.builder().password("def").build();

        assertThrows(BadRequestError.class, ErrorCode.WRONG_PASSWORD,
            () -> this.target.checkRequestWithAccount(a, req));
    }

    @Test
    void test_checkRequestWithAccount_OK() {
        var a = AccountEO.builder().password("abc").build();
        var req = AccountAuthReq.builder().password("abc").build();

        this.target.checkRequestWithAccount(a, req);
    }

    @Test
    void test_auth_accountNotFound() {
        var req = AccountAuthReq.builder().accountId("a-1").password("p").build();

        when(this.accountDao.get(false, "a-1")).thenReturn(null);

        assertThrows(NotFoundError.class, ErrorCode.ACCOUNT_NOT_FOUND,
            () -> this.target.authByAccount(req));
    }

    @Test
    void test_auth_ok() {
        var req = AccountAuthReq.builder().accountId("a-1").password("p").build();
        var a = AccountEO.builder().id("a-1").password("p").credential("noreply@wxcount.com").type(AccountType.EMAIL).build();

        when(this.accountDao.get(false, "a-1")).thenReturn(a);

        var r = this.target.authByAccount(req);
        Assertions.assertEquals(a.getId(), r);
    }

    @Test
    void test_createAccountByEmail_happy() {
        var accountId = "a-1";
        var user = UserEO.builder().id("u-1").build();

        var req = CreateAccountByEmailReq.builder().password("p").userId(user.getId()).email("noreply@wxcount.com").build();

        when(this.accountDao.save(ArgumentMatchers.any())).thenReturn(new AccountEO());
        //when(this.emailSender.send(ArgumentMatchers.any())).thenReturn(null);

        this.target.createAccountByEmail(accountId, req, user);

        verify(this.accountDao).save(argThat((AccountEO entity) -> {
            Assertions.assertNotNull(entity.getId());
            Assertions.assertEquals(req.getPassword(), entity.getPassword());
            Assertions.assertSame(user, entity.getUser());
            return true;
        }));
    }

}
