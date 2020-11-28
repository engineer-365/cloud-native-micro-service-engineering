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
 * 1. 借用HTTP status的分类，分为BadRequestErro（HTTP 4xx）和InternalError（HTTP 5xx）等几大类。好处如下：
 *    1.1 借鉴HTTP status成熟的出错状态分类设计。
 *    1.2 显然，RESTful API调用抛出的异常因此可以直接被客户端转换。
 *    看似和HTTP协议有些耦合，但因为借鉴的是分类的思想，不是整个HTTP协议。后续，在gRPC等其他接口协议中也准备使用和这里一致的异常分类体系。
 *
 * 2. 提倡直接使用这个package下的预定义好的异常类，并不提倡使用更多自定义的子类，理由如下：
 *    2.1 sdk的RESTful API客户端也使用了这里预定义好的几个异常类，因此能把RESTful API的调用结果里的HTTP status直接转换成这里的异常类，
 *        但对于抛出的自定义子类异常，客户端就不能识别了，减弱了两边异常处理的一致性。
 *    2.2 HTTP status的出错状态分类设计已经成熟，实际上通常调用方只关心一个很粗略的错误原因，大部分情况下不会针对特定的错误原因做特定处理，
 *        而会是直接把错误写入日志、然后转发异常。所以，多一个自定义的子类异常对作为主要相关方的调用方一般没什么大的用处。
 *    2.3 如果认为需要一个新的自定义，首先应当参考HTTP status的出错状态分类设计仔细考虑是否真的必要。
 *    2.4 自定义异常子类容易导致异常类数量的膨胀，是个经典的Java异常体系的弊端。
 *
 * 3. 服务器端不处理错误消息的i18n，i18n由前端根据出错的key翻译成合适的用户语言。
 *
 * TODO：目前还不完整，需要再增加一些预定义子类。
 *
 */