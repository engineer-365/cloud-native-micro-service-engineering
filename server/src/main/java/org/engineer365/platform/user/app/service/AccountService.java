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
import org.engineer365.platform.user.app.entity.AccountEO;
import org.engineer365.platform.user.app.entity.UserEO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.engineer365.common.error.BadRequestError;
import org.engineer365.common.error.NotFoundError;
import org.engineer365.platform.user.app.dao.AccountDAO;

@Service
@lombok.Setter
@lombok.Getter
public class AccountService {

    @Autowired
    AccountDAO accountDao;

    public AccountEO getAccount(boolean ensureExists, String accountId) {
        return getAccountDao().get(ensureExists, accountId);
    }

    public AccountEO getAccountByEmail(boolean ensureExists, String email) {
        var r = getAccountDao().getByCredentialAndType(email, AccountType.EMAIL);
        if (r == null && ensureExists) {
            throw new NotFoundError(ErrorCode.NO_ACCOUNT_WITH_SPECIFIED_EMAIL);
        }
        return r;
    }

    public void checkRequestWithAccount(AccountEO account, AccountAuthReq req) {
        if (account == null) {
            throw new NotFoundError(ErrorCode.ACCOUNT_NOT_FOUND);
        }

        if (account.getPassword().equals(req.getPassword()) == false) {
            throw new BadRequestError(ErrorCode.WRONG_PASSWORD);
        }
    }

    public String authByAccount(AccountAuthReq req) {
        var a = getAccount(false, req.getAccountId());
        checkRequestWithAccount(a, req);
        return a.getId(); //TODO: replace it with JWT
    }

    public AccountEO createAccountByEmail(String accountId, CreateAccountByEmailReq req, UserEO user) {
        if (getAccountDao().getByCredentialAndType(req.getEmail(), AccountType.EMAIL) != null) {
            throw new BadRequestError(ErrorCode.ACCOUNT_EMAIL_DUPLICATES);
        }

        var r = AccountEO.CREATE_BY_EMAIL_REQ_COPIER.copy(req); {
            r.setId(accountId);
            r.setUser(user);
        }

        return getAccountDao().save(r);
    }

}
