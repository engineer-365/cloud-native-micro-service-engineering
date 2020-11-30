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
package org.engineer365.platform.user.app.dao.jpa;

import org.engineer365.platform.user.app.entity.QUserEO;
import org.engineer365.platform.user.app.entity.UserEO;
import org.springframework.beans.factory.annotation.Autowired;
import org.engineer365.common.dao.jpa.GenericJpaDAO;
import org.engineer365.platform.user.app.dao.ex.UserDAOEx;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Component;


@Component
public class UserDAOImpl
    extends GenericJpaDAO<UserEO, String>
    implements UserDAOEx {

  @Autowired
  public UserDAOImpl(EntityManager entityManager) {
    super(UserEO.class, QUserEO.userEO, entityManager);
  }

}
