package org.engineer365.common.misc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestStringHelper {


  @Test
  public void test_toString_null() {
    Assertions.assertNull(StringHelper.toString(null));
  }


  @Test
  public void test_isBlank_happy() {
    Assertions.assertTrue(StringHelper.isBlank(null));
    Assertions.assertTrue(StringHelper.isBlank(""));
    Assertions.assertTrue(StringHelper.isBlank("   "));
    Assertions.assertTrue(StringHelper.isBlank("\t"));
    Assertions.assertTrue(StringHelper.isBlank("\n"));
    Assertions.assertTrue(StringHelper.isBlank("\r"));
    Assertions.assertTrue(StringHelper.isBlank("\n\t"));

    Assertions.assertFalse(StringHelper.isBlank("a"));
    Assertions.assertFalse(StringHelper.isBlank(" a "));
    Assertions.assertFalse(StringHelper.isBlank(" a"));
    Assertions.assertFalse(StringHelper.isBlank("a "));
    Assertions.assertFalse(StringHelper.isBlank("\na\t"));
    Assertions.assertFalse(StringHelper.isBlank("\ta"));
    Assertions.assertFalse(StringHelper.isBlank("a\n"));
  }

  @Test
  public void test_notBlank_happy() {
    Assertions.assertFalse(StringHelper.notBlank(null));
    Assertions.assertFalse(StringHelper.notBlank(""));
    Assertions.assertFalse(StringHelper.notBlank("   "));
    Assertions.assertFalse(StringHelper.notBlank("\t"));
    Assertions.assertFalse(StringHelper.notBlank("\n"));
    Assertions.assertFalse(StringHelper.notBlank("\r"));
    Assertions.assertFalse(StringHelper.notBlank("\n\t"));

    Assertions.assertTrue(StringHelper.notBlank("a"));
    Assertions.assertTrue(StringHelper.notBlank(" a "));
    Assertions.assertTrue(StringHelper.notBlank(" a"));
    Assertions.assertTrue(StringHelper.notBlank("a "));
    Assertions.assertTrue(StringHelper.notBlank("\na\t"));
    Assertions.assertTrue(StringHelper.notBlank("\ta"));
    Assertions.assertTrue(StringHelper.notBlank("a\n"));
  }

}
