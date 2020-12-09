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
import javax.persistence.Table;

import org.engineer365.platform.user.api.bean.User;
import org.engineer365.platform.user.api.req.CreateUserReq;

import org.engineer365.common.bean.BeanCopyer;
import org.engineer365.common.entity.UpdateableEO;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.experimental.SuperBuilder
@Entity
@Table(name = "user_user")
public class UserEO extends UpdateableEO {

    public static final BeanCopyer<UserEO, User> OUTPUT_COPIER = new BeanCopyer<UserEO, User>(UserEO.class, User.class,
            User::new, User[]::new);

    public static final BeanCopyer<CreateUserReq, UserEO> CREATE_REQ_COPIER = new BeanCopyer<CreateUserReq, UserEO>(
            CreateUserReq.class, UserEO.class, UserEO::new, UserEO[]::new);

    @Column(length = 32, nullable = false)
    String name;

    @Column(name = "full_name", length = 64, nullable = false)
    String fullName;

    @Column(name = "primary_account_id", length = 22)
    String primaryAccountId;

    @Column(name = "is_root", nullable = false)
    boolean root;

}
