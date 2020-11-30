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

import java.time.Duration;
import java.util.Date;

import org.engineer365.platform.user.api.enums.AccountType;
import org.engineer365.platform.user.api.enums.AuthResultCode;
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

import org.engineer365.common.misc.DateHelper;
import org.engineer365.common.service.ServiceTestBase;


public class AccountServiceTest extends ServiceTestBase {

    @Mock
    AccountDAO accountDao;

    @InjectMocks
    AccountService target;

    @Test
    void test_checkReqWithAccount_ACCOUNT_NOT_FOUND() {
        Assertions.assertEquals(AuthResultCode.ACCOUNT_NOT_FOUND,
                this.target.checkReqWithAccount(null, new AccountAuthReq()));
        Assertions.assertEquals(AuthResultCode.ACCOUNT_NOT_FOUND, this.target.checkReqWithAccount(null, null));
    }

    @Test
    void test_checkReqWithAccount_SALT_NOT_SUPPORTED_YET() {
        var a = new AccountEO();
        a.setSalted(true);

        Assertions.assertEquals(AuthResultCode.SALT_NOT_SUPPORTED_YET,
                this.target.checkReqWithAccount(a, new AccountAuthReq()));
    }

    @Test
    void test_checkReqWithAccount_PASSWORD_NOT_MATCHES() {
        var a = new AccountEO();
        a.setSalted(false);
        a.setPassword("abc");

        var req = new AccountAuthReq();
        req.setPassword("def");

        Assertions.assertEquals(AuthResultCode.PASSWORD_NOT_MATCHES, this.target.checkReqWithAccount(a, req));
    }

    @Test
    void test_checkReqWithAccount_NOT_EFFECTIVE_YET() {
        var now = DateHelper.now();

        var a = new AccountEO();
        a.setSalted(false);
        a.setPassword("abc");
        a.setEffectiveBegin(new Date(now.getTime() + 1000));
        a.setEffectiveEnd(a.getEffectiveBegin());

        var req = new AccountAuthReq();
        req.setPassword("abc");

        Assertions.assertEquals(AuthResultCode.NOT_EFFECTIVE_YET, this.target.checkReqWithAccount(a, req));
    }

    @Test
    void test_checkReqWithAccount_EXPIRED() {
        var now = DateHelper.now();

        var a = new AccountEO();
        a.setSalted(false);
        a.setPassword("abc");
        a.setEffectiveBegin(new Date(now.getTime() - 10000));
        a.setEffectiveEnd(new Date(now.getTime() - 1000));

        var req = new AccountAuthReq();
        req.setPassword("abc");

        Assertions.assertEquals(AuthResultCode.EXPIRED, this.target.checkReqWithAccount(a, req));
    }

    @Test
    void test_checkReqWithAccount_OK() {
        var now = DateHelper.now();

        var a = new AccountEO();
        a.setSalted(false);
        a.setPassword("abc");
        a.setEffectiveBegin(new Date(now.getTime() - 1000));
        a.setEffectiveEnd(new Date(now.getTime() + 1000));

        var req = new AccountAuthReq();
        req.setPassword("abc");

        Assertions.assertEquals(AuthResultCode.OK, this.target.checkReqWithAccount(a, req));
    }

    @Test
    void test_auth_accountNotFound() {
        var req = new AccountAuthReq();
        req.setAccountId("a-1");
        req.setClientAddress("c-1");
        req.setApp("pp5");
        req.setService("transfer");
        req.setPassword("p");

        when(this.accountDao.get(false, "a-1")).thenReturn(null);

        var r = this.target.authByAccount(req);
        Assertions.assertEquals(AuthResultCode.ACCOUNT_NOT_FOUND, r);

        verify(this.authHistoryDao).save(argThat((AuthHistoryEO h) -> {
            Assertions.assertNotNull(h.getId());
            Assertions.assertNull(h.getAccount());
            Assertions.assertEquals(req.getClientAddress(), h.getClientAddress());
            Assertions.assertEquals(req.getApp(), h.getApp());
            Assertions.assertEquals(req.getService(), h.getService());
            Assertions.assertEquals(req.getPassword(), h.getPassword());
            Assertions.assertEquals(AuthResultCode.ACCOUNT_NOT_FOUND, h.getResultCode());
            return true;
        }));
    }

    @Test
    void test_auth_ok() {
        var req = new AccountAuthReq();
        req.setAccountId("a-1");
        req.setClientAddress("c-1");
        req.setApp("pp5");
        req.setService("transfer");
        req.setPassword("p");

        var now = DateHelper.now();

        var a = new AccountEO();
        a.setSalted(false);
        a.setPassword("p");
        a.setEffectiveBegin(new Date(now.getTime() - 1000));
        a.setEffectiveEnd(new Date(now.getTime() + 1000));
        a.setKey("noreply@wxcount.com");
        a.setType(AccountType.EMAIL);

        when(this.accountDao.get(false, "a-1")).thenReturn(a);

        var r = this.target.authByAccount(req);
        Assertions.assertEquals(AuthResultCode.OK, r);

        verify(this.authHistoryDao).save(argThat((AuthHistoryEO h) -> {
            Assertions.assertNotNull(h.getId());
            Assertions.assertSame(a, h.getAccount());
            Assertions.assertEquals(req.getClientAddress(), h.getClientAddress());
            Assertions.assertEquals(req.getApp(), h.getApp());
            Assertions.assertEquals(req.getService(), h.getService());
            Assertions.assertEquals(req.getPassword(), h.getPassword());
            Assertions.assertEquals(AuthResultCode.OK, h.getResultCode());
            return true;
        }));
    }

    @Test
    void test_createAccountByEmail_happy() {
        var now = DateHelper.now();

        var accountId = "a-1";
        var user = new UserEO();
        user.setId("u-1");

        var req = new CreateAccountByEmailReq();
        req.setEffectiveBegin(new Date(now.getTime() - 1000));
        req.setEffectiveEnd(new Date(now.getTime() + 1000));
        req.setPassword("p");
        req.setUserId(user.getId());
        req.setEmail("noreply@wxcount.com");

        when(this.accountDao.save(ArgumentMatchers.any())).thenReturn(new AccountEO());
        //when(this.emailSender.send(ArgumentMatchers.any())).thenReturn(null);

        this.target.createAccountByEmail(accountId, req, user);

        verify(this.accountDao).save(argThat((AccountEO entity) -> {
            Assertions.assertNotNull(entity.getId());
            Assertions.assertEquals(req.getEffectiveBegin(), entity.getEffectiveBegin());
            Assertions.assertEquals(req.getEffectiveEnd(), entity.getEffectiveEnd());
            Assertions.assertEquals(req.getPassword(), entity.getPassword());
            Assertions.assertSame(user, entity.getUser());
            Assertions.assertNotNull(entity.getSalt());
            Assertions.assertFalse(entity.isSalted());
            return true;
        }));
    }

}
