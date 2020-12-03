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

import org.engineer365.platform.user.api.bean.Account;
import org.engineer365.platform.user.api.bean.User;
import org.engineer365.platform.user.api.req.AccountAuthReq;
import org.engineer365.platform.user.api.req.CreateAccountByEmailReq;
import org.engineer365.platform.user.api.req.CreateUserByEmailReq;
import org.engineer365.platform.user.app.entity.AccountEO;
import org.engineer365.platform.user.app.entity.UserEO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.engineer365.common.misc.UuidHelper;
import org.engineer365.platform.user.api.UserApiV1;

@Service
@lombok.Setter
@lombok.Getter
@Transactional(propagation = Propagation.REQUIRED)
public class UserApiV1Service implements UserApiV1 {

    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;

    @Override
    public Account getAccount(String accountId) {
        var r = getAccountService().getAccount(false, accountId);
        return AccountEO.OUTPUT_COPIER.copy(r);
    }

    @Override
    public Account getAccountByEmail(String email) {
        var r = getAccountService().getAccountByEmail(false, email);
        return AccountEO.OUTPUT_COPIER.copy(r);
    }

    @Override
    public User getUser(String userId) {
        var r = getUserService().getUser(false, userId);
        return UserEO.OUTPUT_COPIER.copy(r);
    }

    @Override
    public String authByAccount(AccountAuthReq areq) {
        return getAccountService().authByAccount(areq);
    }

    @Override
    public Account createUserByEmail(CreateUserByEmailReq req) {
        var aidAndUid = UuidHelper.shortUuid();

        var ureq = CreateUserByEmailReq.USER_REQ_COPIER.copy(req);
        ureq.setPrimaryAccountId(aidAndUid);
        getUserService().createUser(aidAndUid, ureq);

        var areq = CreateUserByEmailReq.ACCOUNT_REQ_COPIER.copy(req);
        areq.setUserId(aidAndUid);
        return _createAccountByEmail(areq, aidAndUid);
    }

    @Override
    public Account createAccountByEmail(CreateAccountByEmailReq req) {
        var aid = UuidHelper.shortUuid();
        return _createAccountByEmail(req, aid);
    }

    Account _createAccountByEmail(CreateAccountByEmailReq req, String accountId) {
        var u = getUserService().getUser(true, req.getUserId());
        var a = getAccountService().createAccountByEmail(accountId, req, u);
        return AccountEO.OUTPUT_COPIER.copy(a);
    }

}
