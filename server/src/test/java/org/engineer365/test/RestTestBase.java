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

import org.junit.jupiter.api.Disabled;

import org.engineer365.common.json.JSON;
import org.engineer365.common.rest.RestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;


/**
 * Controller单元测试的基类，封装了一些浅层次的对MockMVC的使用方法。
 *
 * Controller单元测试的目的：
 * 1）验证URL（包括URL里的参数）的正确性
 * 2）验证JSON序列化和反序列化的正确性
 * 3）验证HTTP status code的正确性
 * 4）验证controller和service的参数/结果的传递
 *
 * 注：虽然参数校验目前是在controller层实现，但后续会搬到service层，所以controller层这里测试时也不涉及。
 */
@Disabled
@Execution(ExecutionMode.SAME_THREAD) // controller test不支持并行执行
@Import(RestConfig.class)
public class RestTestBase {

    @Autowired
    protected MockMvc mockMvc;

    /**
     * 验证是否api未出错，并且返回的response body（只支持JSON）是否和期望值一致
     *
     * @param expectedResponseContent - 期望的返回内容，可以为null
     * @return
     */
    public ResultActions expectOk(ResultActions actions, Object expectedResponseContent) {
        try {
            var ra = actions.andExpect(status().isOk());
            if (expectedResponseContent == null) {
                return ra; // 是否改为返回HTTP 204 (No Content)
            }
            return ra.andExpect(content().string(JSON.to(expectedResponseContent)));
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 创建一个GET请求
     *
     * @param urlTemplate - URL模板（可用“{参数名}”表示参数占位符）
     * @param uriVars - URL模板的参数占位符对应的参数值
     */
    public MockHttpServletRequestBuilder GET(String urlTemplate, Object... uriVars) {
		return MockMvcRequestBuilders
                .get(urlTemplate, uriVars)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
    }


    /**
     * 创建一个POST请求
     *
     * @param requestContent - POST body（会被序列化成JSON）
     * @param urlTemplate - URL模板（可用“{参数名}”表示参数占位符）
     * @param uriVars - URL模板的参数占位符对应的参数值
     */
    public MockHttpServletRequestBuilder POST(Object requestContent, String urlTemplate, Object... uriVars) {
		return MockMvcRequestBuilders
                .post(urlTemplate, uriVars)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JSON.to(requestContent));
    }

    /**
     * 发出一个GET请求，然后验证是否api未出错，并且返回的response body（只支持JSON）是否和期望值一致
     *
     * @param expectedResponseContent - 期望的返回内容，可以为null
     * @param urlTemplate - URL模板（可用“{参数名}”表示参数占位符）
     * @param uriVars - URL模板的参数占位符对应的参数值
     */
    public ResultActions getThenExpectOk(Object expectedResponseContent, String urlTemplate, Object... uriVars) {
        try {
            var reqBuilder = GET(urlTemplate, uriVars);
            var actions = this.mockMvc.perform(reqBuilder);
            return expectOk(actions, expectedResponseContent);
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 发出一个POST请求，然后验证是否api未出错，并且返回的response body（只支持JSON）是否和期望值一致
     *
     * @param requestContent - POST body（会被序列化成JSON）
     * @param expectedResponseContent - 期望的返回内容，可以为null
     * @param urlTemplate - URL模板（可用“{参数名}”表示参数占位符）
     * @param uriVars - URL模板的参数占位符对应的参数值
     */
    public ResultActions postThenExpectOk(Object requestContent, Object expectedResponseContent, String urlTemplate, Object... uriVars) {
        try {
            var reqBuilder = POST(requestContent, urlTemplate, uriVars);
            var actions = this.mockMvc.perform(reqBuilder);
            return expectOk(actions, expectedResponseContent);
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
