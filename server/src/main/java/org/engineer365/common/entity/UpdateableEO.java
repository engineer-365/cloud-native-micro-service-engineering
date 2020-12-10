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
package org.engineer365.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.springframework.data.annotation.LastModifiedDate;

/**
 * 可修改的实体类的VO。
 *
 * 所有property必须和@see org.engineer365.common.bean.UpdateableBean一一对应，因为
 * EO和VO间的property copy机制依赖于这个规定。
 *
 */
@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.experimental.SuperBuilder
@MappedSuperclass
public class UpdateableEO extends GenericEO {

  /**
   * 版本号。用于乐观锁(optimistic locking)
   */
  @Version
  int version;

  /**
   * 修改时间
   */
  @LastModifiedDate
  @Column(name = "modified_at", nullable = false)
  Date modifiedAt;

}
