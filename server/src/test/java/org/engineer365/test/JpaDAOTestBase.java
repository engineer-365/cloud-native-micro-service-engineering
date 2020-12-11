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
package org.engineer365.test;

import java.util.Arrays;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Assertions;
import org.engineer365.common.dao.jpa.JpaDAO;
import org.engineer365.common.json.JSON;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * JPA DAO的单元测试用的基类，包含数据导入和实体对象数据验证等一些工具性方法
 *
 * 遵循spring里JPA repository的做法，case执行完成后事务由spring控制做回滚，
 * 以此实现case之间的测试数据的隔离。
 *
 * 测试的目标数据库是H2（内存数据库），大部分case已经足够。如果有native SQL
 * 测试，可以放到integration test里做。
 *
 * @param E - 实体类
 * @param ID - 实体类的主键
 * @param D - DAO类
 */
@Disabled
@DataJpaTest
@EnableJpaAuditing
@lombok.Getter
public class JpaDAOTestBase<E, ID, D extends JpaDAO<E, ID>> {

  @Autowired
  TestEntityManager entityManager;

  final D dao;

  protected JpaDAOTestBase(D dao) {
    this.dao = dao;
  }

  protected TestEntityManager entityManager() {
    return this.entityManager;
  }

  protected D dao() {
    return this.dao;
  }

  /**
   * 验证两组实体对象数据是否相同
   *
   * @param assertId 是否验证实体对象的id值
   * @param actuals 待验证的一组实体对象
   * @param expecteds 用于对比的一组实体对象（期望值）
   */
  @SuppressWarnings("unchecked")
  public void assertEntities(List<E> actuals, E... expecteds) {
    assertEntities(actuals, Arrays.asList(expecteds));
  }


  /**
   * 验证两组实体对象数据是否相同
   *
   * @param actuals 待验证的一组实体对象
   * @param expecteds 用于对比的一组实体对象（期望值）
   */
  public void assertEntities(List<E> actuals, List<E> expecteds) {

    var actualM = mapEntitiesById(actuals);
    var expectedM = mapEntitiesById(expecteds);

    var actualJ = JSON.pretty(actualM);
    var expectedJ = JSON.pretty(expectedM);

    if (actualM.size() != expectedM.size()) {
      Assertions.fail(String.format(
          "Size of actual (%d) is not equal to expected (%d). Actual entities: %s\n  expected entities: %s",
          actualM.size(), expectedM.size(), actualJ, expectedJ));
    }

    for (var entry : expectedM.entrySet()) {
      var key = entry.getKey();
      if (!actualM.containsKey(key)) {
        Assertions.fail(String.format("no actual Object with key=%s. Actual entities: %s\n  expected entities: %s", key,
            actualJ, expectedJ));
      }

      assertEntity(entry.getValue(), actualM.get(key));
    }
  }

  /**
   * 把一组实体对象转换成以id值为key的一个Map，方便单元测试时用id取数据作对比。
   *
   * @param entities 一组实体对象
   * @return
   */
  public Map<String, E> mapEntitiesById(List<E> entities) {
    var r = new HashMap<String, E>();

    for (var entity : entities) {
      var map = toMap(entity);
      String identifier = (String)map.get("id");
      r.put(identifier, entity);
    }
    return r;
  }

  /**
   * 把一个实体对象转换成一个Map。key
   *
   * @param byId 是否以id值为map的key
   * @param entities 一组实体对象
   */
  @SuppressWarnings("unchecked")
  public Map<String, Object> toMap(E entity) {
    var r = JSON.from(JSON.to(entity), Map.class);
    r.remove("createdAt");
    r.remove("modifiedAt");
    return r;
  }

  /**
   * 验证两个实体对象数据是否相同
   */
  public void assertEntity(E expected, E actual) {

    var expectedM = toMap(expected);
    var actualM = toMap(actual);

    var actualId = (String)actualM.get("id");
    var expectedId = (String)expectedM.get("id");
    if (!Objects.equals(actualId, expectedId)) {
      Assertions.fail(String.format("id is not equal. \n  actual id: %s\n  expected id: %s", actualId, expectedId));
    }

    actualM.remove("id");
    expectedM.remove("id");

    if (!expectedM.equals(actualM)) {
      Assertions.fail(String.format("no same properties. \n  actual: %s\n  expected: %s\n", JSON.pretty(actual), JSON.pretty(expected)));
    }
  }

  public E importEntity(E entity) {
    return dao().save(entity);
  }

}
