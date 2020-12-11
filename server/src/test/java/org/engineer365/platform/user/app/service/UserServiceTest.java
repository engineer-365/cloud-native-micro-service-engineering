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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.argThat;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.engineer365.platform.user.api.req.CreateUserReq;
import org.engineer365.platform.user.app.dao.UserDAO;
import org.engineer365.platform.user.app.entity.UserEO;

import org.engineer365.test.ServiceTestBase;

public class UserServiceTest extends ServiceTestBase {

    @Mock
    UserDAO dao;

    @InjectMocks
    UserService target;

    @Test
    void test_createAccount_happy() {
        var userId = "u-1";

        var req = CreateUserReq.builder().fullName("n-1").build();

        when(this.dao.save(ArgumentMatchers.any())).thenReturn(new UserEO());
        this.target.createUser(userId, req);

        verify(this.dao).save(argThat((UserEO entity) -> {
            Assertions.assertNotNull(entity.getId());
            Assertions.assertEquals(req.getFullName(), entity.getFullName());
            return true;
        }));
    }

}
