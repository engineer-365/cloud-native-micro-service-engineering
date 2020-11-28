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

import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.AccessLevel;

/**
 * 方便使用QueryDSL实现JPA动态查询的DAO基类
 *
 * @param T 实体类的类型
 * @param ID 实体类的主键的类型
 */
@lombok.Getter(AccessLevel.PROTECTED)
public class GenericJpaDAO<T, ID> extends SimpleJpaRepository<T, ID> implements JpaDAO<T, ID> {

  final JPAQueryFactory queryFactory;

  /**
   * QueryDSL的实体类的path，代表一个QueryDSL实体类
   */
  final EntityPathBase<T> entityPath;

  final EntityManager entityManager;

  public GenericJpaDAO(Class<T> entityClass, EntityPathBase<T> entityPath, EntityManager entityManager) {
    super(entityClass, entityManager);

    this.entityPath = entityPath;
    this.entityManager = entityManager;
    this.queryFactory = new JPAQueryFactory(entityManager);
  }

  // TODO: 加入几个常用的QueryDSL查询方法

}
