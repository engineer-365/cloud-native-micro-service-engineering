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
