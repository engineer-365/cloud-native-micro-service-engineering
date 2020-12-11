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
package org.engineer365.platform.user.app.dao.support;

import org.engineer365.test.JpaDAOTestBase;
import org.engineer365.common.dao.jpa.JpaDAO;
import org.engineer365.platform.user.app.dao.UserDAO;
import org.engineer365.platform.user.app.entity.UserEO;
import org.junit.jupiter.api.Disabled;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;


/**
 * User模块的JPA DAO的单元测试用的基类
 *
 * @param E - 实体类
 * @param ID - 实体类的主键
 * @param D - DAO类
 */
@Disabled
@EntityScan(basePackageClasses = UserEO.class)
@EnableJpaRepositories(basePackageClasses = UserDAO.class)
@ContextConfiguration(classes = UserDAO.class)
public class JpaDAOTestSupport<E, ID, D extends JpaDAO<E, ID>> extends JpaDAOTestBase<E, ID, D> {

  protected JpaDAOTestSupport(D dao) {
    super(dao);
  }

}
