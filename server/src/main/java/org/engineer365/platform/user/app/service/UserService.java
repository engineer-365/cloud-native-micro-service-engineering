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

import org.engineer365.common.error.BadRequestError;
import org.engineer365.platform.user.api.enums.ErrorCode;
import org.engineer365.platform.user.api.req.CreateUserReq;
import org.engineer365.platform.user.app.entity.UserEO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.engineer365.platform.user.app.dao.UserDAO;

@Service
@lombok.Setter
@lombok.Getter
public class UserService {

    @Autowired
    UserDAO dao;

    public UserEO getUser(boolean ensureExists, String userId) {
        return getDao().get(ensureExists, userId);
    }

    public UserEO getByName(String name) {
        return getDao().getByName(name);
    }

    public UserEO createUser(String userId, CreateUserReq req) {
        var d = getDao();
        if (d.getByName(req.getName()) != null) {
            throw new BadRequestError(ErrorCode.USER_NAME_DUPLICATES);
        }

        var r = UserEO.CREATE_REQ_COPIER.copy(req);
        r.setId(userId);

        return d.save(r);
    }

}
