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
package org.engineer365.platform.user.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.engineer365.platform.user.api.bean.Account;
import org.engineer365.platform.user.api.enums.AccountType;
import org.engineer365.platform.user.api.req.CreateAccountByEmailReq;


import org.engineer365.common.bean.BeanCopyer;
import org.engineer365.common.entity.UpdateableEO;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.experimental.SuperBuilder
@Entity
@Table(name = "user_account")
public class AccountEO extends UpdateableEO {

    public static final BeanCopyer<AccountEO, Account> OUTPUT_COPIER = new BeanCopyer<AccountEO, Account>(
            AccountEO.class, Account.class, Account::new, Account[]::new) {

        @Override
        protected void doRender(AccountEO source, Account target) {
            super.doRender(source, target);

            var u = source.getUser();
            if (u != null) {
                target.setUserId(u.getId());
            }
        }
    };

    public static final BeanCopyer<CreateAccountByEmailReq, AccountEO> CREATE_BY_EMAIL_REQ_COPIER
        = new BeanCopyer<CreateAccountByEmailReq, AccountEO>(
            CreateAccountByEmailReq.class, AccountEO.class, AccountEO::new, AccountEO[]::new) {

        @Override
        protected void doRender(CreateAccountByEmailReq source, AccountEO target) {
            super.doRender(source, target);

            target.setType(AccountType.EMAIL);
            target.setCredential(source.getEmail());
        }
    };

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    UserEO user;

    @Enumerated(EnumType.STRING)
    @Column(length = 16, name = "type", nullable = false)
    AccountType type;

    @Column(length = 64, name = "credential", nullable = false)
    String credential;

    @Column(name = "password", length = 64, nullable = false)
    String password;

}
