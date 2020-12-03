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
package org.engineer365.common.dao.jpa;

import java.util.List;

import javax.annotation.Nullable;

import org.engineer365.common.error.NotFoundError;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 方便使用QueryDSL实现JPA动态查询的DAO基类
 *
 *
 * @param T 实体类的类型
 * @param ID 实体类的主键的类型
 */
@NoRepositoryBean
@Nullable
public interface JpaDAO<T, ID> extends PagingAndSortingRepository<T, ID> {

  // TODO: 加入几个常用的接口方法
  List<T> findAll();

  default T get(boolean ensureExists, ID id) {
    var r = findById(id);
    if (r.isPresent()) {
      return r.get();
    }
    if (ensureExists) {
      throw new NotFoundError(NotFoundError.Code.ENTITY_NOT_FOUND, "id=%s", String.valueOf(id));
    }
    return null;
  }

}
