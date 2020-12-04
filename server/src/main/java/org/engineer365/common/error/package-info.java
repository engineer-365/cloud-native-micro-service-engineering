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
package org.engineer365.common.error;

/**
 * 异常分类体系。
 *
 * 遵循以下原则：
 *
 * 1. 借鉴HTTP status成熟的出错状态分类，分为BadRequestErro（HTTP 4xx）和InternalError（HTTP 5xx）等几大类。
 * 2. 详细的错误代码用enum表示，每个模块/微服务可以有自己的错误代码枚举类，尽量但不强求跨模块/微服务时不重复
 * 3. 透明地传递跨模块/微服务的错误
 * 4. 并不提倡使用更多自定义的子类。
 * 5. 服务器端不处理错误消息的i18n，i18n由前端根据出错的key翻译成合适的用户语言。
 *
 * TODO：目前还不完整，需要再增加一些预定义子类。
 *
 */