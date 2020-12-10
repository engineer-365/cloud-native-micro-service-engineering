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
package org.engineer365.platform.user.app.controller;

import org.engineer365.platform.user.api.bean.Account;
import org.engineer365.platform.user.api.req.AccountAuthReq;
import org.engineer365.platform.user.api.req.CreateAccountByEmailReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.engineer365.platform.user.app.service.UserApiV1Service;

/**
 * @author
 *
 */
@lombok.Getter
@RestController
@RequestMapping(
    path = "platform/user/api/v1/rest",
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = "*")
public class AccountApiV1RestController {

    @Autowired
    UserApiV1Service service;


    @PostMapping(path = "account/createAccountByEmail")
    public Account createAccountByEmail(@RequestBody @Valid CreateAccountByEmailReq req) {
        return getService().createAccountByEmail(req);
    }


    @GetMapping(path = "account/_/{accountId}")
    public Account getAccount(@PathVariable @NotBlank String accountId) {
        return getService().getAccount(accountId);
    }


    @GetMapping(path = "account/getAccountByEmail")
    public Account getAccountByEmail(@RequestParam @NotBlank String email) {
        return getService().getAccountByEmail(email);
    }


    @PostMapping(path = "account/authByAccount")
    public String authByAccount(@RequestBody @Valid AccountAuthReq req) {
        return getService().authByAccount(req);
    }

}
