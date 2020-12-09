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
package org.engineer365.platform.user.api.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.engineer365.common.bean.BeanCopyer;
import org.engineer365.common.bean.Dumpable;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.experimental.SuperBuilder
@lombok.EqualsAndHashCode(callSuper=false)
public class CreateUserByEmailReq extends Dumpable {

  public static final BeanCopyer<CreateUserByEmailReq, CreateAccountByEmailReq> ACCOUNT_REQ_COPIER
    = new BeanCopyer<CreateUserByEmailReq, CreateAccountByEmailReq>(
      CreateUserByEmailReq.class, CreateAccountByEmailReq.class, CreateAccountByEmailReq::new, CreateAccountByEmailReq[]::new);


  public static final BeanCopyer<CreateUserByEmailReq, CreateUserReq> USER_REQ_COPIER
    = new BeanCopyer<CreateUserByEmailReq, CreateUserReq>(
        CreateUserByEmailReq.class, CreateUserReq.class, CreateUserReq::new, CreateUserReq[]::new);

  @NotBlank
  @Size(max = 32)
  String name;

  @NotBlank
  @Size(max = 64)
  String fullName;

  @NotBlank
  @Size(max = 64)
  String email;

  @NotBlank
  @Size(max = 64)
  String password;

}
