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
package org.engineer365.common.bean;

import java.util.Date;

/**
 * 具有唯一性id标示的基类，主要用于实体类的VO。
 * 所有property必须和@see org.engineer365.common.entity.GenericEO一一对应，因为
 * EO和VO间的property copy机制依赖于这个规定。
 *
 * 这里限制了id必须是String，所以需要非String的id时需要另外实现。
 *
 */
@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.experimental.SuperBuilder
public class GenericBean extends Dumpable {

  /**
   * 唯一性标示
   */
  String id;

  /**
   * 创建时间
   */
  Date createdAt;

}
