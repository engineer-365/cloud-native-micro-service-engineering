package org.engineer365.common.dao.jpa;

import java.util.Arrays;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.engineer365.common.json.JSON;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * JPA DAO的单元测试用的基类，包含数据导入和实体对象数据验证等一些工具性方法
 */
@DataJpaTest
@lombok.Getter
public class DAOTestBase<T, ID, R extends JpaDAO<T, ID>> {

  @Autowired
  TestEntityManager entityManager;

  final R dao;

  protected DAOTestBase(R dao) {
    this.dao = dao;
  }

  protected TestEntityManager entityManager() {
    return this.entityManager;
  }

  protected R dao() {
    return this.dao;
  }

  /**
   * 验证两组实体对象数据是否相同
   *
   * @param assertId 是否验证实体对象的id值
   * @param actuals 待验证的一组实体对象
   * @param expecteds 用于对比的一组实体对象（期望值）
   */
  public void assertEntities(List<T> actuals, T... expecteds) {
    assertEntities(actuals, Arrays.asList(expecteds));
  }


  /**
   * 验证两组实体对象数据是否相同
   *
   * @param actuals 待验证的一组实体对象
   * @param expecteds 用于对比的一组实体对象（期望值）
   */
  public void assertEntities(List<T> actuals, List<T> expecteds) {

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
  public Map<String, T> mapEntitiesById(List<T> entities) {
    var r = new HashMap<String, T>();

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
  public Map<String, Object> toMap(T entity) {
    var r = JSON.from(JSON.to(entity), Map.class);
    r.remove("createdAt");
    r.remove("modifiedAt");
    return r;
  }

  /**
   * 验证两个实体对象数据是否相同
   */
  public void assertEntity(T expected, T actual) {

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

  public T importEntity(T entity) {
    return dao().save(entity);
  }

}
